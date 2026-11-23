package mx.tec.sonrisas.asistencia.ui.screens.responsableScreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.tec.sonrisas.asistencia.ui.components.AttendanceStatus
import mx.tec.sonrisas.asistencia.ui.components.LocationIssue
import mx.tec.sonrisas.asistencia.ui.components.SonrisasAvatar
import mx.tec.sonrisas.asistencia.ui.components.SonrisasLocationNotice
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasSegmentedControl
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

enum class IncidenceType(val label: String) {
    Absence("Falta"),
    Delay("Retardo"),
    Incident("Incidencia"),
}

private val severityRanges = listOf("0–4 tolerancia", "5–14", "15–29", "30+ falta")

@Composable
fun RegisterIncidenceScreen(
    personName: String,
    personDetail: String,
    selectedType: IncidenceType,
    onTypeSelected: (IncidenceType) -> Unit,
    assignedTime: String,
    arrivalTime: String,
    minutesLate: Int,
    reason: String,
    onReasonChange: (String) -> Unit,
    onArrivalTimeClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    locationIssue: LocationIssue? = null,
    onBackClick: () -> Unit = {},
) {
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
        SonrisasTopBar(
            title = "Registrar incidencia",
            onBack = onBackClick,
        )
        if (locationIssue != null) {
            Spacer(Modifier.height(Spacing.lg))
            SonrisasLocationNotice(issue = locationIssue)
        }
        Spacer(Modifier.height(Spacing.lg))
        PersonSummaryCard(name = personName, detail = personDetail)
        Spacer(Modifier.height(Spacing.lg))
        SonrisasSegmentedControl(
            options = IncidenceType.entries,
            selected = selectedType,
            onSelect = onTypeSelected,
            label = { it.label },
        )
        if (selectedType == IncidenceType.Delay) {
            Spacer(Modifier.height(Spacing.lg))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.md),
            ) {
                LabeledField(label = "Entrada asignada", modifier = Modifier.weight(1f)) {
                    FieldBox {
                        Text(
                            text = assignedTime,
                            style = SonrisasTextStyle.Body,
                            color = InkPrimary,
                        )
                    }
                }
                LabeledField(label = "Llegó", modifier = Modifier.weight(1f)) {
                    FieldBox(onClick = onArrivalTimeClick) {
                        Text(
                            text = arrivalTime,
                            style = SonrisasTextStyle.Body,
                            color = InkPrimary,
                        )
                    }
                }
            }
            Spacer(Modifier.height(Spacing.lg))
            DelaySeverityCard(minutesLate = minutesLate)
        }
        Spacer(Modifier.height(Spacing.lg))
        LabeledField(label = "Motivo") {
            FieldBox(minHeight = 96.dp) {
                BasicTextField(
                    value = reason,
                    onValueChange = onReasonChange,
                    textStyle = SonrisasTypography.bodyMedium,
                    cursorBrush = SolidColor(BrandPurple),
                    modifier = Modifier.fillMaxWidth(),
                )
                if (reason.isEmpty()) {
                    Text(
                        text = "Escribe el motivo",
                        style = SonrisasTextStyle.Body,
                        color = InkFaint,
                    )
                }
            }
        }
        Spacer(Modifier.height(Spacing.xl))
        SonrisasPrimaryButton(
            text = "Guardar registro",
            onClick = onSaveClick,
        )
        Spacer(Modifier.height(Spacing.xxl))
    }
}

@Composable
private fun PersonSummaryCard(
    name: String,
    detail: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceLilac)
            .padding(Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SonrisasAvatar(name = name, size = 64.dp)
        Spacer(Modifier.width(Spacing.lg))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                style = SonrisasTypography.titleLarge,
                color = InkPrimary,
            )
            Text(
                text = detail,
                style = SonrisasTextStyle.Body,
                color = InkSoft,
            )
        }
    }
}

@Composable
private fun LabeledField(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = SonrisasTypography.titleMedium,
            color = InkFaint,
        )
        Spacer(Modifier.height(Spacing.sm))
        content()
    }
}

@Composable
private fun FieldBox(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    minHeight: androidx.compose.ui.unit.Dp = 54.dp,
    content: @Composable BoxScope.() -> Unit,
) {
    val clickableModifier = if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier

    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = minHeight)
            .clip(RadiusMd)
            .background(SurfaceWhite)
            .border(BorderStroke(1.dp, LineColor), RadiusMd)
            .then(clickableModifier)
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
        contentAlignment = Alignment.CenterStart,
        content = content,
    )
}

