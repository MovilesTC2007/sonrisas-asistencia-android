package mx.tec.sonrisas.asistencia.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

// Guarda el access_token cifrado con una llave del Android Keystore (nunca sale
// del dispositivo). role_description viaja aqui tambien, no en DataStore, porque
// MainActivity necesita leerla de forma sincrona al arrancar la app para decidir
// la pantalla inicial, y SharedPreferences es sincrono mientras que DataStore no.
object TokenStore {
    private const val PREFS_NAME = "auth_secure_prefs"
    private const val KEY_ACCESS_TOKEN = "access_token"
    private const val KEY_ROLE_DESCRIPTION = "role_description"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        prefs = EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    fun saveSession(accessToken: String, roleDescription: String) {
        prefs.edit()
            .putString(KEY_ACCESS_TOKEN, accessToken)
            .putString(KEY_ROLE_DESCRIPTION, roleDescription)
            .apply()
    }

    fun getAccessToken(): String? = prefs.getString(KEY_ACCESS_TOKEN, null)

    fun getRoleDescription(): String? = prefs.getString(KEY_ROLE_DESCRIPTION, null)

    fun hasSession(): Boolean = getAccessToken() != null

    // Logout explicito o 401 del backend (ver AuthInterceptor).
    fun clear() {
        prefs.edit().clear().apply()
    }
}
