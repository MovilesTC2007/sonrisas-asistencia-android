package mx.tec.sonrisas.asistencia.data.remote

import com.google.gson.annotations.SerializedName

// El backend usa snake_case; @SerializedName traduce a los nombres de campo en Kotlin.

data class LoginRequestDto(val email: String, val password: String)

data class LoginResponseDto(
    @SerializedName("access_token") val accessToken: String,
    val user: UserDto,
)

data class UserDto(
    @SerializedName("user_id") val userId: String,
    @SerializedName("daycare_id") val daycareId: String,
    val name: String,
    val lastname: String,
    val email: String,
    @SerializedName("role_id") val roleId: String,
    @SerializedName("position_id") val positionId: String?,
    @SerializedName("is_active") val isActive: Boolean,
)

// Las funciones SQL escalares del backend se devuelven como {"<nombre_funcion>": valor}.
data class RoleResponseDto(@SerializedName("get_roles_description") val description: String)

data class ErrorResponseDto(val detail: String?)
