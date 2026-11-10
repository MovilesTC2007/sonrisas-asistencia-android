package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar
import mx.tec.sonrisas.asistencia.ui.components.AttendanceStatus
import mx.tec.sonrisas.asistencia.ui.components.SonrisasStatCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusSm
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

//el enum y textStyle se añadio a AttendanceStatus
private val CalendarDayStyle = SonrisasTextStyle.Body.copy(fontSize = 16.sp, lineHeight = 20.sp)

private data class DayRecord(val status: AttendanceStatus, val detail: String)

private fun dateKey(year: Int, month: Int, day: Int) = year * 10_000 + month * 100 + day

private val Today = dateKey(2026, 10, 8)

private val SampleRecords: Map<Int, DayRecord> = mapOf(
    dateKey(2026, 10, 1) to DayRecord(AttendanceStatus.OnTime, "Entrada 7:52 · Salida 16:03"),
    dateKey(2026, 10, 2) to DayRecord(AttendanceStatus.Absent, "Sin registro de entrada"),
    dateKey(2026, 10, 5) to DayRecord(AttendanceStatus.OnTime, "Entrada 7:50 · Salida 16:01"),
    dateKey(2026, 10, 6) to DayRecord(AttendanceStatus.MinorDelay, "Entrada 8:08 · Salida 16:05"),
    dateKey(2026, 10, 7) to DayRecord(AttendanceStatus.OnTime, "Entrada 7:56 · Salida 16:00"),
    dateKey(2026, 10, 8) to DayRecord(AttendanceStatus.OnTime, "Entrada 7:55 · En turno"),
)

private val MonthNames = listOf(
    "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
    "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre",
)
private val MonthAbbr = listOf(
    "ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic",
)
private val WeekdayInitials = listOf("L", "M", "M", "J", "V", "S", "D")
private val WeekdayNames = listOf("lunes", "martes", "miércoles", "jueves", "viernes", "sábado", "domingo")
private val WeekdayAbbr = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")

private fun firstWeekdayOffset(year: Int, month: Int): Int {
    val cal = Calendar.getInstance().apply { clear(); set(year, month - 1, 1) }
    return (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7
}

private fun daysInMonth(year: Int, month: Int): Int =
    Calendar.getInstance()
        .apply { clear(); set(year, month - 1, 1) }
        .getActualMaximum(Calendar.DAY_OF_MONTH)
@Composable
fun MyAttendanceScreen(
    modifier: Modifier = Modifier,
    initialSelectedDay: Int = 8,
) {
    var year by rememberSaveable { mutableIntStateOf(2026) }
    var month by rememberSaveable { mutableIntStateOf(10) }
    var selectedDate by rememberSaveable { mutableIntStateOf(dateKey(2026, 10, initialSelectedDay)) }

    // Resumen del mes mostrado. "Asistencias" cuenta los días con entrada (a tiempo + retardo).
    //para este punto solo utilizar el remember y no rememberSaveable nos evita volver a filtrar los registros cuando se cambia en la recomposición
    val monthRecords = remember(year, month) {
        SampleRecords.filterKeys { it / 100 == year * 100 + month }.values
    }
    //en si solamente hay dos tipos de retardos(como el que ella hizo conmigo cuando dijo que fue mi culpa)
    val delays = monthRecords.count {
        it.status == AttendanceStatus.MinorDelay || it.status == AttendanceStatus.MajorDelay
    }
    val attended = monthRecords.count { it.status == AttendanceStatus.OnTime } + delays
    val absences = monthRecords.count { it.status == AttendanceStatus.Absent }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgCream)
            .statusBarsPadding(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.xl),
        ) {
            Spacer(Modifier.height(Spacing.md))
            //aplicacion de encabezado
            SonrisasTopBar(title = "Mis asistencias")

            Spacer(Modifier.height(Spacing.lg))
            MonthSelector(
                label = "${MonthNames[month - 1]} $year",
                onPrevious = {
                    if (month == 1) {
                        month = 12
                        year -= 1
                    } else {
                        month -= 1
                    }
                },
                onNext = {
                    if (month == 12) {
                        month = 1
                        year += 1
                    } else {
                        month += 1
                    }
                },
            )

            Spacer(Modifier.height(Spacing.sm))
            AttendanceCalendar(
                year = year,
                month = month,
                selectedDate = selectedDate,
                onDayClick = { selectedDate = it },
            )

            Spacer(Modifier.height(Spacing.lg))
            StatusLegend()

            Spacer(Modifier.height(Spacing.lg))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            ) {
                SonrisasStatCard(
                    value = attended,
                    label = if (attended == 1) "Asistencia" else "Asistencias",
                    status = AttendanceStatus.OnTime,
                    modifier = Modifier.weight(1f),
                )
                SonrisasStatCard(
                    value = delays,
                    label = if (delays == 1) "Retardo" else "Retardos",
                    status = AttendanceStatus.MinorDelay,
                    modifier = Modifier.weight(1f),
                )
                SonrisasStatCard(
                    value = absences,
                    label = if (absences == 1) "Falta" else "Faltas",
                    status = AttendanceStatus.Absent,
                    modifier = Modifier.weight(1f),
                )
            }

            Spacer(Modifier.height(Spacing.xl))
            SelectedDayRow(date = selectedDate)
            Spacer(Modifier.height(Spacing.xxl))
        }
        //eliminacion de la navbar
    }
}

