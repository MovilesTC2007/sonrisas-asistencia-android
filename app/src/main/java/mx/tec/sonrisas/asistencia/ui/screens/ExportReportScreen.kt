package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.DireccionBottomBar
import mx.tec.sonrisas.asistencia.ui.components.DireccionTab
import mx.tec.sonrisas.asistencia.ui.components.SonrisasDropdownField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasFilterChip
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasRadioButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasSegmentedControl
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

private enum class ReportType(val label: String) {
    Attendance("Asistencias"),
    Incidents("Faltas, retardos e incidencias"),
    Turnover("Rotación: altas, bajas y ausentismo"),
    StaffMovements("Movimientos de personal"),
}

private enum class ReportPeriod(val label: String, val range: String) {
    Week("Semana", "5 al 11 de octubre de 2026"),
    Month("Mes", "1 al 31 de octubre de 2026"),
    Custom("Otro", "Elige las fechas de inicio y fin"),
}

private enum class ReportFormat(val label: String) { Pdf("PDF"), Excel("Excel") }

private val EstanciaChoices = listOf("Todas (62)", "Arcoíris", "Los Girasoles", "Luna Nueva")

@Composable
fun ExportReportScreen(modifier: Modifier = Modifier) {
    var selectedTab by rememberSaveable { mutableStateOf(DireccionTab.Reportes) }
    var reportType by rememberSaveable { mutableStateOf(ReportType.Turnover) }
    var estancias by rememberSaveable { mutableStateOf(EstanciaChoices[0]) }
    var period by rememberSaveable { mutableStateOf(ReportPeriod.Month) }
    var format by rememberSaveable { mutableStateOf(ReportFormat.Pdf) }

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
            ExportReportHeader()

            Spacer(Modifier.height(Spacing.md))
            FieldLabel(text = "Qué reporte")
            Spacer(Modifier.height(Spacing.sm))
            ReportTypeCard(
                selected = reportType,
                onSelect = { reportType = it },
            )

            Spacer(Modifier.height(Spacing.xl))
            FieldLabel(text = "Estancias")
            Spacer(Modifier.height(Spacing.sm))
            SonrisasDropdownField(
                value = estancias,
                options = EstanciaChoices,
                onSelect = { estancias = it },
                label = "estancias",
            )

            Spacer(Modifier.height(Spacing.xl))
            FieldLabel(text = "Periodo")
            Spacer(Modifier.height(Spacing.sm))
            SonrisasSegmentedControl(
                options = ReportPeriod.entries,
                selected = period,
                onSelect = { period = it },
                label = { it.label },
            )
            Spacer(Modifier.height(Spacing.sm))
            Text(
                text = period.range,
                style = SonrisasTextStyle.Caption,
                color = InkSoft,
                modifier = Modifier.padding(horizontal = Spacing.xs),
            )

            Spacer(Modifier.height(Spacing.xl))
            FieldLabel(text = "Formato")
            Spacer(Modifier.height(Spacing.xs))
            Row(
                modifier = Modifier.selectableGroup(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            ) {
                ReportFormat.entries.forEach { option ->
                    SonrisasFilterChip(
                        text = option.label,
                        selected = option == format,
                        onClick = { format = option },
                    )
                }
            }

            Spacer(Modifier.height(Spacing.xl))
            SonrisasPrimaryButton(
                text = "Descargar reporte",
                onClick = {},
                leadingIcon = Icons.Outlined.Download,
                modifier = Modifier
                    .shadow(
                        elevation = 12.dp,
                        shape = RadiusPill,
                        ambientColor = BrandPurple,
                        spotColor = BrandPurple,
                    )
                    .height(56.dp),
            )
            Spacer(Modifier.height(Spacing.xxl))
        }

        DireccionBottomBar(
            selected = selectedTab,
            onTabClick = { selectedTab = it },
        )
    }
}

@Composable
private fun ExportReportHeader() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {}, modifier = Modifier.size(48.dp)) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                contentDescription = "Regresar",
                tint = InkPrimary,
                modifier = Modifier.size(32.dp),
            )
        }
        Spacer(Modifier.width(Spacing.xs))
        Text(
            text = "Exportar reporte",
            style = SonrisasTextStyle.Display,
            color = InkPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.semantics { heading() },
        )
    }
}

@Composable
private fun FieldLabel(text: String) {
    Text(
        text = text,
        style = SonrisasTextStyle.Body,
        color = InkSoft,
        modifier = Modifier
            .padding(horizontal = Spacing.xs)
            .semantics { heading() },
    )
}

@Composable
private fun ReportTypeCard(
    selected: ReportType,
    onSelect: (ReportType) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceWhite)
            .selectableGroup(),
    ) {
        val options = ReportType.entries
        options.forEachIndexed { index, option ->
            ReportTypeRow(
                label = option.label,
                selected = option == selected,
                onClick = { onSelect(option) },
            )
            if (index < options.lastIndex) {
                HorizontalDivider(
                    color = LineColor,
                    modifier = Modifier.padding(horizontal = Spacing.lg),
                )
            }
        }
    }
}

@Composable
private fun ReportTypeRow(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .selectable(selected = selected, role = Role.RadioButton, onClick = onClick)
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SonrisasRadioButton(selected = selected)
        Spacer(Modifier.width(Spacing.md))
        Text(
            text = label,
            style = SonrisasTextStyle.Body.copy(
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
            ),
            color = InkPrimary,
            modifier = Modifier.weight(1f),
        )
    }
}

@Preview(name = "Exportar reporte", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun ExportReportScreenPreview() {
    SonrisasAsistenciasTheme {
        ExportReportScreen()
    }
}