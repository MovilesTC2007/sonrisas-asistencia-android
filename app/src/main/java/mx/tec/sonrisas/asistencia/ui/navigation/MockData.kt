package mx.tec.sonrisas.asistencia.ui.navigation

import mx.tec.sonrisas.asistencia.ui.components.AttendanceStatus
import mx.tec.sonrisas.asistencia.ui.components.CoverageLevel
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.ALL_ESTANCIAS
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.ChangeEvent
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.ChangeKind
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.DireccionProfile
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.EstanciaDetail
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.EstanciaListItem
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.PersonDetail
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.PersonRecord
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.SampleEstanciaDetail
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.StatisticsSummary
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.StatsPeriod
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.StayAlert
import mx.tec.sonrisas.asistencia.ui.screens.direccionScreens.WeekdayAbsenteeism
import mx.tec.sonrisas.asistencia.ui.screens.empleadoScreens.AttendanceRecord
import mx.tec.sonrisas.asistencia.ui.screens.empleadoScreens.RequestHistoryItem
import mx.tec.sonrisas.asistencia.ui.screens.empleadoScreens.RequestStatus
import mx.tec.sonrisas.asistencia.ui.screens.responsableScreens.AreaCoverage

// ESTO SE VA A DEJAR MIENTRAS AGREGAMOS EL BACKEND CORRECTAMENTE, MIENTRAS TANTO PARTE DE LOS DATOS SE TOMARAN DUMMY DESDE AQUI
// Regla de detekt (ClassOrdering): primero todas las propiedades y al final todas las funciones.
internal object MockData {

    // Empleado
    const val EMPLOYEE_NAME = "Fernando"
    const val SHIFT_SCHEDULE = "8:00 – 16:00"
    const val SHIFT_ROLE = "Maestra · Lactantes"
    const val IS_ONLINE = true
    const val PENDING_RECORDS = 0
    val recentRecords = listOf(
        AttendanceRecord("Mié 7 oct", "7:58 – 16:02", AttendanceStatus.OnTime),
        AttendanceRecord("Mar 6 oct", "8:08 – 16:01", AttendanceStatus.MinorDelay),
        AttendanceRecord("Lun 5 oct", "7:55 – 16:00", AttendanceStatus.OnTime),
        AttendanceRecord("Vie 2 oct", "Sin registro", AttendanceStatus.Absent),
        AttendanceRecord("Jue 1 oct", "7:57 – 16:00", AttendanceStatus.OnTime),
    )

    const val REQUEST_DATE = "12 de octubre de 2026"
    val requestHistory = listOf(
        RequestHistoryItem("Permiso · 12 oct", RequestStatus.Pending),
        RequestHistoryItem("Cambio de turno · 25 sep", RequestStatus.Approved),
        RequestHistoryItem("Falta · 10 ago", RequestStatus.Rejected),
    )

    // Responsable
    const val MANAGER_NAME = "Elena"
    const val PRESENT_COUNT = 17
    const val DELAYED_COUNT = 2
    const val ABSENT_COUNT = 1
    const val PENDING_REQUESTS = 2
    val areaCoverage = listOf(
        AreaCoverage("Lactantes", 3, 3),
        AreaCoverage("Maternales", 1, 2),
        AreaCoverage("Preescolar", 5, 5),
        AreaCoverage("Apoyo", 7, 8),
    )

    // Dirección
    const val DIRECTOR_NAME = "Patricia Gómez Ríos"
    const val TODAY_LABEL = "Martes 17 de noviembre"
    const val UPDATED_LABEL = "Actualizado hace 2 min"
    const val TOTAL_STAFF = 480
    const val PRESENT_TODAY = 442
    const val HIRES_THIS_MONTH = 4
    const val DEPARTURES_THIS_MONTH = 2
    const val MONTH_NAME = "noviembre"
    const val TOTAL_ESTANCIAS = 62

    // null = hay conetion
    val offlineSince: String? = null

    val weeklyAbsenteeism = listOf(
        WeekdayAbsenteeism("Lun", 7.1f),
        WeekdayAbsenteeism("Mar", 6.0f),
        WeekdayAbsenteeism("Mié", 7.1f),
        WeekdayAbsenteeism("Jue", 7.1f),
        WeekdayAbsenteeism("Vie", 7.1f),
    )

