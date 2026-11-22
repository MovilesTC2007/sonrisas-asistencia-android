package mx.tec.sonrisas.asistencia.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.ChangeEventDetailScreen
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.ChangeHistoryScreen
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.ChangeKind
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.DireccionProfileScreen
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.EstanciaDetailScreen
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.EstanciaFilter
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.EstanciasListScreen
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.ExportContent
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.HistoryPeriod
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.ManagementDashboardScreen
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.PersonDetailScreen
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.ReportFormat
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.ReportPeriod
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.ReportType
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.ReportsScreen
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.ReportsView
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.StatisticsContent
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.StatsPeriod
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.TRANSFER_CODE_LENGTH
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.TransferAccountScreen
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.TransferStep
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.filterChanges
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.filterEstancias

private const val REFRESH_MOCK_MS = 800L

// Panel

@Composable
internal fun DireccionHomeDestination(
    navController: NavHostController,
    entry: NavBackStackEntry,
) {
    var isRefreshing by remember { mutableStateOf(false) }
    var updatedLabel by rememberSaveable { mutableStateOf(MockData.UPDATED_LABEL) }
    val scope = rememberCoroutineScope()

    ManagementDashboardScreen(
        directorName = MockData.DIRECTOR_NAME,
        dateLabel = MockData.TODAY_LABEL,
        updatedLabel = updatedLabel,
        isRefreshing = isRefreshing,
        onRefresh = {
            scope.launch {
                isRefreshing = true
                delay(REFRESH_MOCK_MS)
                updatedLabel = "Actualizado hace un momento"
                isRefreshing = false
            }
        },
        totalStaff = MockData.TOTAL_STAFF,
        presentToday = MockData.PRESENT_TODAY,
        hiresThisMonth = MockData.HIRES_THIS_MONTH,
        departuresThisMonth = MockData.DEPARTURES_THIS_MONTH,
        monthName = MockData.MONTH_NAME,
        alertCount = MockData.alertCount,
        worstCoverage = MockData.worstCoverage,
        totalEstancias = MockData.TOTAL_ESTANCIAS,
        weeklyAbsenteeism = MockData.weeklyAbsenteeism,
        onProfileClick = {
            entry.ifResumed { navController.navigate(SonrisasRoutes.DIRECCION_PROFILE) }
        },
        onAlertClick = {
            entry.ifResumed {
                navController.navigate(SonrisasRoutes.estanciasList(EstanciaFilter.WithAlert.name))
            }
        },
        onEstanciaClick = { id ->
            entry.ifResumed { navController.navigate(SonrisasRoutes.estanciaDetail(id)) }
        },
        onViewAllClick = {
            entry.ifResumed { navController.navigate(SonrisasRoutes.estanciasList()) }
        },
    )
}

// Estancias

// Pestaña Estancias de Dirección: la ruta del grafo ES la ruta de la pestaña,
// para que la barra inferior siga marcada en lista, detalle y persona.
internal fun NavGraphBuilder.estanciasGraph(navController: NavHostController) {
    navigation(
        startDestination = SonrisasRoutes.ESTANCIAS_LIST,
        route = SonrisasTab.Estancias.route,
    ) {
        composable(
            route = SonrisasRoutes.ESTANCIAS_LIST,
            arguments = listOf(
                navArgument(SonrisasRoutes.ESTANCIAS_FILTER_ARG) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
            ),
        ) { entry ->
            val filterName = entry.arguments?.getString(SonrisasRoutes.ESTANCIAS_FILTER_ARG)
            EstanciasListDestination(
                initialFilter = EstanciaFilter.entries.firstOrNull { it.name == filterName } ?: EstanciaFilter.All,
                onEstanciaClick = { id ->
                    entry.ifResumed { navController.navigate(SonrisasRoutes.estanciaDetail(id)) }
                },
            )
        }

        pushComposable(
            route = SonrisasRoutes.ESTANCIA_DETAIL,
            arguments = listOf(navArgument(SonrisasRoutes.ESTANCIA_ID_ARG) { type = NavType.IntType }),
        ) { entry ->
            val estancia = entry.arguments
                ?.getInt(SonrisasRoutes.ESTANCIA_ID_ARG)
                ?.let { MockData.estanciaDetail(it) }

            if (estancia == null) {
                LaunchedEffect(Unit) { navController.popBackStack() }
            } else {
                EstanciaDetailScreen(
                    estancia = estancia,
                    onBack = { entry.ifResumed { navController.popBackStack() } },
                    onPersonClick = { personId ->
                        entry.ifResumed { navController.navigate(SonrisasRoutes.personDetail(personId)) }
                    },
                    onViewReportsClick = {
                        entry.ifResumed {
                            navController.navigate(
                                SonrisasRoutes.reports(view = ReportsView.Export.name, estanciaId = estancia.id),
                            )
                        }
                    },
                    onViewHistoryClick = {
                        entry.ifResumed { navController.navigate(SonrisasRoutes.historyList(estancia.id)) }
                    },
                )
            }
        }

        pushComposable(
            route = SonrisasRoutes.PERSON_DETAIL,
            arguments = listOf(navArgument(SonrisasRoutes.PERSON_ID_ARG) { type = NavType.IntType }),
        ) { entry ->
            val person = entry.arguments
                ?.getInt(SonrisasRoutes.PERSON_ID_ARG)
                ?.let { MockData.personDetail(it) }

            if (person == null) {
                LaunchedEffect(Unit) { navController.popBackStack() }
            } else {
                PersonDetailScreen(
                    person = person,
                    onBack = { entry.ifResumed { navController.popBackStack() } },
                )
            }
        }
    }
}

