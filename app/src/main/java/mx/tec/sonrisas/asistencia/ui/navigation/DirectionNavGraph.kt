package mx.tec.sonrisas.asistencia.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import mx.tec.sonrisas.asistencia.ui.screens.EstanciaFilter
import mx.tec.sonrisas.asistencia.ui.screens.EstanciasListScreen
import mx.tec.sonrisas.asistencia.ui.screens.filterEstancias

// Pestaña Estancias de Dirección: la ruta del grafo ES la ruta de la pestaña,
// para que la barra inferior pueda seguir  en lista y detalle.
@Suppress("UnusedParameter")
internal fun NavGraphBuilder.estanciasGraph(navController: NavHostController) {
    navigation(
        startDestination = SonrisasRoutes.ESTANCIAS_LIST,
        route = SonrisasTab.Estancias.route,
    ) {
        composable(SonrisasRoutes.ESTANCIAS_LIST) {
            EstanciasListDestination()
        }
    }
}

@Composable
private fun EstanciasListDestination() {
    var query by rememberSaveable { mutableStateOf("") }
    var filter by rememberSaveable { mutableStateOf(EstanciaFilter.All) }

    EstanciasListScreen(
        estancias = filterEstancias(MockData.estancias, query, filter),
        query = query,
        onQueryChange = { query = it },
        selectedFilter = filter,
        onFilterChange = { filter = it },
        onEstanciaClick = {},
    )
}