    val stayAlerts = listOf(
        StayAlert("Estancia Arcoíris", "Personal insuficiente"),
        StayAlert("Estancia Las Rosas", "Personal insuficiente"),
        StayAlert("Estancia Los Girasoles", "Personal insuficiente"),
    )

    val estancias = listOf(
        EstanciaListItem(1, "Arcoíris", "Monterrey, N.L.", 17, 18),
        EstanciaListItem(2, "Rayito de Sol", "Guadalupe, N.L.", 16, 16),
        EstanciaListItem(3, "Los Girasoles", "Apodaca, N.L.", 14, 18),
        EstanciaListItem(4, "Luna Nueva", "San Nicolás, N.L.", 15, 15),
        EstanciaListItem(5, "Amanecer", "Escobedo, N.L.", 16, 17),
    )

    // Derivadas de estancias
    val ranking = estancias.sortedByDescending { it.presentCount.toFloat() / it.requiredCount }

    val worstCoverage = estancias
        .sortedBy { it.presentCount.toFloat() / it.requiredCount }
        .take(5)

    val alertCount = estancias.count { it.level != CoverageLevel.Complete }

    val exportEstanciaOptions = listOf("Todas ($TOTAL_ESTANCIAS)") + estancias.map { it.name }

    val historyEstanciaOptions = listOf(ALL_ESTANCIAS) + estancias.map { it.name }

    val direccionProfile = DireccionProfile(
        fullName = DIRECTOR_NAME,
        email = "direccion@sonrisasdecristal.org",
        scope = "Todas las estancias ($TOTAL_ESTANCIAS)",
        trustedDevices = 2,
    )

    val personRecords = listOf(
        PersonRecord("Lun 16 nov", "Entrada 7:55 · Salida 16:02", AttendanceStatus.OnTime),
        PersonRecord("Vie 13 nov", "Entrada 8:12 · Salida 16:00", AttendanceStatus.MinorDelay),
        PersonRecord("Jue 12 nov", "Entrada 8:00 · Salida 16:00", AttendanceStatus.OnTime),
        PersonRecord("Mié 11 nov", "Sin registro", AttendanceStatus.Absent),
        PersonRecord("Mar 10 nov", "Entrada 7:58 · Salida 16:01", AttendanceStatus.OnTime),
    )

