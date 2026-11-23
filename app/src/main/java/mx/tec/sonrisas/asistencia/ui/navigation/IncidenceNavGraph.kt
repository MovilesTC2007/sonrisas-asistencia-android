package mx.tec.sonrisas.asistencia.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import mx.tec.sonrisas.asistencia.ui.components.AttendanceStatus
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTimePickerDialog
import mx.tec.sonrisas.asistencia.ui.screens.responsableScreens.IncidenceCandidate
import mx.tec.sonrisas.asistencia.ui.screens.responsableScreens.IncidenceStaffListScreen
import mx.tec.sonrisas.asistencia.ui.screens.responsableScreens.IncidenceType
import mx.tec.sonrisas.asistencia.ui.screens.responsableScreens.RegisterIncidenceScreen

// Registrar incidencia: primero se elige a la persona y después se captura la incidencia.

private val IncidentCategories = listOf("Accidente", "Salud", "Conducta", "Otro")

internal fun NavGraphBuilder.incidenceRoutes(navController: NavHostController) {
    pushComposable(ManagementRoutes.INCIDENCE_LIST) { entry ->
        IncidenceStaffListScreen(
            candidates = MockData.incidenceCandidates,
            onBack = { entry.ifResumed { navController.popBackStack() } },
            onPersonClick = { id ->
                entry.ifResumed { navController.navigate(ManagementRoutes.registerIncidence(id)) }
            },
        )
    }

    pushComposable(
        route = ManagementRoutes.REGISTER_INCIDENCE,
        arguments = listOf(navArgument(ManagementRoutes.PERSON_ID_ARG) { type = NavType.IntType }),
    ) { entry ->
        val candidate = entry.arguments
            ?.getInt(ManagementRoutes.PERSON_ID_ARG)
            ?.let { MockData.incidenceCandidate(it) }

        if (candidate == null) {
            LaunchedEffect(Unit) { navController.popBackStack() }
        } else {
            RegisterIncidenceDestination(
                candidate = candidate,
                onBack = { entry.ifResumed { navController.popBackStack() } },
            )
        }
    }
}

@Composable
private fun RegisterIncidenceDestination(
    candidate: IncidenceCandidate,
    onBack: () -> Unit,
) {
    var type by rememberSaveable { mutableStateOf(initialType(candidate.status)) }
    var arrival by rememberSaveable { mutableStateOf(candidate.arrivalTime ?: candidate.assignedTime) }
    var reason by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf<String?>(null) }
    var showTimePicker by rememberSaveable { mutableStateOf(false) }

    RegisterIncidenceScreen(
        personName = candidate.name,
        personDetail = "${candidate.detail} · hoy",
        selectedType = type,
        onTypeSelected = { type = it },
        assignedTime = candidate.assignedTime,
        arrivalTime = arrival,
        minutesLate = (timeToMinutes(arrival) - timeToMinutes(candidate.assignedTime)).coerceAtLeast(0),
        reason = reason,
        onReasonChange = { reason = it },
        onArrivalTimeClick = { showTimePicker = true },
        onSaveClick = onBack,
        onBackClick = onBack,
        incidentCategories = IncidentCategories,
        selectedIncidentCategory = category,
        onIncidentCategorySelected = { category = it },
    )

    if (showTimePicker) {
        val minutes = timeToMinutes(arrival)
        SonrisasTimePickerDialog(
            title = "Hora de llegada",
            initialHour = minutes / MINUTES_PER_HOUR,
            initialMinute = minutes % MINUTES_PER_HOUR,
            onDismiss = { showTimePicker = false },
            onConfirm = { hour, minute ->
                arrival = formatTime(hour, minute)
                showTimePicker = false
            },
        )
    }
}

private fun initialType(status: AttendanceStatus): IncidenceType = when (status) {
    AttendanceStatus.Absent -> IncidenceType.Absence
    AttendanceStatus.OnTime -> IncidenceType.Incident
    else -> IncidenceType.Delay
}
