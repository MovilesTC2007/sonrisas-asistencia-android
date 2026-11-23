package mx.tec.sonrisas.asistencia.ui.screens.responsableScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.AttendanceStatus
import mx.tec.sonrisas.asistencia.ui.components.SonrisasAvatar
import mx.tec.sonrisas.asistencia.ui.components.SonrisasButtonSize
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTintedButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTypography
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite
enum class TodayListFilter(val label: String) {
    Attendance("Asistencia"),
    NoClockIn("Falta"),
    Delays("Retardo"),
}
data class TodayAttendanceEntry(
    val name: String,
    val area: String,
    val clockInTime: String?,
    val status: AttendanceStatus,
)

@Composable
fun TodayAttendanceScreen(
    dateLabel: String,
    facilityName: String,
    entries: List<TodayAttendanceEntry>,
    selectedFilter: TodayListFilter,
    onFilterSelected: (TodayListFilter) -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {
    val filteredEntries = when (selectedFilter) {
        TodayListFilter.Attendance -> entries.filter { it.status == AttendanceStatus.OnTime }
        TodayListFilter.NoClockIn -> entries.filter { it.status == AttendanceStatus.Absent }
        TodayListFilter.Delays -> entries.filter {
            it.status == AttendanceStatus.MinorDelay || it.status == AttendanceStatus.MajorDelay
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgCream)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.xl),
    ) {
        Spacer(Modifier.height(Spacing.md))
        SonrisasTopBar(title = "Lista de hoy", onBack = onBackClick)
        Spacer(Modifier.height(Spacing.md))

        Text(
            text = "$dateLabel · Estancia $facilityName",
            style = SonrisasTextStyle.Title,
            color = InkSoft,
        )
        Spacer(Modifier.height(Spacing.md))

        SonrisasTintedButton(
            text = "Consultar áreas y mínimos",
            onClick = {},
            size = SonrisasButtonSize.Regular,
        )
        Spacer(Modifier.height(Spacing.lg))
        FilterSegmentedControl(
            selected = selectedFilter,
            onSelected = onFilterSelected,
        )
        Spacer(Modifier.height(Spacing.lg))
        if (filteredEntries.isEmpty()) {
            Text(
                text = "No hay personas en esta lista.",
                style = SonrisasTextStyle.Body,
                color = InkSoft,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Spacing.xxl),
            )
        } else {
            SonrisasListCard(items = filteredEntries) { entry ->
                TodayAttendanceRow(entry = entry)
            }
        }
        Spacer(Modifier.height(Spacing.xxl))
    }
}

@Composable
private fun FilterSegmentedControl(
    selected: TodayListFilter,
    onSelected: (TodayListFilter) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusPill)
            .background(LineColor)
            .padding(Spacing.xs),
    ) {
        TodayListFilter.entries.forEach { filter ->
            val isSelected = filter == selected
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RadiusPill)
                    .background(if (isSelected) BrandPurple else Color.Transparent)
                    .selectable(
                        selected = isSelected,
                        role = Role.Tab,
                        onClick = { onSelected(filter) },
                    )
                    .padding(vertical = Spacing.md),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = filter.label,
                    style = SonrisasTypography.titleMedium,
                    color = if (isSelected) SurfaceWhite else InkSoft,
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
private fun TodayAttendanceRow(
    entry: TodayAttendanceEntry,
    modifier: Modifier = Modifier,
) {
    val detail = "${entry.area} · ${entry.clockInTime ?: "Sin registro"}"

    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 72.dp)
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SonrisasAvatar(
            name = entry.name,
            size = 48.dp,
            containerColor = BrandPurpleLight,
            contentColor = BrandPurpleDeepText,
        )
        Spacer(Modifier.width(Spacing.lg))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = entry.name,
                style = SonrisasTextStyle.Title,
                color = InkPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = detail,
                style = SonrisasTextStyle.Body,
                color = InkSoft,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(Modifier.width(Spacing.sm))
        StatusChip(status = entry.status)
    }
}

@Composable
private fun StatusChip(
    status: AttendanceStatus,
    modifier: Modifier = Modifier,
) {
    Text(
        text = status.label,
        style = SonrisasTypography.bodySmall,
        color = status.ink,
        maxLines = 1,
        modifier = modifier
            .clip(RadiusPill)
            .background(status.tint)
            .padding(horizontal = Spacing.md, vertical = Spacing.xs),
    )
}

private val sampleEntries = listOf(
    TodayAttendanceEntry("Ana Martínez", "Lactantes", "7:55", AttendanceStatus.OnTime),
    TodayAttendanceEntry("Lucía Hernández", "Lactantes", "7:58", AttendanceStatus.OnTime),
    TodayAttendanceEntry("Rosa Pérez", "Lactantes", "8:22", AttendanceStatus.MajorDelay),
    TodayAttendanceEntry("María Torres", "Maternales", "8:09", AttendanceStatus.MinorDelay),
    TodayAttendanceEntry("Sofía Ramírez", "Maternales", null, AttendanceStatus.Absent),
)

@Preview(showBackground = true)
@Composable
private fun TodayAttendanceListScreenPreview() {
    SonrisasAsistenciasTheme {
        var filter by remember { mutableStateOf(TodayListFilter.Attendance) }

        TodayAttendanceScreen(
            dateLabel = "Jueves 8 de octubre",
            facilityName = "Arcoíris",
            entries = sampleEntries,
            selectedFilter = filter,
            onFilterSelected = { filter = it },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TodayAttendanceListDelaysPreview() {
    SonrisasAsistenciasTheme {
        TodayAttendanceScreen(
            dateLabel = "Jueves 8 de octubre",
            facilityName = "Arcoíris",
            entries = sampleEntries,
            selectedFilter = TodayListFilter.Delays,
            onFilterSelected = {},
        )
    }
}
