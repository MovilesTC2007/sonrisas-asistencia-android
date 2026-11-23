package mx.tec.sonrisas.asistencia.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import mx.tec.sonrisas.asistencia.ui.screens.responsableScreens.AreaMinimunScreen
import mx.tec.sonrisas.asistencia.ui.screens.responsableScreens.NewAreaScreen
import mx.tec.sonrisas.asistencia.ui.screens.responsableScreens.StaffArea

// Áreas y mínimos (pestaña Turnos del Responsable) y la pantalla para agregar un área.

// Pestaña Turnos: los mínimos se editan directo sobre la lista compartida de MockData.areas.
@Composable
internal fun AreasDestination(onAddAreaClick: () -> Unit) {
    AreaMinimunScreen(
        areas = MockData.areas,
        onMinimumChange = { index, value ->
            MockData.areas[index] = MockData.areas[index].copy(minimum = value)
        },
        onAddAreaClick = onAddAreaClick,
    )
}

internal fun NavGraphBuilder.areaRoutes(navController: NavHostController) {
    pushComposable(ManagementRoutes.NEW_AREA) { entry ->
        NewAreaScreen(
            existingNames = MockData.areas.map { it.name },
            onBack = { entry.ifResumed { navController.popBackStack() } },
            onSave = { name, detail, minimum ->
                MockData.areas.add(StaffArea(name = name, detail = detail, minimum = minimum))
                entry.ifResumed { navController.popBackStack() }
            },
        )
    }
}
