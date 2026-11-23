package mx.tec.sonrisas.asistencia.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import mx.tec.sonrisas.asistencia.ui.components.AttendanceStatus
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTimePickerDialog
import mx.tec.sonrisas.asistencia.ui.components.WeekDay
import mx.tec.sonrisas.asistencia.ui.components.rememberSonrisasToast
import mx.tec.sonrisas.asistencia.ui.screens.empleadoScreens.LocationUnderReviewScreen
import mx.tec.sonrisas.asistencia.ui.screens.generalScreens.AccountDetail
import mx.tec.sonrisas.asistencia.ui.screens.generalScreens.UserProfileScreen
import mx.tec.sonrisas.asistencia.ui.screens.responsableScreens.AssignShiftScreen
import mx.tec.sonrisas.asistencia.ui.screens.responsableScreens.CorrectRecordScreen
import mx.tec.sonrisas.asistencia.ui.screens.responsableScreens.DeactivateStaffScreen
import mx.tec.sonrisas.asistencia.ui.screens.responsableScreens.InviteCodeScreen
import mx.tec.sonrisas.asistencia.ui.screens.responsableScreens.ManagerRequest
import mx.tec.sonrisas.asistencia.ui.screens.responsableScreens.ManagerRequestsListScreen
import mx.tec.sonrisas.asistencia.ui.screens.responsableScreens.NewStaffScreen
import mx.tec.sonrisas.asistencia.ui.screens.responsableScreens.RequestReviewScreen
import mx.tec.sonrisas.asistencia.ui.screens.responsableScreens.ResponsableProfileScreen
import mx.tec.sonrisas.asistencia.ui.screens.responsableScreens.TodayAttendanceEntry
import mx.tec.sonrisas.asistencia.ui.screens.responsableScreens.TodayAttendanceScreen
import mx.tec.sonrisas.asistencia.ui.screens.responsableScreens.TodayListFilter
import mx.tec.sonrisas.asistencia.ui.screens.responsableScreens.TransferStaffScreen

internal object ManagementRoutes {
    const val TODAY_LIST = "responsable/lista-hoy"
    const val NEW_AREA = "responsable/nueva-area"
    const val INCIDENCE_LIST = "responsable/incidencias"
    const val PERSON_ID_ARG = "personId"
    const val REGISTER_INCIDENCE = "responsable/incidencias/{personId}"
    const val CORRECT_RECORD = "responsable/corregir-registro"
    const val ASSIGN_SHIFT = "responsable/asignar-turno"
    const val NEW_STAFF = "responsable/alta-persona"
    const val INVITE_CODE = "responsable/codigo-invitacion"
    const val INVITE_NAME_ARG = "nombre"
    const val INVITE_DETAIL_ARG = "detalle"
    const val NEW_STAFF_INVITE = "responsable/alta-persona/codigo?nombre={nombre}&detalle={detalle}"
    const val DEACTIVATE = "responsable/dar-de-baja"
    const val TRANSFER_STAFF = "responsable/trasladar"
    const val REQUESTS_LIST = "responsable/solicitudes"
    const val REQUEST_ID_ARG = "requestId"
    const val REQUEST_REVIEW = "responsable/solicitudes/{requestId}"
    const val LOCATION_REVIEW = "responsable/ubicacion-revision"

    fun registerIncidence(personId: Int) = "responsable/incidencias/$personId"

    fun newStaffInvite(name: String, detail: String) =
        "responsable/alta-persona/codigo?nombre=${Uri.encode(name)}&detalle=${Uri.encode(detail)}"

    fun requestReview(requestId: Int) = "responsable/solicitudes/$requestId"
}

private val TodayEntries = listOf(
    TodayAttendanceEntry("Ana Martínez", "Lactantes", "7:55", AttendanceStatus.OnTime),
    TodayAttendanceEntry("Lucía Hernández", "Lactantes", "7:58", AttendanceStatus.OnTime),
    TodayAttendanceEntry("Rosa Pérez", "Lactantes", "8:22", AttendanceStatus.MajorDelay),
    TodayAttendanceEntry("María Torres", "Maternales", "8:09", AttendanceStatus.MinorDelay),
    TodayAttendanceEntry("Sofía Ramírez", "Maternales", null, AttendanceStatus.Absent),
)

