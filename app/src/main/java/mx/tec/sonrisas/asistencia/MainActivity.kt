package mx.tec.sonrisas.asistencia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import mx.tec.sonrisas.asistencia.ui.navigation.SonrisasApp
import mx.tec.sonrisas.asistencia.ui.navigation.UserRole
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme

private val DEV_ROLE = UserRole.Empleado
//por eliminar
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SonrisasAsistenciasTheme {
                //para pruebas directas sin manejo de rol por ahora
                SonrisasApp(role = DEV_ROLE)
            }
        }
    }
}
