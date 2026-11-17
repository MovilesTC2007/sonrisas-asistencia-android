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
import mx.tec.sonrisas.asistencia.ui.screens.EstanciaDetailScreen
import mx.tec.sonrisas.asistencia.ui.screens.EstanciaFilter
import mx.tec.sonrisas.asistencia.ui.screens.EstanciasListScreen
import mx.tec.sonrisas.asistencia.ui.screens.ManagementDashboardScreen
import mx.tec.sonrisas.asistencia.ui.screens.filterEstancias

// Pestaña Estancias de Dirección: la ruta del grafo ES la ruta de la pestaña,
// para que la barra inferior siga marcada en lista y detalle.
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
                    onViewReportsClick = { navController.navigateToTab(SonrisasTab.Reportes) },
                    onViewHistoryClick = { navController.navigateToTab(SonrisasTab.Historial) },
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

private const val REFRESH_MOCK_MS = 800L

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
        // Perfil (pantalla 15) todavía no existe: se conecta en su paso.
        onProfileClick = {},
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
