package mx.tec.sonrisas.asistencia.data

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.tec.sonrisas.asistencia.data.local.CachedUser
import mx.tec.sonrisas.asistencia.data.local.TokenStore
import mx.tec.sonrisas.asistencia.data.local.UserPreferencesRepository
import mx.tec.sonrisas.asistencia.data.remote.ErrorResponseDto
import mx.tec.sonrisas.asistencia.data.remote.LoginRequestDto
import mx.tec.sonrisas.asistencia.data.remote.RetrofitClient
import mx.tec.sonrisas.asistencia.data.remote.UserDto
import mx.tec.sonrisas.asistencia.ui.navigation.UserRole
import retrofit2.HttpException

class AuthException(message: String, cause: Throwable? = null) : Exception(message, cause)

// Orquesta login/logout: llama al backend (Retrofit), guarda el token cifrado
// (TokenStore) y cachea el perfil no sensible (UserPreferencesRepository).
object AuthRepository {
    private val api get() = RetrofitClient.api

    suspend fun login(email: String, password: String): Result<UserRole> = withContext(Dispatchers.IO) {
        runCatching {
            val login = apiCall { api.login(LoginRequestDto(email, password)) }
            val role = apiCall { api.getRole(login.user.roleId, "Bearer ${login.accessToken}") }
            TokenStore.saveSession(login.accessToken, role.description)
            UserPreferencesRepository.save(login.user.toCachedUser())
            roleFrom(role.description)
        }
    }

    suspend fun logout() {
        TokenStore.clear()
        UserPreferencesRepository.clear()
    }

    fun hasSession(): Boolean = TokenStore.hasSession()

    // Rol cacheado para saltar el login en un arranque en frio con sesion valida.
    fun cachedRole(): UserRole? = TokenStore.getRoleDescription()?.let(::roleFrom)

    private fun roleFrom(roleDescription: String): UserRole = when (roleDescription.trim().lowercase()) {
        "director" -> UserRole.Direccion
        "encargado" -> UserRole.Responsable
        else -> UserRole.Empleado // "staff" y cualquier valor no reconocido caen aqui
    }

    // Retrofit lanza HttpException en respuestas no exitosas (4xx/5xx) sin leer
    // el cuerpo del error; aqui se extrae el "detail" que manda el backend.
    private suspend fun <T> apiCall(call: suspend () -> T): T = try {
        call()
    } catch (e: HttpException) {
        val raw = e.response()?.errorBody()?.string()
        val detail = raw?.let { runCatching { Gson().fromJson(it, ErrorResponseDto::class.java).detail }.getOrNull() }
        throw AuthException(detail ?: "Error del servidor (${e.code()})", cause = e)
    }

    private fun UserDto.toCachedUser() = CachedUser(
        userId = userId,
        daycareId = daycareId,
        name = name,
        lastname = lastname,
        email = email,
        roleId = roleId,
        positionId = positionId,
        isActive = isActive,
    )
}