@Composable
private fun EstanciasListDestination(
    initialFilter: EstanciaFilter,
    onEstanciaClick: (Int) -> Unit,
) {
    var query by rememberSaveable { mutableStateOf("") }
    var filter by rememberSaveable { mutableStateOf(initialFilter) }

    EstanciasListScreen(
        estancias = filterEstancias(MockData.estancias, query, filter),
        query = query,
        onQueryChange = { query = it },
        selectedFilter = filter,
        onFilterChange = { filter = it },
        onEstanciaClick = onEstanciaClick,
    )
}

// Reportes

// Pestaña Reportes de Dirección: igual que Estancias, la ruta del grafo es la de la de la pestaña.
internal fun NavGraphBuilder.reportesGraph(navController: NavHostController) {
    navigation(
        startDestination = SonrisasRoutes.REPORTS_HOME,
        route = SonrisasTab.Reportes.route,
    ) {
        composable(
            route = SonrisasRoutes.REPORTS_HOME,
            arguments = listOf(
                navArgument(SonrisasRoutes.REPORTS_VIEW_ARG) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument(SonrisasRoutes.REPORTS_ESTANCIA_ARG) {
                    type = NavType.IntType
                    defaultValue = SonrisasRoutes.NO_ESTANCIA
                },
            ),
        ) { entry ->
            val viewName = entry.arguments?.getString(SonrisasRoutes.REPORTS_VIEW_ARG)
            ReportsDestination(
                initialView = ReportsView.entries.firstOrNull { it.name == viewName } ?: ReportsView.Statistics,
                initialEstanciaId = entry.arguments?.getInt(SonrisasRoutes.REPORTS_ESTANCIA_ARG)
                    ?: SonrisasRoutes.NO_ESTANCIA,
                onEstanciaClick = { id ->
                    entry.ifResumed { navController.navigate(SonrisasRoutes.estanciaDetail(id)) }
                },
            )
        }
    }
}

@Composable
private fun ReportsDestination(
    initialView: ReportsView,
    initialEstanciaId: Int,
    onEstanciaClick: (Int) -> Unit,
) {
    var view by rememberSaveable { mutableStateOf(initialView) }
    var statsPeriod by rememberSaveable { mutableStateOf(StatsPeriod.Today) }
    var reportType by rememberSaveable { mutableStateOf(ReportType.Turnover) }
    var estancia by rememberSaveable { mutableStateOf(MockData.exportEstanciaLabel(initialEstanciaId)) }
    var exportPeriod by rememberSaveable { mutableStateOf(ReportPeriod.Month) }
    var format by rememberSaveable { mutableStateOf(ReportFormat.Pdf) }

    ReportsScreen(
        view = view,
        onViewChange = { view = it },
        offlineSince = MockData.offlineSince,
    ) {
        when (view) {
            ReportsView.Statistics -> StatisticsContent(
                period = statsPeriod,
                onPeriodChange = { statsPeriod = it },
                stats = MockData.statistics(statsPeriod),
                trend = MockData.weeklyAbsenteeism,
                ranking = MockData.ranking,
                alerts = MockData.stayAlerts,
                onEstanciaClick = onEstanciaClick,
            )

            ReportsView.Export -> ExportContent(
                reportType = reportType,
                onReportTypeChange = { reportType = it },
                estancia = estancia,
                estanciaOptions = MockData.exportEstanciaOptions,
                onEstanciaChange = { estancia = it },
                period = exportPeriod,
                onPeriodChange = { exportPeriod = it },
                format = format,
                onFormatChange = { format = it },
                canDownload = MockData.offlineSince == null,
                hasError = false,
                // La descarga real llega con el backend.
                onDownloadClick = {},
            )
        }
    }
}

