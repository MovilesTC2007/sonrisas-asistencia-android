package mx.tec.sonrisas.asistencia.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import mx.tec.sonrisas.asistencia.ui.components.AttendanceStatus
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.screens.AccountDetail
import mx.tec.sonrisas.asistencia.ui.screens.AreaMinimunScreen
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
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

// ============================================================================
// TEMPORAL: solo para ver las pantallas de Responsable y Empleado en la app.
// Para quitarlo: borra este archivo y las líneas marcadas con "// TEMP" en SonrisasApp.kt.
// ============================================================================

internal object TempRoutes {
    const val TODAY_LIST = "temp/lista-hoy"
    const val REGISTER_INCIDENCE = "temp/registrar-incidencia"
    const val CORRECT_RECORD = "temp/corregir-registro"
    const val ASSIGN_SHIFT = "temp/asignar-turno"
    const val REQUEST_REVIEW = "temp/revisar-solicitud"
    const val AREAS = "temp/areas-minimos"
    const val NEW_STAFF = "temp/alta-persona"
    const val INVITE_CODE = "temp/codigo-invitacion"
    const val DEACTIVATE = "temp/dar-de-baja"
    const val TRANSFER_STAFF = "temp/trasladar"
    const val PERMISSIONS = "temp/permisos-rol"
    const val LOCATION_REVIEW = "temp/ubicacion-revision"
    const val EMPLOYEE_PROFILE = "temp/perfil-empleada"
}

private data class TempEntry(val label: String, val route: String)

private val TempEntries = listOf(
    TempEntry("Revisar solicitud", TempRoutes.REQUEST_REVIEW),
    TempEntry("Lista de hoy", TempRoutes.TODAY_LIST),
    TempEntry("Registrar incidencia", TempRoutes.REGISTER_INCIDENCE),
    TempEntry("Corregir registro", TempRoutes.CORRECT_RECORD),
    TempEntry("Asignar turno", TempRoutes.ASSIGN_SHIFT),
    TempEntry("Áreas y mínimos", TempRoutes.AREAS),
    TempEntry("Alta de persona", TempRoutes.NEW_STAFF),
    TempEntry("Código de invitación", TempRoutes.INVITE_CODE),
    TempEntry("Dar de baja", TempRoutes.DEACTIVATE),
    TempEntry("Trasladar", TempRoutes.TRANSFER_STAFF),
    TempEntry("Permisos y rol", TempRoutes.PERMISSIONS),
    TempEntry("Empleada · Ubicación en revisión", TempRoutes.LOCATION_REVIEW),
    TempEntry("Empleada · Perfil", TempRoutes.EMPLOYEE_PROFILE),
)

private val TempTodayEntries = listOf(
    TodayAttendanceEntry("Ana Martínez", "Lactantes", "7:55", AttendanceStatus.OnTime),
    TodayAttendanceEntry("Lucía Hernández", "Lactantes", "7:58", AttendanceStatus.OnTime),
    TodayAttendanceEntry("Rosa Pérez", "Lactantes", "8:22", AttendanceStatus.MajorDelay),
    TodayAttendanceEntry("María Torres", "Maternales", "8:09", AttendanceStatus.MinorDelay),
    TodayAttendanceEntry("Sofía Ramírez", "Maternales", null, AttendanceStatus.Absent),
)

// Índice con todas las pantallas sueltas (se muestra en la pestaña Solicitudes del Responsable).
@Composable
internal fun TempScreensHub(onOpen: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.xl),
    ) {
        Spacer(Modifier.height(Spacing.md))
        SonrisasTopBar(title = "Pantallas (temporal)")
        Spacer(Modifier.height(Spacing.md))
        SonrisasListCard(items = TempEntries) { item ->
            Text(
                text = item.label,
                style = SonrisasTextStyle.Body,
                color = InkPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpen(item.route) }
                    .heightIn(min = 56.dp)
                    .padding(horizontal = Spacing.lg, vertical = Spacing.lg),
            )
        }
        Spacer(Modifier.height(Spacing.xxl))
    }
}

// Perfil de la Empleada (pestaña Perfil del rol Empleado).
@Composable
internal fun TempEmployeeProfile() {
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

// Nota: Áreas, Alta, Código, Baja, Traslado y Permisos todavía no reciben onBack;
// su flecha no hace nada, pero el botón atrás del teléfono sí regresa.
internal fun NavGraphBuilder.tempPreviewRoutes(navController: NavHostController) {
    val back: () -> Unit = { navController.popBackStack() }

    pushComposable(TempRoutes.TODAY_LIST) {
        var filter by remember { mutableStateOf(TodayListFilter.All) }
        TodayAttendanceScreen(
            dateLabel = "Jueves 8 de octubre",
            facilityName = "Arcoíris",
            entries = TempTodayEntries,
            selectedFilter = filter,
            onFilterSelected = { filter = it },
            onBackClick = back,
            onRegisterIncidenceClick = { navController.navigate(TempRoutes.REGISTER_INCIDENCE) },
        )
    }

    pushComposable(TempRoutes.REGISTER_INCIDENCE) {
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

    pushComposable(TempRoutes.CORRECT_RECORD) {
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

    pushComposable(TempRoutes.ASSIGN_SHIFT) {
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

    pushComposable(TempRoutes.REQUEST_REVIEW) {
        var response by remember { mutableStateOf("") }
        RequestReviewScreen(
            personName = "Fernando Anckerman Ramírez",
            roleLabel = "Maestro · Lactantes",
            requestType = "Permiso por horas",
            whenLabel = "Lunes 12 oct · 9:00 a 11:00",
            reason = "Tengo una cita médica de 9:00 a 11:00.",
            response = response,
            onResponseChange = { response = it },
            onApproveClick = back,
            onRejectClick = back,
            coverageWarning = "Lactantes queda con 2 de 3 personas de 9:00 a 11:00.",
            onBackClick = back,
        )
    }

    pushComposable(TempRoutes.AREAS) { AreaMinimunScreen() }
    pushComposable(TempRoutes.NEW_STAFF) { NewStaffScreen() }
    pushComposable(TempRoutes.INVITE_CODE) { InviteCodeScreen() }
    pushComposable(TempRoutes.DEACTIVATE) { DeactivateStaffScreen() }
    pushComposable(TempRoutes.TRANSFER_STAFF) { TransferStaffScreen() }
    pushComposable(TempRoutes.PERMISSIONS) { StaffPermissionsScreen() }
    pushComposable(TempRoutes.LOCATION_REVIEW) {
        LocationUnderReviewScreen(recordedTime = "7:51", onBackToHomeClick = back)
    }
    pushComposable(TempRoutes.EMPLOYEE_PROFILE) { TempEmployeeProfile() }
}
