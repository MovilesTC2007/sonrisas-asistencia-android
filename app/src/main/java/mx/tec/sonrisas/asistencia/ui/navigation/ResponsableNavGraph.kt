package mx.tec.sonrisas.asistencia.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import mx.tec.sonrisas.asistencia.ui.components.AttendanceStatus
import mx.tec.sonrisas.asistencia.ui.screens.AccountDetail
import mx.tec.sonrisas.asistencia.ui.screens.AssignShiftScreen
import mx.tec.sonrisas.asistencia.ui.screens.CorrectRecordScreen
import mx.tec.sonrisas.asistencia.ui.screens.DeactivateStaffScreen
import mx.tec.sonrisas.asistencia.ui.screens.IncidenceType
import mx.tec.sonrisas.asistencia.ui.screens.InviteCodeScreen
import mx.tec.sonrisas.asistencia.ui.screens.LocationUnderReviewScreen
import mx.tec.sonrisas.asistencia.ui.screens.NewStaffScreen
import mx.tec.sonrisas.asistencia.ui.screens.RegisterIncidenceScreen
import mx.tec.sonrisas.asistencia.ui.screens.RequestReviewScreen
import mx.tec.sonrisas.asistencia.ui.screens.ShiftScheduleType
import mx.tec.sonrisas.asistencia.ui.screens.StaffPermissionsScreen
import mx.tec.sonrisas.asistencia.ui.screens.TodayAttendanceEntry
import mx.tec.sonrisas.asistencia.ui.screens.TodayAttendanceScreen
import mx.tec.sonrisas.asistencia.ui.screens.TodayListFilter
import mx.tec.sonrisas.asistencia.ui.screens.TransferStaffScreen
import mx.tec.sonrisas.asistencia.ui.screens.UserProfileScreen
import mx.tec.sonrisas.asistencia.ui.screens.WeekDay

internal object ManagementRoutes {
    const val TODAY_LIST = "responsable/lista-hoy"
    const val REGISTER_INCIDENCE = "responsable/registrar-incidencia"
    const val CORRECT_RECORD = "responsable/corregir-registro"
    const val ASSIGN_SHIFT = "responsable/asignar-turno"
    const val NEW_STAFF = "responsable/alta-persona"
    const val INVITE_CODE = "responsable/codigo-invitacion"
    const val DEACTIVATE = "responsable/dar-de-baja"
    const val TRANSFER_STAFF = "responsable/trasladar"
    const val PERMISSIONS = "responsable/permisos-rol"
    const val LOCATION_REVIEW = "responsable/ubicacion-revision"
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
internal fun ManagerProfileDestination() {
    UserProfileScreen(
        fullName = "Elena Ríos Salazar",
        roleSummary = "Responsable · Arcoíris",
        details = listOf(
            AccountDetail(label = "Correo", value = "elena@gmail.com"),
            AccountDetail(label = "Estancia", value = "Arcoíris"),
        ),
        locationUsageText = "Solo al registrar",
    )
}

// Pestaña Solicitudes del rol Responsable.
@Composable
internal fun ManagerRequestsDestination(onBack: () -> Unit) {
    var response by remember { mutableStateOf("") }
    RequestReviewScreen(
        personName = "Fernando Anckerman Ramírez",
        roleLabel = "Maestro · Lactantes",
        requestType = "Permiso por horas",
        whenLabel = "Lunes 12 oct · 9:00 a 11:00",
        reason = "Tengo una cita médica de 9:00 a 11:00.",
        response = response,
        onResponseChange = { response = it },
        onApproveClick = onBack,
        onRejectClick = onBack,
        coverageWarning = "Lactantes queda con 2 de 3 personas de 9:00 a 11:00.",
        onBackClick = onBack,
    )
}

// Nota: Alta, Código, Baja, Traslado y Permisos todavía no reciben onBack;
// su flecha no hace nada, pero el botón atrás del teléfono sí regresa.
internal fun NavGraphBuilder.responsableRoutes(navController: NavHostController) {
    val back: () -> Unit = { navController.popBackStack() }

    pushComposable(ManagementRoutes.TODAY_LIST) {
        var filter by remember { mutableStateOf(TodayListFilter.All) }
        TodayAttendanceScreen(
            dateLabel = "Jueves 8 de octubre",
            facilityName = "Arcoíris",
            entries = TodayEntries,
            selectedFilter = filter,
            onFilterSelected = { filter = it },
            onBackClick = back,
            onRegisterIncidenceClick = { navController.navigate(ManagementRoutes.REGISTER_INCIDENCE) },
        )
    }

    pushComposable(ManagementRoutes.REGISTER_INCIDENCE) {
        var type by remember { mutableStateOf(IncidenceType.Delay) }
        var reason by remember { mutableStateOf("Tráfico por obras en Av. Constitución.") }
        RegisterIncidenceScreen(
            personName = "Rosa Pérez García",
            personDetail = "Auxiliar · Lactantes · jueves 8 oct",
            selectedType = type,
            onTypeSelected = { type = it },
            assignedTime = "8:00",
            arrivalTime = "8:22",
            minutesLate = 22,
            reason = reason,
            onReasonChange = { reason = it },
            onArrivalTimeClick = {},
            onSaveClick = back,
            onBackClick = back,
        )
    }

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

    pushComposable(ManagementRoutes.ASSIGN_SHIFT) {
        var scheduleType by remember { mutableStateOf(ShiftScheduleType.Fixed) }
        var days by remember {
            mutableStateOf(
                setOf(WeekDay.Monday, WeekDay.Tuesday, WeekDay.Wednesday, WeekDay.Thursday, WeekDay.Friday),
            )
        }
        AssignShiftScreen(
            staffName = "María Torres Díaz",
            staffRoleSummary = "Maestra · Maternales",
            scheduleType = scheduleType,
            onScheduleTypeChange = { scheduleType = it },
            selectedDays = days,
            onDayToggle = { day -> days = if (day in days) days - day else days + day },
            entryTime = "8:00",
            exitTime = "16:00",
            areaName = "Preescolar",
            coverageWarning = "Si mueves a María a Preescolar, Maternales queda con 1 de 2 personas.",
            onBackClick = back,
            onSaveClick = back,
        )
    }

    pushComposable(ManagementRoutes.NEW_STAFF) { NewStaffScreen() }
    pushComposable(ManagementRoutes.INVITE_CODE) { InviteCodeScreen() }
    pushComposable(ManagementRoutes.DEACTIVATE) { DeactivateStaffScreen() }
    pushComposable(ManagementRoutes.TRANSFER_STAFF) { TransferStaffScreen() }
    pushComposable(ManagementRoutes.PERMISSIONS) { StaffPermissionsScreen() }
    pushComposable(ManagementRoutes.LOCATION_REVIEW) {
        LocationUnderReviewScreen(recordedTime = "7:51", onBackToHomeClick = back)
    }
}
