package mx.tec.sonrisas.asistencia.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import mx.tec.sonrisas.asistencia.ui.components.SonrisasNavItem

//MAPA FINAL DE NAVEGACION A CAMBIAR CON PANTALLAS NUEVAS pues
//aqui se va a describir los tres perfiles de la app
//no se utiliza realmente este codigo solo es para parametros de sondeo actualicen esto cuando se agreguen pantallas
//un enum para los perfiles para dejar de utilizar lo de 1,2,3,4
enum class UserRole {
    Empleado,
    Responsable,
    Direccion;

    val tabs: List<SonrisasTab>
        get() = when (this) {
            Empleado -> listOf(
                SonrisasTab.Inicio,
                SonrisasTab.Asistencias,
                SonrisasTab.Solicitudes,
                SonrisasTab.Perfil,
            )
            Responsable -> listOf(
                SonrisasTab.Inicio,
                SonrisasTab.Personal,
                SonrisasTab.Solicitudes,
                SonrisasTab.Perfil,
            )
            Direccion -> listOf(
                SonrisasTab.Inicio,
                SonrisasTab.Estadisticas,
                SonrisasTab.Perfil,
            )
        }
}

//delegacion para inico de cada rol
enum class SonrisasTab(
    override val label: String,
    override val icon: ImageVector,
    val route: String,
) : SonrisasNavItem {
    Inicio("Inicio", Icons.Outlined.Home, "inicio"),
    Asistencias("Asistencias", Icons.Outlined.CalendarMonth, "asistencias"),
    Personal("Personal", Icons.Outlined.People, "personal"),
    Solicitudes("Solicitudes", Icons.Outlined.Description, "solicitudes"),
    Estadisticas("Estadísticas", Icons.Outlined.BarChart, "estadisticas"),
    Perfil("Perfil", Icons.Outlined.Person, "perfil"),
}

//estas son para las patallas que no llevan la navbar osea las que son pestañas de una pantalla ya hecha 
object SonrisasRoutes {
    //con grafos para animar mas nuestra vida
    const val ACCESS_GRAPH = "acceso"
    const val MAIN_GRAPH = "principal"

    //aqui voy a poner la animacion de bienvenida a la aplicacion
    const val LOGIN = "login"
    const val CREATE_ACCOUNT = "crear-cuenta"
    const val LOCATION_PERMISSION = "permiso-ubicacion"
    const val ATTENDANCE_CONFIRMATION = "confirmacion-registro"
    const val REGISTER_STAFF = "registrar-personal"

    //el staffID van vacias se llenan al pasar las pantallas con las rutas
    const val STAFF_ID_ARG = "staffId"
    const val STAFF_DETAIL = "personal/detalle/{staffId}"

    ///armado ruta
    fun staffDetail(staffId: Int) = "personal/detalle/$staffId"
}