    val changeEvents = listOf(
        ChangeEvent(
            id = 1,
            kind = ChangeKind.Correction,
            description = "Elena Ruiz corrigió la salida de Carmen López",
            estancia = "Arcoíris",
            time = "hoy 9:14",
            daysAgo = 0,
            actor = "Elena Ruiz",
            oldValue = "Salida: sin registro",
            newValue = "Salida: 15:00",
            reason = "Olvidó registrar la salida",
        ),
        ChangeEvent(
            id = 2,
            kind = ChangeKind.Delay,
            description = "Elena Ruiz registró retardo mayor de Rosa Pérez",
            estancia = "Arcoíris",
            time = "hoy 8:40",
            daysAgo = 0,
            actor = "Elena Ruiz",
            oldValue = "Entrada asignada: 8:00",
            newValue = "Llegó 8:22 · retardo mayor",
            reason = "Tráfico por obras en Av. Constitución.",
        ),
        ChangeEvent(
            id = 3,
            kind = ChangeKind.GroupEntry,
            description = "Lucía Hernández registró la entrada de 6 personas",
            estancia = "Arcoíris",
            time = "hoy 7:58",
            daysAgo = 0,
            actor = "Lucía Hernández",
        ),
        ChangeEvent(
            id = 4,
            kind = ChangeKind.Removal,
            description = "Mónica Salinas dio de baja a Laura Ortiz",
            estancia = "Los Girasoles",
            time = "ayer 17:20",
            daysAgo = 1,
            actor = "Mónica Salinas",
            oldValue = "Activa",
            newValue = "Baja",
            reason = "Renuncia",
        ),
        ChangeEvent(
            id = 5,
            kind = ChangeKind.Location,
            description = "El sistema marcó una ubicación para revisión",
            estancia = "Luna Nueva",
            time = "ayer 7:51",
            daysAgo = 1,
            actor = "Sistema",
            newValue = "Entrada en revisión",
            reason = "Ubicación fuera del rango de la estancia",
        ),
        ChangeEvent(
            id = 6,
            kind = ChangeKind.Correction,
            description = "Sandra Vega corrigió la entrada de Julia Morales",
            estancia = "Luna Nueva",
            time = "vie 13 nov 8:12",
            daysAgo = 4,
            actor = "Sandra Vega",
            oldValue = "Entrada: 8:30",
            newValue = "Entrada: 8:05",
            reason = "Falla de GPS al registrar",
        ),
        ChangeEvent(
            id = 7,
            kind = ChangeKind.Addition,
            description = "Mónica Salinas dio de alta a Paola Ríos",
            estancia = "Los Girasoles",
            time = "mié 28 oct 10:05",
            daysAgo = 20,
            actor = "Mónica Salinas",
            newValue = "Alta · Lactantes",
        ),
        ChangeEvent(
            id = 8,
            kind = ChangeKind.Access,
            description = "Patricia Gómez inició sesión",
            estancia = null,
            time = "hoy 7:30",
            daysAgo = 0,
            actor = "Patricia Gómez",
            device = "Pixel 7 · Monterrey, N.L.",
        ),
        ChangeEvent(
            id = 9,
            kind = ChangeKind.Access,
            description = "Patricia Gómez exportó un reporte de rotación",
            estancia = null,
            time = "ayer 12:10",
            daysAgo = 1,
            actor = "Patricia Gómez",
            device = "Pixel 7 · Monterrey, N.L.",
        ),
        ChangeEvent(
            id = 10,
            kind = ChangeKind.Access,
            description = "Intento de acceso desde un teléfono nuevo",
            estancia = null,
            time = "dom 15 nov 22:03",
            daysAgo = 2,
            actor = "Sistema",
            device = "Teléfono no reconocido",
            reason = "Se pidió código de verificación (2FA)",
        ),
    )

    // Funciones
    fun exportEstanciaLabel(estanciaId: Int): String =
        estancias.firstOrNull { it.id == estanciaId }?.name ?: exportEstanciaOptions.first()

    fun historyEstanciaLabel(estanciaId: Int): String =
        estancias.firstOrNull { it.id == estanciaId }?.name ?: ALL_ESTANCIAS

    fun statistics(period: StatsPeriod): StatisticsSummary = when (period) {
        StatsPeriod.Today -> StatisticsSummary(442, 480, 419, 15, 8, 38)
        StatsPeriod.Week -> StatisticsSummary(448, 480, 425, 14, 9, 32)
        StatsPeriod.Month -> StatisticsSummary(455, 480, 432, 13, 10, 25)
    }

    // Mientras no haya backend, todas las estancias comparten el mismo detalle de muestra,
    // con su nombre, municipio y cifras propias.
    fun estanciaDetail(id: Int): EstanciaDetail? = estancias.firstOrNull { it.id == id }?.let { item ->
        SampleEstanciaDetail.copy(
            id = item.id,
            name = item.name,
            city = item.city,
            present = item.presentCount,
            total = item.requiredCount,
        )
    }

    fun changeEvent(id: Int): ChangeEvent? = changeEvents.firstOrNull { it.id == id }

    fun personDetail(personId: Int): PersonDetail? =
        SampleEstanciaDetail.people.firstOrNull { it.id == personId }?.let { person ->
            PersonDetail(
                id = person.id,
                name = person.name,
                role = "Maestra",
                estancia = SampleEstanciaDetail.name,
                area = person.area,
                todayStatus = person.status,
                todayDetail = person.clockIn?.let { "Entrada $it" } ?: "Sin registro de entrada",
                month = "Noviembre",
                onTimeCount = 12,
                delayCount = 2,
                absenceCount = if (person.status == AttendanceStatus.Absent) 2 else 0,
                records = personRecords,
            )
        }
}