@Composable
private fun MonthSelector(
    label: String,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ChevronButton(
            icon = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
            description = "Mes anterior",
            onClick = onPrevious,
        )
        Text(
            text = label,
            style = SonrisasTextStyle.Title,
            color = BrandPurpleDeepText,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
        )
        ChevronButton(
            icon = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            description = "Mes siguiente",
            onClick = onNext,
        )
    }
}

@Composable
private fun ChevronButton(icon: ImageVector, description: String, onClick: () -> Unit) {
    IconButton(onClick = onClick, modifier = Modifier.size(48.dp)) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = BrandPurple,
            modifier = Modifier.size(32.dp),
        )
    }
}

@Composable
private fun AttendanceCalendar(
    year: Int,
    month: Int,
    selectedDate: Int,
    onDayClick: (Int) -> Unit,
) {
    val offset = firstWeekdayOffset(year, month)
    val totalDays = daysInMonth(year, month)
    val rows = (offset + totalDays + 6) / 7

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            WeekdayInitials.forEachIndexed { index, initial ->
                Text(
                    text = initial,
                    style = SonrisasTextStyle.Label,
                    color = InkFaint,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = Spacing.sm)
                        .semantics { contentDescription = WeekdayNames[index] },
                )
            }
        }

        repeat(rows) { row ->
            Row(modifier = Modifier.fillMaxWidth()) {
                repeat(7) { column ->
                    val day = row * 7 + column - offset + 1
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                    ) {
                        if (day in 1..totalDays) {
                            val key = dateKey(year, month, day)
                            CalendarDayCell(
                                day = day,
                                record = SampleRecords[key],
                                isSelected = key == selectedDate,
                                isToday = key == Today,
                                isFuture = key > Today,
                                onClick = { onDayClick(key) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CalendarDayCell(
    day: Int,
    record: DayRecord?,
    isSelected: Boolean,
    isToday: Boolean,
    isFuture: Boolean,
    onClick: () -> Unit,
) {
    val numberColor = when {
        isSelected -> BrandPurpleDeepText
        isFuture -> InkFaint
        else -> InkPrimary
    }
    val description = buildString {
        append(day)
        if (isToday) append(", hoy")
        record?.let { append(", ${it.status.label.lowercase()}") }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .clip(RadiusSm)
            .background(if (isSelected) BrandPurpleLight else Color.Transparent)
            .clickable(role = Role.Button, onClick = onClick)
            .clearAndSetSemantics {
                contentDescription = description
                selected = isSelected
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = day.toString(),
            style = CalendarDayStyle.copy(
                fontWeight = if (isSelected || isToday) FontWeight.SemiBold else FontWeight.Medium,
            ),
            color = numberColor,
        )
        Spacer(Modifier.height(3.dp))
        Box(
            modifier = Modifier
                .size(5.dp)
                .clip(CircleShape)
                .background(record?.status?.accent ?: Color.Transparent),
        )
    }
}

@Composable
private fun StatusLegend() {
    //cambio para los estados
    FlowRow(
        modifier = Modifier.padding(horizontal = Spacing.xs),
        horizontalArrangement = Arrangement.spacedBy(Spacing.xl),
        verticalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        AttendanceStatus.entries.forEach { status ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Icon(
                    imageVector = status.icon,
                    contentDescription = null,
                    tint = status.accent,
                    modifier = Modifier.size(16.dp),
                )
                Text(
                    text = status.label,
                    style = SonrisasTextStyle.Caption.copy(fontWeight = FontWeight.SemiBold),
                    color = InkPrimary,
                )
            }
        }
    }
}

@Composable
private fun SelectedDayRow(date: Int) {
    val year = date / 10_000
    val month = date / 100 % 100
    val day = date % 100
    val weekday = WeekdayAbbr[(firstWeekdayOffset(year, month) + day - 1) % 7]
    val title = buildString {
        append("$weekday $day ${MonthAbbr[month - 1]}")
        if (date == Today) append(" · Hoy")
    }
    val record = SampleRecords[date]
    val subtitle = record?.detail ?: if (date > Today) "Aún no hay registros" else "Sin registro"

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing.md)
                .semantics(mergeDescendants = true) {},
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.SemiBold),
                    color = InkPrimary,
                )
                Spacer(Modifier.height(Spacing.xs))
                Text(
                    text = subtitle,
                    style = SonrisasTextStyle.Caption,
                    color = InkSoft,
                )
            }
            record?.let {
                Icon(
                    imageVector = it.status.icon,
                    contentDescription = it.status.label,
                    tint = it.status.accent,
                    modifier = Modifier.size(22.dp),
                )
            }
        }
        HorizontalDivider(color = LineColor)
    }
}

@Preview(name = "Mis asistencias · hoy (p. 9)", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun MyAttendanceTodayPreview() {
    SonrisasAsistenciasTheme {
        MyAttendanceScreen()
    }
}

@Preview(name = "Mis asistencias · día con retardo", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun MyAttendanceDelayPreview() {
    SonrisasAsistenciasTheme {
        MyAttendanceScreen(initialSelectedDay = 6)
    }
}
