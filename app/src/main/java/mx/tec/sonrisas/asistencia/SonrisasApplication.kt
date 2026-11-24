package mx.tec.sonrisas.asistencia

import android.app.Application
import mx.tec.sonrisas.asistencia.data.local.TokenStore
import mx.tec.sonrisas.asistencia.data.local.UserPreferencesRepository

class SonrisasApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TokenStore.init(this)
        UserPreferencesRepository.init(this)
    }
}
