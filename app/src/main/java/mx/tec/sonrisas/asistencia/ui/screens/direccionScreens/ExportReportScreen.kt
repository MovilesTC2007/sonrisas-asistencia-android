package mx.tec.sonrisas.asistencia.ui.screens.direccionScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasDropdownField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasFilterChip
import mx.tec.sonrisas.asistencia.ui.components.SonrisasNoticeBanner
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasRadioButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasSegmentedControl
import mx.tec.sonrisas.asistencia.ui.theme.AccentRed
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.StatusRedTint
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

enum class ReportType(val label: String) {
    Attendance("Asistencias"),
    Incidents("Faltas, retardos e incidencias"),
    Turnover("Rotación: altas, bajas y ausentismo"),
    StaffMovements("Movimientos de personal"),
}

enum class ReportPeriod(val label: String, val range: String) {
    Week("Semana", "9 al 15 de noviembre de 2026"),
    Month("Mes", "1 al 30 de noviembre de 2026"),
    Custom("Otro", "Elige las fechas de inicio y fin"),
}

enum class ReportFormat(val label: String) { Pdf("PDF"), Excel("Excel") }

@Composable
fun ExportContent(
    reportType: ReportType,
    onReportTypeChange: (ReportType) -> Unit,
    estancia: String,
    estanciaOptions: List<String>,
    onEstanciaChange: (String) -> Unit,
    period: ReportPeriod,
    onPeriodChange: (ReportPeriod) -> Unit,
    format: ReportFormat,
    onFormatChange: (ReportFormat) -> Unit,
    canDownload: Boolean,
    hasError: Boolean,
    onDownloadClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        FieldLabel(text = "Qué reporte")
        Spacer(Modifier.height(Spacing.sm))
        ReportTypeCard(selected = reportType, onSelect = onReportTypeChange)

        Spacer(Modifier.height(Spacing.xl))
        FieldLabel(text = "Estancias")
        Spacer(Modifier.height(Spacing.sm))
        SonrisasDropdownField(
            value = estancia,
            options = estanciaOptions,
            onSelect = onEstanciaChange,
            label = "estancias",
        )

        Spacer(Modifier.height(Spacing.xl))
        FieldLabel(text = "Periodo")
        Spacer(Modifier.height(Spacing.sm))
        SonrisasSegmentedControl(
            options = ReportPeriod.entries,
            selected = period,
            onSelect = onPeriodChange,
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
                    onClick = { onFormatChange(option) },
                )
            }
        }

        if (hasError) {
            Spacer(Modifier.height(Spacing.xl))
            SonrisasNoticeBanner(
                icon = Icons.Outlined.ErrorOutline,
                message = "No se pudo exportar el reporte. Intenta de nuevo.",
                containerColor = StatusRedTint,
                iconTint = AccentRed,
            )
        }
        if (!canDownload) {
            Spacer(Modifier.height(Spacing.md))
            Text(
                text = "Sin conexión: conéctate para descargar el reporte.",
                style = SonrisasTextStyle.Caption,
                color = InkSoft,
                modifier = Modifier.padding(horizontal = Spacing.xs),
            )
        }

        Spacer(Modifier.height(Spacing.xl))
        SonrisasPrimaryButton(
            text = if (hasError) "Reintentar" else "Descargar reporte",
            onClick = onDownloadClick,
            leadingIcon = Icons.Outlined.Download,
            enabled = canDownload,
            elevated = true,
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
