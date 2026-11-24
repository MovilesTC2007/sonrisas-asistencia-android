package mx.tec.sonrisas.asistencia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import mx.tec.sonrisas.asistencia.data.AuthRepository
import mx.tec.sonrisas.asistencia.ui.navigation.SonrisasApp
import mx.tec.sonrisas.asistencia.ui.navigation.UserRole
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Sesion ya guardada (TokenStore via AuthRepository) -> saltar login.
        val hasSession = AuthRepository.hasSession()
        val initialRole = AuthRepository.cachedRole() ?: UserRole.Empleado

        setContent {
            SonrisasAsistenciasTheme {
                var role by rememberSaveable { mutableStateOf(initialRole) }
                SonrisasApp(
                    role = role,
                    startAtLogin = !hasSession,
                    onRoleResolved = { role = it },
                )
            }
        }
    }
}
