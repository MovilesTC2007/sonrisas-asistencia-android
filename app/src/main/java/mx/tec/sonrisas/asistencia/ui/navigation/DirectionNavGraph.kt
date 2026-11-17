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
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import mx.tec.sonrisas.asistencia.ui.screens.EstanciaDetailScreen
import mx.tec.sonrisas.asistencia.ui.screens.EstanciaFilter
import mx.tec.sonrisas.asistencia.ui.screens.EstanciasListScreen
import mx.tec.sonrisas.asistencia.ui.screens.filterEstancias

// Pestaña Estancias de Dirección: la ruta del grafo ES la ruta de la pestaña,
// para que la barra inferior pueda seguir  en lista y detalle.
internal fun NavGraphBuilder.estanciasGraph(navController: NavHostController) {
    navigation(
        startDestination = SonrisasRoutes.ESTANCIAS_LIST,
        route = SonrisasTab.Estancias.route,
    ) {
        composable(SonrisasRoutes.ESTANCIAS_LIST) { entry ->
            EstanciasListDestination(
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
private fun EstanciasListDestination(onEstanciaClick: (Int) -> Unit) {
    var query by rememberSaveable { mutableStateOf("") }
    var filter by rememberSaveable { mutableStateOf(EstanciaFilter.All) }

    EstanciasListScreen(
        estancias = filterEstancias(MockData.estancias, query, filter),
        query = query,
        onQueryChange = { query = it },
        selectedFilter = filter,
        onFilterChange = { filter = it },
        onEstanciaClick = onEstanciaClick,
    )
}
