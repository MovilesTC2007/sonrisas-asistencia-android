package mx.tec.sonrisas.asistencia.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.userDataStore by preferencesDataStore(name = "user_profile")

// Perfil del colaborador tal como lo entrega /auth/login o /users/me.
// No es informacion sensible (el backend ya la da a cualquiera con un token
// valido), asi que basta DataStore sin cifrar: solo es una cache para no
// repetir la llamada de red en cada pantalla.
data class CachedUser(
    val userId: String,
    val daycareId: String,
    val name: String,
    val lastname: String,
    val email: String,
    val roleId: String,
    val positionId: String?,
    val isActive: Boolean,
)

object UserPreferencesRepository {
    private object Keys {
        val USER_ID = stringPreferencesKey("user_id")
        val DAYCARE_ID = stringPreferencesKey("daycare_id")
        val NAME = stringPreferencesKey("name")
        val LASTNAME = stringPreferencesKey("lastname")
        val EMAIL = stringPreferencesKey("email")
        val ROLE_ID = stringPreferencesKey("role_id")
        val POSITION_ID = stringPreferencesKey("position_id")
        val IS_ACTIVE = booleanPreferencesKey("is_active")
    }

    private lateinit var appContext: Context

    val user: Flow<CachedUser?>
        get() = appContext.userDataStore.data.map { prefs ->
            val userId = prefs[Keys.USER_ID] ?: return@map null
            CachedUser(
                userId = userId,
                daycareId = prefs[Keys.DAYCARE_ID].orEmpty(),
                name = prefs[Keys.NAME].orEmpty(),
                lastname = prefs[Keys.LASTNAME].orEmpty(),
                email = prefs[Keys.EMAIL].orEmpty(),
                roleId = prefs[Keys.ROLE_ID].orEmpty(),
                positionId = prefs[Keys.POSITION_ID],
                isActive = prefs[Keys.IS_ACTIVE] ?: false,
            )
        }

    fun init(context: Context) {
        appContext = context
    }

    suspend fun currentUser(): CachedUser? = user.first()

    suspend fun save(cachedUser: CachedUser) {
        appContext.userDataStore.edit { prefs ->
            prefs[Keys.USER_ID] = cachedUser.userId
            prefs[Keys.DAYCARE_ID] = cachedUser.daycareId
            prefs[Keys.NAME] = cachedUser.name
            prefs[Keys.LASTNAME] = cachedUser.lastname
            prefs[Keys.EMAIL] = cachedUser.email
            prefs[Keys.ROLE_ID] = cachedUser.roleId
            cachedUser.positionId?.let { prefs[Keys.POSITION_ID] = it }
            prefs[Keys.IS_ACTIVE] = cachedUser.isActive
        }
    }

    suspend fun clear() {
        appContext.userDataStore.edit { it.clear() }
    }
}
