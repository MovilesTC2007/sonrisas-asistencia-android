package mx.tec.sonrisas.asistencia.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import mx.tec.sonrisas.asistencia.ui.screens.EstanciaDetailScreen

// Pestaña Estancias de Dirección: la ruta del grafo ES la ruta de la pestaña,
// para que la barra inferior pueda seguir  en lista y detalle.
internal fun NavGraphBuilder.estanciasGraph(navController: NavHostController) {
    navigation(
        startDestination = SonrisasRoutes.ESTANCIAS_LIST,
        route = SonrisasTab.Estancias.route,
    ) {
        composable(SonrisasRoutes.ESTANCIAS_LIST) {
            EstanciaDetailScreen()
        }
    }
}
