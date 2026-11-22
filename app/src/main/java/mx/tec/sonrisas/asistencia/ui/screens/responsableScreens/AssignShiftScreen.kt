package mx.tec.sonrisas.asistencia.ui.screens.responsableScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.KeyboardArrowDown
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasAvatar
import mx.tec.sonrisas.asistencia.ui.components.SonrisasNoticeBanner
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.RadiusMd
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTypography
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceLilac
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

enum class ShiftScheduleType(val label: String) {
    Fixed("Horario fijo"),
    Rotating("Rotativo"),
}

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
fun AssignShiftScreen(
    staffName: String,
    staffRoleSummary: String,
    scheduleType: ShiftScheduleType,
    onScheduleTypeChange: (ShiftScheduleType) -> Unit,
    selectedDays: Set<WeekDay>,
    onDayToggle: (WeekDay) -> Unit,
    entryTime: String,
    exitTime: String,
    areaName: String,
    modifier: Modifier = Modifier,
    coverageWarning: String? = null,
    onBackClick: () -> Unit = {},
    onEntryTimeClick: () -> Unit = {},
    onExitTimeClick: () -> Unit = {},
    onAreaClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgCream)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.xl),
    ) {
        Spacer(Modifier.height(Spacing.md))
        SonrisasTopBar(title = "Asignar turno", onBack = onBackClick)
        Spacer(Modifier.height(Spacing.lg))
        StaffSummaryCard(name = staffName, roleSummary = staffRoleSummary)
        Spacer(Modifier.height(Spacing.lg))
        ScheduleTypeSelector(
            selectedType = scheduleType,
            onTypeSelected = onScheduleTypeChange,
        )
        Spacer(Modifier.height(Spacing.xl))
        FieldLabel(text = "Días")
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
                FieldLabel(text = "Entrada")
                Spacer(Modifier.height(Spacing.sm))
                PickerField(
                    value = entryTime,
                    trailingIcon = Icons.Outlined.Schedule,
                    onClick = onEntryTimeClick,
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                FieldLabel(text = "Salida")
                Spacer(Modifier.height(Spacing.sm))
                PickerField(
                    value = exitTime,
                    trailingIcon = Icons.Outlined.Schedule,
                    onClick = onExitTimeClick,
                )
            }
        }
        Spacer(Modifier.height(Spacing.lg))
        FieldLabel(text = "Área")
        Spacer(Modifier.height(Spacing.sm))
        PickerField(
            value = areaName,
            trailingIcon = Icons.Outlined.KeyboardArrowDown,
            onClick = onAreaClick,
        )
        if (coverageWarning != null) {
            Spacer(Modifier.height(Spacing.lg))
            SonrisasNoticeBanner(
                icon = Icons.Outlined.ErrorOutline,
                message = coverageWarning,
            )
        }
        Spacer(Modifier.height(Spacing.lg))
        Text(
            text = "La hora de entrada es la que se usa para calcular retardos.",
            style = SonrisasTextStyle.Body,
            color = InkFaint,
        )
        Spacer(Modifier.height(Spacing.xl))
        SonrisasPrimaryButton(
            text = "Guardar turno",
            onClick = onSaveClick,
            enabled = selectedDays.isNotEmpty(),
        )
        Spacer(Modifier.height(Spacing.xxl))
    }
}

@Composable
private fun StaffSummaryCard(
    name: String,
    roleSummary: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceLilac)
            .padding(Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.lg),
    ) {
        SonrisasAvatar(name = name)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                style = SonrisasTextStyle.Title,
                color = InkPrimary,
            )
            Text(
                text = roleSummary,
                style = SonrisasTextStyle.Body,
                color = InkSoft,
            )
        }
    }
}

@Composable
private fun ScheduleTypeSelector(
    selectedType: ShiftScheduleType,
    onTypeSelected: (ShiftScheduleType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusPill)
            .background(LineColor)
            .padding(Spacing.xs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ShiftScheduleType.values().forEach { type ->
            val isSelected = type == selectedType
            Box(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 44.dp)
                    .clip(RadiusPill)
                    .background(if (isSelected) BrandPurple else Color.Transparent)
                    .clickable(role = Role.Tab) { onTypeSelected(type) },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = type.label,
                    style = SonrisasTypography.titleMedium,
                    color = if (isSelected) Color.White else InkFaint,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun FieldLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = SonrisasTypography.titleMedium,
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
            ),
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
private fun PickerField(
    value: String,
    trailingIcon: ImageVector,
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
            imageVector = trailingIcon,
            contentDescription = null,
            tint = InkFaint,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AssignShiftScreenPreview() {
    SonrisasAsistenciasTheme {
        var scheduleType by remember { mutableStateOf(ShiftScheduleType.Fixed) }
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
        AssignShiftScreen(
            staffName = "María Torres Díaz",
            staffRoleSummary = "Maestra · Maternales",
            scheduleType = scheduleType,
            onScheduleTypeChange = { scheduleType = it },
            selectedDays = selectedDays,
            onDayToggle = { day ->
                selectedDays = if (day in selectedDays) selectedDays - day else selectedDays + day
            },
            entryTime = "8:00",
            exitTime = "16:00",
            areaName = "Preescolar",
            coverageWarning = "Si mueves a María a Preescolar, Maternales queda con 1 de 2 personas.",
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AssignShiftScreenNoDaysPreview() {
    SonrisasAsistenciasTheme {
        var scheduleType by remember { mutableStateOf(ShiftScheduleType.Fixed) }
        var selectedDays by remember { mutableStateOf(emptySet<WeekDay>()) }
        AssignShiftScreen(
            staffName = "María Torres Díaz",
            staffRoleSummary = "Maestra · Maternales",
            scheduleType = scheduleType,
            onScheduleTypeChange = { scheduleType = it },
            selectedDays = selectedDays,
            onDayToggle = { day ->
                selectedDays = if (day in selectedDays) selectedDays - day else selectedDays + day
            },
            entryTime = "8:00",
            exitTime = "16:00",
            areaName = "Maternales",
        )
    }
}
