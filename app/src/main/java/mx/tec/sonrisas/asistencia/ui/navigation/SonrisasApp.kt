package mx.tec.sonrisas.asistencia.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mx.tec.sonrisas.asistencia.ui.components.SonrisasBottomBar
import mx.tec.sonrisas.asistencia.ui.screens.AttendanceConfirmationScreen
import mx.tec.sonrisas.asistencia.ui.screens.AttendanceScreen
import mx.tec.sonrisas.asistencia.ui.screens.ChangeHistoryScreen
import mx.tec.sonrisas.asistencia.ui.screens.ComingSoonScreen
import mx.tec.sonrisas.asistencia.ui.screens.CreateAccountScreen
import mx.tec.sonrisas.asistencia.ui.screens.DetailStaffScreen
import mx.tec.sonrisas.asistencia.ui.screens.EstanciaDetailScreen
import mx.tec.sonrisas.asistencia.ui.screens.ExportReportScreen
import mx.tec.sonrisas.asistencia.ui.screens.LocationRequestScreen
import mx.tec.sonrisas.asistencia.ui.screens.LoginScreen
import mx.tec.sonrisas.asistencia.ui.screens.ManagementDashboardScreen
import mx.tec.sonrisas.asistencia.ui.screens.ManagerHomeScreen
import mx.tec.sonrisas.asistencia.ui.screens.MyAttendanceScreen
import mx.tec.sonrisas.asistencia.ui.screens.RegisterStaffScreen
import mx.tec.sonrisas.asistencia.ui.screens.RequestType
import mx.tec.sonrisas.asistencia.ui.screens.RequestsScreen
import mx.tec.sonrisas.asistencia.ui.screens.ShiftStatus
import mx.tec.sonrisas.asistencia.ui.screens.StaffScreen
import mx.tec.sonrisas.asistencia.ui.screens.StatisticsScreen
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme


//MAIN CONTAINER
//IDEA BASE:
//primero Scaffold para los margenes de la app con huecos para las tarjetas y contenidos
//Parte central es el navhost donde se llaman las pantallas explicitamente pues
////despues por final es la navbar aqui se dibuja directo para no llamarlo por pantalla realmente
//El funcionamiento de este codigo es para el manejo de rutas entre cada pantalla

private const val NAV_ANIM_MS = 300 //screens Transition
private const val FADE_MS = 220 //mismo movimiento de transicion

@Composable
fun SonrisasApp(
    role: UserRole,
    modifier: Modifier = Modifier,
    startAtLogin: Boolean = true,
    navController: NavHostController = rememberNavController(),
) {
    //los siguientes comandos son para navegacion continua y actualizacion del Scaffold a tiempo real no fake yo no lo descargo porque ya lo tengo
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    //corroboracion de rol para su pantalla
    val tabs = role.tabs
    val currentTab = tabs.firstOrNull { it.route == currentRoute }
    var lastTab by rememberSaveable(role) { mutableStateOf(tabs.first()) }
    LaunchedEffect(currentTab) {
        if (currentTab != null) lastTab = currentTab
    }

    //para que no se anime al entrar la barra
    val showBottomBar = if (backStackEntry == null) !startAtLogin else currentTab != null

    Scaffold(
        modifier = modifier,
        containerColor = BgCream,
        bottomBar = {
            //animacion de entrada al cambio de pantalla, igual y la quito para mejorarla
            AnimatedVisibility(
                visible = showBottomBar,
                enter = expandVertically(tween(NAV_ANIM_MS), expandFrom = Alignment.Top) +
                    fadeIn(tween(NAV_ANIM_MS)),
                exit = shrinkVertically(tween(NAV_ANIM_MS), shrinkTowards = Alignment.Top) +
                    fadeOut(tween(NAV_ANIM_MS)),
            ) {
                SonrisasBottomBar(
                    items = tabs,
                    selected = currentTab ?: lastTab,
                    onItemClick = { tab -> navController.navigateToTab(tab) },
                    badgeCount = { tab ->
                        if (role == UserRole.Responsable && tab == SonrisasTab.Solicitudes) {
                            MockData.PENDING_REQUESTS
                        } else {
                            0
                        }
                    },
                )
            }
        },
    ) { innerPadding ->
        //para no consumir espacio extra tomamos el innerPadding para que se sopreponga sin consumirlo

        SonrisasNavHost(
            navController = navController,
            role = role,
            startAtLogin = startAtLogin,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
        )
    }
}

//MAPA DE RUTAS: este es el good para cambiar cuando agreguemos las nuevas pantallas faltantes
@Composable
private fun SonrisasNavHost(
    navController: NavHostController,
    role: UserRole,
    startAtLogin: Boolean,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = if (startAtLogin) SonrisasRoutes.ACCESS_GRAPH else SonrisasRoutes.MAIN_GRAPH,
        modifier = modifier,
        enterTransition = { fadeIn(tween(FADE_MS)) },
        exitTransition = { fadeOut(tween(FADE_MS)) },
    ) {
        accessGraph(navController = navController, role = role)
        mainGraph(navController = navController, role = role)
    }
}