private fun statusForDelay(minutes: Int): AttendanceStatus = when {
    minutes <= 4 -> AttendanceStatus.OnTime
    minutes <= 14 -> AttendanceStatus.MinorDelay
    minutes <= 29 -> AttendanceStatus.MajorDelay
    else -> AttendanceStatus.Absent
}

@Composable
private fun DelaySeverityCard(
    minutesLate: Int,
    modifier: Modifier = Modifier,
) {
    val currentStatus = statusForDelay(minutesLate)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceWhite)
            .padding(Spacing.lg),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "$minutesLate min tarde",
                style = SonrisasTextStyle.Title,
                color = InkPrimary,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = currentStatus.label,
                style = SonrisasTypography.bodySmall,
                color = currentStatus.ink,
                maxLines = 1,
                modifier = Modifier
                    .clip(RadiusPill)
                    .background(currentStatus.tint)
                    .padding(horizontal = Spacing.md, vertical = Spacing.xs),
            )
        }
        Spacer(Modifier.height(Spacing.md))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
        ) {
            AttendanceStatus.entries.forEach { status ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(RadiusPill)
                        .background(if (status == currentStatus) status.accent else status.tint),
                )
            }
        }
        Spacer(Modifier.height(Spacing.sm))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
        ) {
            severityRanges.forEach { range ->
                Text(
                    text = range,
                    style = SonrisasTextStyle.Caption.copy(fontSize = 11.sp),
                    color = InkSoft,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterIncidenceDelayPreview() {
    SonrisasAsistenciasTheme {
        var type by remember { mutableStateOf(IncidenceType.Delay) }
        var reason by remember { mutableStateOf("Tráfico por obras en Av. Constitución.") }

        RegisterIncidenceScreen(
            personName = "Rosa Pérez García",
            personDetail = "Auxiliar · Lactantes · jueves 8 oct",
            selectedType = type,
            onTypeSelected = { type = it },
            assignedTime = "8:00",
            arrivalTime = "8:22",
            minutesLate = 22,
            reason = reason,
            onReasonChange = { reason = it },
            onArrivalTimeClick = {},
            onSaveClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterIncidenceDelay2Preview() {
    SonrisasAsistenciasTheme {
        var type by remember { mutableStateOf(IncidenceType.Delay) }
        var reason by remember { mutableStateOf("Tráfico por obras en Av. Constitución.") }

        RegisterIncidenceScreen(
            personName = "Rosa Pérez García",
            personDetail = "Auxiliar · Lactantes · jueves 8 oct",
            selectedType = type,
            onTypeSelected = { type = it },
            assignedTime = "8:00",
            arrivalTime = "9:22",
            minutesLate = 82,
            reason = reason,
            onReasonChange = { reason = it },
            onArrivalTimeClick = {},
            onSaveClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterIncidenceLocationPreview() {
    SonrisasAsistenciasTheme {
        RegisterIncidenceScreen(
            personName = "Rosa Pérez García",
            personDetail = "Auxiliar · Lactantes · jueves 8 oct",
            selectedType = IncidenceType.Delay,
            onTypeSelected = {},
            assignedTime = "8:00",
            arrivalTime = "8:22",
            minutesLate = 22,
            reason = "",
            onReasonChange = {},
            onArrivalTimeClick = {},
            onSaveClick = {},
            locationIssue = LocationIssue.Imprecise,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterIncidenceAbsencePreview() {
    SonrisasAsistenciasTheme {
        RegisterIncidenceScreen(
            personName = "Rosa Pérez García",
            personDetail = "Auxiliar · Lactantes · jueves 8 oct",
            selectedType = IncidenceType.Absence,
            onTypeSelected = {},
            assignedTime = "8:00",
            arrivalTime = "8:22",
            minutesLate = 0,
            reason = "",
            onReasonChange = {},
            onArrivalTimeClick = {},
            onSaveClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterIncidencePreview() {
    SonrisasAsistenciasTheme {
        RegisterIncidenceScreen(
            personName = "Rosa Pérez García",
            personDetail = "Auxiliar · Lactantes · jueves 8 oct",
            selectedType = IncidenceType.Incident,
            onTypeSelected = {},
            assignedTime = "8:00",
            arrivalTime = "8:22",
            minutesLate = 0,
            reason = "",
            onReasonChange = {},
            onArrivalTimeClick = {},
            onSaveClick = {},
        )
    }
}
