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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Person
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasAvatar
import mx.tec.sonrisas.asistencia.ui.components.SonrisasNoticeBanner
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasShiftSchedule
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTextField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.components.WeekDay
import mx.tec.sonrisas.asistencia.ui.components.rememberSonrisasToast
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.RadiusMd
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTypography
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceLilac
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

@Composable
fun AssignShiftScreen(
    staffName: String,
    onStaffNameChange: (String) -> Unit,
    roleName: String,
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
    onRoleClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
) {
    val showSavedToast = rememberSonrisasToast()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgCream)
            .statusBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.xl),
    ) {
        Spacer(Modifier.height(Spacing.md))
        SonrisasTopBar(title = "Editar personal", onBack = onBackClick)
        Spacer(Modifier.height(Spacing.lg))
        StaffSummaryCard(name = staffName, roleSummary = "$roleName · $areaName")
        Spacer(Modifier.height(Spacing.lg))
        FieldLabel(text = "Modificar nombre")
        Spacer(Modifier.height(Spacing.sm))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RadiusMd)
                .background(SurfaceWhite)
                .border(width = 1.dp, color = LineColor, shape = RadiusMd)
                .padding(horizontal = Spacing.lg, vertical = Spacing.sm),
        ) {
            SonrisasTextField(
                value = staffName,
                onValueChange = onStaffNameChange,
                label = "Nombre completo",
                icon = Icons.Outlined.Person,
            )
        }
        Spacer(Modifier.height(Spacing.lg))
        FieldLabel(text = "Rol")
        Spacer(Modifier.height(Spacing.sm))
        PickerField(
            value = roleName,
            trailingIcon = Icons.Outlined.KeyboardArrowDown,
            onClick = onRoleClick,
        )
        Spacer(Modifier.height(Spacing.lg))
        SonrisasShiftSchedule(
            selectedDays = selectedDays,
            onDayToggle = onDayToggle,
            entryTime = entryTime,
            exitTime = exitTime,
            onEntryTimeClick = onEntryTimeClick,
            onExitTimeClick = onExitTimeClick,
        )
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
            text = "Guardar cambios",
            onClick = {
                onSaveClick()
                showSavedToast()
            },
            enabled = staffName.isNotBlank() && selectedDays.isNotEmpty(),
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
private fun EditStaffScreenPreview() {
    SonrisasAsistenciasTheme {
        var staffName by remember { mutableStateOf("María Torres Díaz") }
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
            staffName = staffName,
            onStaffNameChange = { staffName = it },
            roleName = "Maestra",
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
private fun EditStaffScreenNoDaysPreview() {
    SonrisasAsistenciasTheme {
        var staffName by remember { mutableStateOf("María Torres Díaz") }
        var selectedDays by remember { mutableStateOf(emptySet<WeekDay>()) }
        AssignShiftScreen(
            staffName = staffName,
            onStaffNameChange = { staffName = it },
            roleName = "Maestra",
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
