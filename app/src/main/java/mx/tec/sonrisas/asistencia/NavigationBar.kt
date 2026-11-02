package mx.tec.sonrisas.asistencia
import android.net.http.SslCertificate.restoreState
import android.net.http.SslCertificate.saveState
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Addchart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TextSnippet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState

data class NavButton(
    val label: String,
    val icon: ImageVector,
    val route: String,
)

object Constants {
    val buttons = listOf(
        NavButton("Inicio", Icons.Default.Home, "inicio"), // 0
        NavButton("Asistencias", Icons.Default.CalendarMonth, "asistencias"), // 1
        NavButton("Solicitudes", Icons.Default.TextSnippet, "solicitudes"), // 2
        NavButton("Perfil", Icons.Default.Person, "perfil"), // 3
        NavButton("Personal", Icons.Default.People, "personal"), // 4
        NavButton("Turnos", Icons.Default.AccessTime, "turnos"), // 5
        NavButton("Métricas", Icons.Default.Addchart, "metricas"), // 6
    )
}

// 1 = Presidente, 2 = Responsable, 3 = Coordinador, 4 = Empleado

@Preview(showBackground = true)
@Composable
fun AppBottomBar(modifier: Modifier = Modifier, role: Int = 2) {
    val userButtons: List<NavButton> = when (role) {
        1 -> listOf(6)
        2, 3 -> listOf(0, 4, 5, 2)
        4 -> listOf(0, 1, 2, 3)
        else -> emptyList()
    }.map { Constants.buttons[it] }

    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                userButtons.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        if(userButtons.isNotEmpty()) {
            NavHost(
                navController = navController,
                startDestination = userButtons[0].route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("inicio") { Text("Inicio") }
                composable("asistencias") { Text("Asistencias") }
                composable("solicitudes") { Text("Solicitudes") }
                composable("perfil") { Text("Perfil") }
                composable("personal") { Text("Personal") }
                composable("turnos") { Text("Turnos") }
                composable("metricas") { Text("Métricas") }
            }
        }
    }
}