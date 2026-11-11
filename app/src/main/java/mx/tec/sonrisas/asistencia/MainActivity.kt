package mx.tec.sonrisas.asistencia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import mx.tec.sonrisas.asistencia.ui.navigation.SonrisasApp
import mx.tec.sonrisas.asistencia.ui.navigation.UserRole
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme

//ROL CON EL QUE ENTRA LA APP (temporal mientras no hay backend)
//Aqui se cambia para probar cada rol, las opciones son:
//  UserRole.Empleado     -> Inicio | Asistencias | Solicitudes | Perfil
//  UserRole.Responsable  -> Inicio | Personal | Turnos | Solicitudes
//  UserRole.Direccion    -> Inicio | Estancias | Reportes | Historial
//Cuando el login sea real, el rol vendra del servidor y esta variable se elimina
private val DEV_ROLE = UserRole.Direccion
//por eliminar
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SonrisasAsistenciasTheme {
                SonrisasApp(role = DEV_ROLE)
            }
        }
    }
}