// Historial
internal fun NavGraphBuilder.historialGraph(navController: NavHostController) {
    navigation(
        startDestination = SonrisasRoutes.HISTORY_LIST,
        route = SonrisasTab.Historial.route,
    ) {
        composable(
            route = SonrisasRoutes.HISTORY_LIST,
            arguments = listOf(
                navArgument(SonrisasRoutes.HISTORY_ESTANCIA_ARG) {
                    type = NavType.IntType
                    defaultValue = SonrisasRoutes.NO_ESTANCIA
                },
            ),
        ) { entry ->
            HistoryDestination(
                initialEstanciaId = entry.arguments?.getInt(SonrisasRoutes.HISTORY_ESTANCIA_ARG)
                    ?: SonrisasRoutes.NO_ESTANCIA,
                onEventClick = { id ->
                    entry.ifResumed { navController.navigate(SonrisasRoutes.historyEvent(id)) }
                },
            )
        }

        pushComposable(
            route = SonrisasRoutes.HISTORY_EVENT,
            arguments = listOf(navArgument(SonrisasRoutes.HISTORY_EVENT_ID_ARG) { type = NavType.IntType }),
        ) { entry ->
            val event = entry.arguments
                ?.getInt(SonrisasRoutes.HISTORY_EVENT_ID_ARG)
                ?.let { MockData.changeEvent(it) }

            if (event == null) {
                LaunchedEffect(Unit) { navController.popBackStack() }
            } else {
                ChangeEventDetailScreen(
                    event = event,
                    onBack = { entry.ifResumed { navController.popBackStack() } },
                )
            }
        }
    }
}

@Composable
private fun HistoryDestination(
    initialEstanciaId: Int,
    onEventClick: (Int) -> Unit,
) {
    var estancia by rememberSaveable { mutableStateOf(MockData.historyEstanciaLabel(initialEstanciaId)) }
    var period by rememberSaveable { mutableStateOf(HistoryPeriod.Week) }
    var kind by rememberSaveable { mutableStateOf<ChangeKind?>(null) }

    ChangeHistoryScreen(
        events = filterChanges(MockData.changeEvents, estancia, period, kind),
        estanciaOptions = MockData.historyEstanciaOptions,
        estancia = estancia,
        onEstanciaChange = { estancia = it },
        period = period,
        onPeriodChange = { period = it },
        kind = kind,
        onKindChange = { kind = it },
        onEventClick = onEventClick,
    )
}

// Perfil

// Sin barra inferior
internal fun NavGraphBuilder.direccionProfileRoutes(navController: NavHostController) {
    pushComposable(SonrisasRoutes.DIRECCION_PROFILE) { entry ->
        DireccionProfileScreen(
            profile = MockData.direccionProfile,
            onBack = { entry.ifResumed { navController.popBackStack() } },
            onTransferClick = {
                entry.ifResumed { navController.navigate(SonrisasRoutes.DIRECCION_TRANSFER) }
            },
            onLogoutClick = { navController.signOut() },
        )
    }

    pushComposable(SonrisasRoutes.DIRECCION_TRANSFER) { entry ->
        TransferAccountDestination(
            onBack = { entry.ifResumed { navController.popBackStack() } },
            onFinished = { navController.signOut() },
        )
    }
}

@Composable
private fun TransferAccountDestination(
    onBack: () -> Unit,
    onFinished: () -> Unit,
) {
    var step by rememberSaveable { mutableStateOf(TransferStep.Details) }
    var newName by rememberSaveable { mutableStateOf("") }
    var newEmail by rememberSaveable { mutableStateOf("") }
    // La contraseña y el código no se guardan en el estado restaurable a propósito.
    var password by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }

    val canContinue = when (step) {
        TransferStep.Details -> newName.isNotBlank() && newEmail.contains("@")
        TransferStep.Verify -> password.isNotBlank() && code.length == TRANSFER_CODE_LENGTH
        TransferStep.Done -> true
    }

    TransferAccountScreen(
        step = step,
        newName = newName,
        onNewNameChange = { newName = it },
        newEmail = newEmail,
        onNewEmailChange = { newEmail = it },
        password = password,
        onPasswordChange = { password = it },
        code = code,
        onCodeChange = { value -> code = value.filter(Char::isDigit).take(TRANSFER_CODE_LENGTH) },
        canContinue = canContinue,
        onContinue = {
            when (step) {
                TransferStep.Details -> step = TransferStep.Verify
                TransferStep.Verify -> step = TransferStep.Done
                TransferStep.Done -> onFinished()
            }
        },
        onBack = {
            if (step == TransferStep.Verify) step = TransferStep.Details else onBack()
        },
    )
}

// para volver a login y cerrar sesion
private fun NavHostController.signOut() {
    navigate(SonrisasRoutes.ACCESS_GRAPH) {
        popUpTo(SonrisasRoutes.MAIN_GRAPH) { inclusive = true }
    }
}