private fun NavGraphBuilder.accessGraph(navController: NavHostController, role: UserRole) {
    navigation(startDestination = SonrisasRoutes.LOGIN, route = SonrisasRoutes.ACCESS_GRAPH) {

        composable(SonrisasRoutes.LOGIN) { entry ->
            LoginScreen(
                //esto es para el Mockup PARA QUE ENTRE CUALQUIER DATO DE CONTRASEÑA Y ASI igual se deja para cuando llamemos al back
                onLogin = { _, _ -> entry.ifResumed { navController.onSignedIn(role) } },
                onInviteCode = { entry.ifResumed { navController.navigate(SonrisasRoutes.CREATE_ACCOUNT) } },
            )
        }
        pushComposable(SonrisasRoutes.CREATE_ACCOUNT) { entry ->
            CreateAccountScreen(
                onBack = { entry.ifResumed { navController.popBackStack() } },
                onActivate = { _, _, _, _ -> entry.ifResumed { navController.onSignedIn(role) } },
            )
        }
        pushComposable(SonrisasRoutes.LOCATION_PERMISSION) { entry ->
            LocationRequestScreen(
                onBack = { entry.ifResumed { navController.popBackStack() } },
                onAllowLocation = {
                    entry.ifResumed {
                        navController.enterMainGraph()
                    }
                }, // considerar para el permiso de uso de Ubicacion, ahorita elimino el mensaje xd
            )
        }
    }
}

//REGISTRO DE LAS RUTAS ASIGNADAS POR ROL
private fun NavGraphBuilder.mainGraph(navController: NavHostController, role: UserRole) {
    navigation(startDestination = SonrisasTab.Inicio.route, route = SonrisasRoutes.MAIN_GRAPH) {

        composable(SonrisasTab.Inicio.route) { entry ->
            when (role) {
                UserRole.Empleado -> AttendanceScreen(
                    userName = MockData.EMPLOYEE_NAME,
                    shiftSchedule = MockData.SHIFT_SCHEDULE,
                    shiftRole = MockData.SHIFT_ROLE,
                    shiftStatus = ShiftStatus.NotClockedIn,
                    recentRecords = MockData.recentRecords,
                    isOnline = MockData.IS_ONLINE,
                    pendingRecordsCount = MockData.PENDING_RECORDS,
                    onClockActionClick = {
                        entry.ifResumed { navController.navigate(SonrisasRoutes.ATTENDANCE_CONFIRMATION) }
                    },
                )

                UserRole.Responsable -> ManagerHomeScreen(
                    managerName = MockData.MANAGER_NAME,
                    presentCount = MockData.PRESENT_COUNT,
                    delayedCount = MockData.DELAYED_COUNT,
                    absentCount = MockData.ABSENT_COUNT,
                    coverage = MockData.areaCoverage,
                    pendingRequestsCount = MockData.PENDING_REQUESTS,
                    onStaffClick = { navController.navigateToTab(SonrisasTab.Personal) },
                    onShiftsClick = { navController.navigateToTab(SonrisasTab.Turnos) },
                    onRequestsClick = { navController.navigateToTab(SonrisasTab.Solicitudes) },
                    onRegisterMultipleClick = {
                        entry.ifResumed { navController.navigate(SonrisasRoutes.REGISTER_STAFF) }
                    },
                )

                UserRole.Direccion -> ManagementDashboardScreen(
                    totalStaff = MockData.TOTAL_STAFF,
                    absencesToday = MockData.ABSENCES_TODAY,
                    hiresThisMonth = MockData.HIRES_THIS_MONTH,
                    departuresThisMonth = MockData.DEPARTURES_THIS_MONTH,
                    monthName = MockData.MONTH_NAME,
                    facilitiesCoverage = MockData.facilitiesCoverage,
                    weeklyAbsenteeism = MockData.weeklyAbsenteeism,
                )
            }
        }

        composable(SonrisasTab.Asistencias.route) {
            MyAttendanceScreen()
        }

        composable(SonrisasTab.Personal.route) { entry ->
            StaffScreen(
                onStaffClick = { staffId ->
                    entry.ifResumed { navController.navigate(SonrisasRoutes.staffDetail(staffId)) }
                },
            )
        }

        composable(SonrisasTab.Turnos.route) {
            ComingSoonScreen(
                title = "Turnos",
                icon = Icons.Outlined.Schedule,
                message = "Aquí verás las áreas, los mínimos de personal y los horarios de tu estancia.",
            )
        }

        composable(SonrisasTab.Solicitudes.route) {
            when (role) {
                UserRole.Responsable -> ComingSoonScreen(
                    title = "Solicitudes",
                    icon = Icons.Outlined.Description,
                    message = "Aquí revisarás y aprobarás las solicitudes de tu personal.",
                )
                UserRole.Empleado, UserRole.Direccion -> EmployeeRequestsDestination()
            }
        }

        composable(SonrisasTab.Estancias.route) {
            EstanciaDetailScreen()
        }

        composable(SonrisasTab.Historial.route) {
            ChangeHistoryScreen()
        }

        composable(SonrisasTab.Reportes.route) {
            StatisticsScreen(
                presentCount = MockData.STATS_PRESENT,
                expectedCount = MockData.STATS_EXPECTED,
                onTimeCount = MockData.STATS_ON_TIME,
                minorDelayCount = MockData.STATS_MINOR_DELAY,
                majorDelayCount = MockData.STATS_MAJOR_DELAY,
                absentCount = MockData.STATS_ABSENT,
                alerts = MockData.stayAlerts,
            )
        }
        composable(SonrisasTab.Perfil.route) {
            ComingSoonScreen(
                title = "Perfil",
                icon = Icons.Outlined.Person,
                message = "Aquí verás tus datos personales y podrás cerrar sesión.",
            )
        }

        pushComposable(SonrisasRoutes.ATTENDANCE_CONFIRMATION) { entry ->
            AttendanceConfirmationScreen(
                onBackToHome = {
                    entry.ifResumed {
                        navController.popBackStack(SonrisasTab.Inicio.route, inclusive = false)
                    }
                },
            )
        }

        pushComposable(SonrisasRoutes.EXPORT_REPORT) {
            ExportReportScreen()
        }

        pushComposable(SonrisasRoutes.REGISTER_STAFF) { entry ->
            RegisterStaffScreen(
                onBack = { entry.ifResumed { navController.popBackStack() } },
                //PARA Mockup
                onRegisterEntry = { _ -> entry.ifResumed { navController.popBackStack() } },
            )
        }
        pushComposable(
            route = SonrisasRoutes.STAFF_DETAIL,
            arguments = listOf(navArgument(SonrisasRoutes.STAFF_ID_ARG) { type = NavType.IntType }),
        ) { entry ->
            DetailStaffScreen(
                onBack = { entry.ifResumed { navController.popBackStack() } },
            )
        }
    }
}

