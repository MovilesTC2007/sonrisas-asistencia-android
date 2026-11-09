package mx.tec.sonrisas.asistencia.ui.navigation

import mx.tec.sonrisas.asistencia.ui.components.AttendanceStatus
import mx.tec.sonrisas.asistencia.ui.screens.AreaCoverage
import mx.tec.sonrisas.asistencia.ui.screens.AttendanceRecord
import mx.tec.sonrisas.asistencia.ui.screens.RequestHistoryItem
import mx.tec.sonrisas.asistencia.ui.screens.RequestStatus
import mx.tec.sonrisas.asistencia.ui.screens.StayAlert
import mx.tec.sonrisas.asistencia.ui.screens.WeekdayAbsenteeism

//ESTO SE VA A DEJAR MIENTRAS AGREGAMOS EL BACKEND CORRECTAMENTE, MIENTRAS TANTO PARTE DE LOS DATOS SE TOMARAN DUMMY DESDE AQUI 
//generacion de data mock con IA
internal object MockData {

    //Empleado
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

    //Empleado
    const val REQUEST_DATE = "12 de octubre de 2026"
    val requestHistory = listOf(
        RequestHistoryItem("Permiso · 12 oct", RequestStatus.Pending),
        RequestHistoryItem("Cambio de turno · 25 sep", RequestStatus.Approved),
        RequestHistoryItem("Falta · 10 ago", RequestStatus.Rejected),
    )

    //Responsable 
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

    //Dirección 
    const val TOTAL_STAFF = 84
    const val ABSENCES_TODAY = 6
    const val HIRES_THIS_MONTH = 4
    const val DEPARTURES_THIS_MONTH = 2
    const val MONTH_NAME = "noviembre"
    val facilitiesCoverage = listOf(
        AreaCoverage("Arcoíris", 17, 18),
        AreaCoverage("Rayito de Sol", 16, 16),
        AreaCoverage("Luna Nueva", 15, 15),
        AreaCoverage("Amanecer", 16, 17),
        AreaCoverage("Los Girasoles", 14, 18),
    )
    val weeklyAbsenteeism = listOf(
        WeekdayAbsenteeism("Lun", 7.1f),
        WeekdayAbsenteeism("Mar", 6.0f),
        WeekdayAbsenteeism("Mié", 7.1f),
        WeekdayAbsenteeism("Jue", 7.1f),
        WeekdayAbsenteeism("Vie", 7.1f),
    )

    //Dirección
    const val STATS_PRESENT = 442
    const val STATS_EXPECTED = 480
    const val STATS_ON_TIME = 384
    const val STATS_MINOR_DELAY = 15
    const val STATS_MAJOR_DELAY = 8
    const val STATS_ABSENT = 33
    val stayAlerts = listOf(
        StayAlert("Estancia Arcoíris", "Personal insuficiente"),
        StayAlert("Estancia Las Rosas", "Personal insuficiente"),
        StayAlert("Estancia Los Girasoles", "Personal insuficiente"),
    )
}
