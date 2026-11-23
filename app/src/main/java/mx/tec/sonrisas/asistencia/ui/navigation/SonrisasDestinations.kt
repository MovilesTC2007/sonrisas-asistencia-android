package mx.tec.sonrisas.asistencia.ui.navigation

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Apartment
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.ui.graphics.vector.ImageVector
import mx.tec.sonrisas.asistencia.ui.components.SonrisasNavItem

enum class UserRole {
    Empleado,
    Responsable,
    Direccion,
    ;

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
                SonrisasTab.Turnos,
                SonrisasTab.Perfil,
            )
            Direccion -> listOf(
                SonrisasTab.Inicio,
                SonrisasTab.Estancias,
                SonrisasTab.Reportes,
                SonrisasTab.Perfil,
            )
        }
}

// delegacion para inico de cada rol
enum class SonrisasTab(
    override val label: String,
    override val icon: ImageVector,
    val route: String,
) : SonrisasNavItem {
    Inicio("Inicio", Icons.Outlined.Home, "inicio"),
    Asistencias("Asistencias", Icons.Outlined.CalendarMonth, "asistencias"),
    Personal("Personal", Icons.Outlined.People, "personal"),
    Turnos("Turnos", Icons.Outlined.Schedule, "turnos"),
    Solicitudes("Solicitudes", Icons.Outlined.Description, "solicitudes"),
    Estancias("Estancias", Icons.Outlined.Apartment, "estancias"),
    Reportes("Reportes", Icons.Outlined.BarChart, "reportes"),
    Historial("Historial", Icons.Outlined.History, "historial"),
    Perfil("Perfil", Icons.Outlined.Person, "perfil"),
}

// estas son para las patallas que no llevan la navbar osea las que son pestañas de una pantalla ya hecha
object SonrisasRoutes {
    // con grafos para animar mas nuestra vida
    const val ACCESS_GRAPH = "acceso"
    const val MAIN_GRAPH = "principal"

    // aqui voy a poner la animacion de bienvenida a la aplicacion
    const val LOGIN = "login"
    const val CREATE_ACCOUNT = "crear-cuenta"
    const val LOCATION_PERMISSION = "permiso-ubicacion"
    const val ATTENDANCE_CONFIRMATION = "confirmacion-registro"
    const val REGISTER_STAFF = "registrar-personal"
    const val REPORTS_VIEW_ARG = "vista"
    const val REPORTS_ESTANCIA_ARG = "estanciaId"
    const val NO_ESTANCIA = -1
    const val REPORTS_HOME = "reportes/inicio?vista={vista}&estanciaId={estanciaId}"

    // el staffID van vacias se llenan al pasar las pantallas con las rutas
    const val STAFF_ID_ARG = "staffId"
    const val STAFF_DETAIL = "personal/detalle/{staffId}"

    // Grafo para EstanciasListScreen
    const val ESTANCIAS_FILTER_ARG = "filtro"
    const val ESTANCIAS_LIST = "estancias/lista?filtro={filtro}"
    const val ESTANCIA_ID_ARG = "estanciaId"
    const val ESTANCIA_DETAIL = "estancias/detalle/{estanciaId}"
    const val HISTORY_ESTANCIA_ARG = "estanciaId"
    const val HISTORY_LIST = "historial/lista?estanciaId={estanciaId}"
    const val HISTORY_EVENT_ID_ARG = "eventId"
    const val HISTORY_EVENT = "historial/evento/{eventId}"
    const val PERSON_ID_ARG = "personId"
    const val PERSON_DETAIL = "estancias/persona/{personId}"
    const val DIRECCION_PROFILE = "direccion/perfil"
    const val DIRECCION_TRANSFER = "direccion/perfil/transferir"
    const val RESPONSABLE_NAME_ARG = "nombre"
    const val RESPONSABLE_ESTANCIA_ARG = "estancia"
    const val NEW_RESPONSABLE = "direccion/nuevo-responsable"
    const val RESPONSABLE_INVITE = "direccion/nuevo-responsable/codigo?nombre={nombre}&estancia={estancia}"

    fun responsableInvite(name: String, estancia: String) =
        "direccion/nuevo-responsable/codigo?nombre=${Uri.encode(name)}&estancia=${Uri.encode(estancia)}"

    fun historyList(estanciaId: Int? = null) =
        if (estanciaId == null) "historial/lista" else "historial/lista?estanciaId=$estanciaId"

    fun historyEvent(eventId: Int) = "historial/evento/$eventId"

    fun personDetail(personId: Int) = "estancias/persona/$personId"

    // armado ruta
    fun estanciasList(filter: String? = null) =
        if (filter == null) "estancias/lista" else "estancias/lista?filtro=$filter"

    fun estanciaDetail(estanciaId: Int) = "estancias/detalle/$estanciaId"
    fun staffDetail(staffId: Int) = "personal/detalle/$staffId"
    fun reports(view: String? = null, estanciaId: Int? = null): String {
        val params = listOfNotNull(
            view?.let { "vista=$it" },
            estanciaId?.let { "estanciaId=$it" },
        )
        return if (params.isEmpty()) "reportes/inicio" else "reportes/inicio?" + params.joinToString("&")
    }
}