@Composable
private fun EmployeeRequestsDestination() {
    var selectedType by rememberSaveable { mutableStateOf(RequestType.Permission) }
    var reason by rememberSaveable { mutableStateOf("") }

    RequestsScreen(
        selectedType = selectedType,
        dateText = MockData.REQUEST_DATE,
        reason = reason,
        history = MockData.requestHistory,
        onTypeSelected = { selectedType = it },
        onReasonChange = { reason = it },
        //Mockup
        onSendClick = { reason = "" },
    )
}

//SECCION DE MANEJO DE PESTAÑAS good
//OBTENIDO DE: https://developer.android.com/guide/navigation/backstack/multi-back-stacks?hl=es-419
//LA IDEA ES QUE el botón "atrás" desde cualquier pestaña regresa a Inicio y desde Inicio sale de la app.
internal fun NavHostController.navigateToTab(tab: SonrisasTab) {
    navigate(tab.route) {
        popUpTo(SonrisasTab.Inicio.route) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

private fun NavHostController.onSignedIn(role: UserRole) {
    when (role) {
        UserRole.Empleado -> navigate(SonrisasRoutes.LOCATION_PERMISSION)
        UserRole.Responsable, UserRole.Direccion -> enterMainGraph()
    }
}


private fun NavHostController.enterMainGraph() {
    navigate(SonrisasRoutes.MAIN_GRAPH) {
        popUpTo(SonrisasRoutes.ACCESS_GRAPH) { inclusive = true }
    }
}

private inline fun NavBackStackEntry.ifResumed(action: () -> Unit) {
    if (lifecycle.currentState == Lifecycle.State.RESUMED) action()
}
private fun NavGraphBuilder.pushComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = route,
        arguments = arguments,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start, tween(NAV_ANIM_MS)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End, tween(NAV_ANIM_MS)
            )
        },
        content = content,
    )
}

//CASOS DE ROLES DE CANELA

@Preview(name = "App · Empleado", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun SonrisasAppEmpleadoPreview() {
    SonrisasAsistenciasTheme {
        SonrisasApp(role = UserRole.Empleado, startAtLogin = false)
    }
}

@Preview(name = "App · Responsable", showBackground = true, widthDp = 360, heightDp = 900)
@Composable
private fun SonrisasAppResponsablePreview() {
    SonrisasAsistenciasTheme {
        SonrisasApp(role = UserRole.Responsable, startAtLogin = false)
    }
}

@Preview(name = "App · Dirección", showBackground = true, widthDp = 360, heightDp = 1000)
@Composable
private fun SonrisasAppDireccionPreview() {
    SonrisasAsistenciasTheme {
        SonrisasApp(role = UserRole.Direccion, startAtLogin = false)
    }
}

@Preview(name = "App · Arranque en login", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun SonrisasAppLoginPreview() {
    SonrisasAsistenciasTheme {
        SonrisasApp(role = UserRole.Empleado)
    }
}