// Pestaña Perfil del rol Empleado.
@Composable
internal fun EmployeeProfileDestination() {
    UserProfileScreen(
        fullName = "Fernando Anckerman Ramírez",
        roleSummary = MockData.SHIFT_ROLE,
        details = listOf(
            AccountDetail(label = "Correo", value = "fernando@gmail.com"),
            AccountDetail(label = "Estancia", value = "Arcoíris · Lactantes"),
            AccountDetail(label = "Horario", value = MockData.SHIFT_SCHEDULE),
        ),
        locationUsageText = "Solo al registrar",
    )
}

// Pestaña Perfil del rol Responsable.
@Composable
internal fun ManagerProfileDestination(navController: NavHostController) {
    ResponsableProfileScreen(
        profile = MockData.responsableProfile,
        onLogoutClick = { navController.signOut() },
    )
}

// Vuelve al login y cierra la sesión (misma salida que usa Dirección).
private fun NavHostController.signOut() {
    navigate(SonrisasRoutes.ACCESS_GRAPH) {
        popUpTo(SonrisasRoutes.MAIN_GRAPH) { inclusive = true }
    }
}

// Revisar una solicitud: aprobar o rechazar avisa con un toast y regresa a la lista.
@Composable
private fun RequestReviewDestination(
    request: ManagerRequest,
    onBack: () -> Unit,
) {
    var response by rememberSaveable { mutableStateOf("") }
    val showApproved = rememberSonrisasToast("Solicitud aprobada")
    val showRejected = rememberSonrisasToast("Solicitud rechazada")

    RequestReviewScreen(
        personName = request.personName,
        roleLabel = request.roleLabel,
        requestType = request.requestType,
        whenLabel = request.whenLabel,
        reason = request.reason,
        response = response,
        onResponseChange = { response = it },
        onApproveClick = {
            showApproved()
            onBack()
        },
        onRejectClick = {
            showRejected()
            onBack()
        },
        coverageWarning = request.coverageWarning,
        onBackClick = onBack,
    )
}

internal fun NavGraphBuilder.responsableRoutes(navController: NavHostController) {
    val back: () -> Unit = { navController.popBackStack() }

    pushComposable(ManagementRoutes.TODAY_LIST) {
        var filter by remember { mutableStateOf(TodayListFilter.Attendance) }
        TodayAttendanceScreen(
            dateLabel = "Jueves 8 de octubre",
            facilityName = "Arcoíris",
            entries = TodayEntries,
            selectedFilter = filter,
            onFilterSelected = { filter = it },
            onBackClick = back,
        )
    }

    incidenceRoutes(navController)
    areaRoutes(navController)

    pushComposable(ManagementRoutes.CORRECT_RECORD) {
        var reasonSel by remember { mutableStateOf<String?>("Olvidó registrar") }
        var noteSel by remember { mutableStateOf<String?>("Avisó por teléfono") }
        CorrectRecordScreen(
            personName = "Carmen López Vega",
            personDetail = "Cocina · miércoles 7 oct",
            entryTime = "6:58",
            exitTime = "15:00",
            reasonOptions = listOf("Olvidó registrar", "Falla de GPS", "Otro"),
            selectedReason = reasonSel,
            onReasonSelected = { reasonSel = it },
            noteOptions = listOf("Avisó por teléfono", "Lo confirmó su responsable", "Sin nota"),
            selectedNote = noteSel,
            onNoteSelected = { noteSel = it },
            onEntryClick = {},
            onExitClick = {},
            onSaveClick = back,
            exitHint = "La salida estaba vacía.",
            onBackClick = back,
        )
    }

    pushComposable(ManagementRoutes.ASSIGN_SHIFT) { AssignShiftDestination(onBack = back) }

    pushComposable(ManagementRoutes.NEW_STAFF) { entry ->
        NewStaffScreen(
            onBack = back,
            onSaveClick = { name, detail ->
                entry.ifResumed { navController.navigate(ManagementRoutes.newStaffInvite(name, detail)) }
            },
        )
    }

    pushComposable(
        route = ManagementRoutes.NEW_STAFF_INVITE,
        arguments = listOf(
            navArgument(ManagementRoutes.INVITE_NAME_ARG) {
                type = NavType.StringType
                defaultValue = ""
            },
            navArgument(ManagementRoutes.INVITE_DETAIL_ARG) {
                type = NavType.StringType
                defaultValue = ""
            },
        ),
    ) { entry ->
        InviteCodeScreen(
            recipientName = entry.arguments?.getString(ManagementRoutes.INVITE_NAME_ARG).orEmpty(),
            recipientDetail = entry.arguments?.getString(ManagementRoutes.INVITE_DETAIL_ARG).orEmpty(),
            // Ya se guardó: regresa a la lista de personal, no al formulario.
            onBack = {
                entry.ifResumed { navController.popBackStack(SonrisasTab.Personal.route, inclusive = false) }
            },
        )
    }
    pushComposable(ManagementRoutes.INVITE_CODE) { InviteCodeScreen(onBack = back) }
    pushComposable(ManagementRoutes.DEACTIVATE) { DeactivateStaffScreen(onBack = back, onSaveClick = back) }
    pushComposable(ManagementRoutes.TRANSFER_STAFF) { TransferStaffScreen(onBack = back, onSaveClick = back) }

    requestsRoutes(navController)

    pushComposable(ManagementRoutes.LOCATION_REVIEW) {
        LocationUnderReviewScreen(recordedTime = "7:51", onBackToHomeClick = back)
    }
}

