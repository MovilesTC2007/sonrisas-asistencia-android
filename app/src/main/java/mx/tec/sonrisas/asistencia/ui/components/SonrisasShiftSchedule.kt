package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusMd
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTypography
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

enum class WeekDay(val initial: String, val fullName: String) {
    Monday("L", "Lunes"),
    Tuesday("M", "Martes"),
    Wednesday("M", "Miércoles"),
    Thursday("J", "Jueves"),
    Friday("V", "Viernes"),
    Saturday("S", "Sábado"),
    Sunday("D", "Domingo"),
}

@Composable
fun SonrisasShiftSchedule(
    selectedDays: Set<WeekDay>,
    onDayToggle: (WeekDay) -> Unit,
    entryTime: String,
    exitTime: String,
    modifier: Modifier = Modifier,
    onEntryTimeClick: () -> Unit = {},
    onExitTimeClick: () -> Unit = {},
) {
    Column(modifier = modifier.fillMaxWidth()) {
        ScheduleLabel(text = "Días")
        Spacer(Modifier.height(Spacing.sm))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            WeekDay.values().forEach { day ->
                DayChip(
                    day = day,
                    selected = day in selectedDays,
                    onToggle = { onDayToggle(day) },
                )
            }
        }
        Spacer(Modifier.height(Spacing.lg))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                ScheduleLabel(text = "Entrada")
                Spacer(Modifier.height(Spacing.sm))
                TimeField(value = entryTime, onClick = onEntryTimeClick)
            }
            Column(modifier = Modifier.weight(1f)) {
                ScheduleLabel(text = "Salida")
                Spacer(Modifier.height(Spacing.sm))
                TimeField(value = exitTime, onClick = onExitTimeClick)
            }
        }
    }
}

@Composable
private fun ScheduleLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = SonrisasTypography.titleSmall,
        color = InkFaint,
        modifier = modifier,
    )
}

@Composable
private fun DayChip(
    day: WeekDay,
    selected: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(if (selected) BrandPurple else LineColor)
            .toggleable(
                value = selected,
                role = Role.Checkbox,
                onValueChange = { onToggle() },
            )
            .semantics { contentDescription = day.fullName },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = day.initial,
            style = SonrisasTypography.titleLarge,
            color = if (selected) Color.White else InkFaint,
        )
    }
}

@Composable
private fun TimeField(
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusMd)
            .background(SurfaceWhite)
            .border(width = 1.dp, color = LineColor, shape = RadiusMd)
            .clickable(role = Role.Button, onClick = onClick)
            .padding(horizontal = Spacing.lg, vertical = Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = value,
            style = SonrisasTextStyle.Body,
            color = InkPrimary,
            modifier = Modifier.weight(1f),
        )
        Icon(
            imageVector = Icons.Outlined.Schedule,
            contentDescription = null,
            tint = InkFaint,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SonrisasShiftSchedulePreview() {
    SonrisasAsistenciasTheme {
        var selectedDays by remember {
            mutableStateOf(
                setOf(
                    WeekDay.Monday,
                    WeekDay.Tuesday,
                    WeekDay.Wednesday,
                    WeekDay.Thursday,
                    WeekDay.Friday,
                ),
            )
        }
        SonrisasShiftSchedule(
            selectedDays = selectedDays,
            onDayToggle = { day ->
                selectedDays = if (day in selectedDays) selectedDays - day else selectedDays + day
            },
            entryTime = "8:00",
            exitTime = "16:00",
            modifier = Modifier.padding(Spacing.xl),
        )
    }
}