// Lista de solicitudes y revisión de una solicitud.
private fun NavGraphBuilder.requestsRoutes(navController: NavHostController) {
    val back: () -> Unit = { navController.popBackStack() }

    pushComposable(ManagementRoutes.REQUESTS_LIST) { entry ->
        ManagerRequestsListScreen(
            requests = MockData.managerRequests,
            onBack = back,
            onRequestClick = { id ->
                entry.ifResumed { navController.navigate(ManagementRoutes.requestReview(id)) }
            },
        )
    }

    pushComposable(
        route = ManagementRoutes.REQUEST_REVIEW,
        arguments = listOf(navArgument(ManagementRoutes.REQUEST_ID_ARG) { type = NavType.IntType }),
    ) { entry ->
        val request = entry.arguments
            ?.getInt(ManagementRoutes.REQUEST_ID_ARG)
            ?.let { MockData.managerRequest(it) }

        if (request == null) {
            LaunchedEffect(Unit) { navController.popBackStack() }
        } else {
            RequestReviewDestination(request = request, onBack = { entry.ifResumed { navController.popBackStack() } })
        }
    }
}

private enum class ShiftTimeField(val title: String) {
    Entry("Hora de entrada"),
    Exit("Hora de salida"),
}

private const val ORIGINAL_AREA = "Maternales"

// Editar personal: entrada y salida con selector de 24 horas; rol y área con desplegable.
@Composable
private fun AssignShiftDestination(onBack: () -> Unit) {
    var staffName by rememberSaveable { mutableStateOf("María Torres Díaz") }
    var roleName by rememberSaveable { mutableStateOf("Maestra") }
    var areaName by rememberSaveable { mutableStateOf(ORIGINAL_AREA) }
    var entryTime by rememberSaveable { mutableStateOf("8:00") }
    var exitTime by rememberSaveable { mutableStateOf("16:00") }
    var picking by rememberSaveable { mutableStateOf<ShiftTimeField?>(null) }
    var days by remember {
        mutableStateOf(
            setOf(WeekDay.Monday, WeekDay.Tuesday, WeekDay.Wednesday, WeekDay.Thursday, WeekDay.Friday),
        )
    }
    val timeError = if (timeToMinutes(exitTime) <= timeToMinutes(entryTime)) {
        "La hora de salida debe ser posterior a la de entrada."
    } else {
        null
    }

    AssignShiftScreen(
        staffName = staffName,
        onStaffNameChange = { staffName = it },
        roleName = roleName,
        roleOptions = MockData.staffRoles,
        onRoleSelected = { roleName = it },
        selectedDays = days,
        onDayToggle = { day -> days = if (day in days) days - day else days + day },
        entryTime = entryTime,
        exitTime = exitTime,
        areaName = areaName,
        areaOptions = MockData.areas.map { it.name },
        onAreaSelected = { areaName = it },
        coverageWarning = if (areaName != ORIGINAL_AREA) {
            "Si mueves a María a $areaName, $ORIGINAL_AREA queda con 1 de 2 personas."
        } else {
            null
        },
        timeError = timeError,
        onEntryTimeClick = { picking = ShiftTimeField.Entry },
        onExitTimeClick = { picking = ShiftTimeField.Exit },
        onBackClick = onBack,
        onSaveClick = onBack,
    )

    picking?.let { field ->
        val minutes = timeToMinutes(if (field == ShiftTimeField.Entry) entryTime else exitTime)
        SonrisasTimePickerDialog(
            title = field.title,
            initialHour = minutes / MINUTES_PER_HOUR,
            initialMinute = minutes % MINUTES_PER_HOUR,
            onDismiss = { picking = null },
            onConfirm = { hour, minute ->
                val time = formatTime(hour, minute)
                if (field == ShiftTimeField.Entry) entryTime = time else exitTime = time
                picking = null
            },
        )
    }
}
