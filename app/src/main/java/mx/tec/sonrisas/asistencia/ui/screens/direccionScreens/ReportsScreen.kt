package mx.tec.sonrisas.asistencia.ui.screens.direccionScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WifiOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mx.tec.sonrisas.asistencia.ui.components.SonrisasNoticeBanner
import mx.tec.sonrisas.asistencia.ui.components.SonrisasSegmentedControl
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

enum class ReportsView(val label: String) {
    Statistics("Estadísticas"),
    Export("Exportar"),
}

// Contenedor de la pestaña Reportes
// el contenido llega por slot.
@Composable
fun ReportsScreen(
    view: ReportsView,
    onViewChange: (ReportsView) -> Unit,
    modifier: Modifier = Modifier,
    offlineSince: String? = null,
    content: @Composable ColumnScope.() -> Unit,
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
        SonrisasTopBar(title = "Reportes")
        Spacer(Modifier.height(Spacing.md))
        SonrisasSegmentedControl(
            options = ReportsView.entries,
            selected = view,
            onSelect = onViewChange,
            label = { it.label },
        )
        if (offlineSince != null) {
            Spacer(Modifier.height(Spacing.md))
            SonrisasNoticeBanner(
                icon = Icons.Outlined.WifiOff,
                message = "Mostrando datos de las $offlineSince",
            )
        }
        Spacer(Modifier.height(Spacing.lg))
        content()
        Spacer(Modifier.height(Spacing.xxl))
    }
}

private val sampleStats = StatisticsSummary(
    presentCount = 442,
    expectedCount = 480,
    onTimeCount = 419,
    minorDelayCount = 15,
    majorDelayCount = 8,
    absentCount = 38,
)

private val sampleRanking = listOf(
    EstanciaListItem(2, "Rayito de Sol", "Guadalupe, N.L.", 16, 16),
    EstanciaListItem(1, "Arcoíris", "Monterrey, N.L.", 17, 18),
    EstanciaListItem(3, "Los Girasoles", "Apodaca, N.L.", 14, 18),
)

private val sampleTrend = listOf(
    WeekdayAbsenteeism("Lun", 7.1f),
    WeekdayAbsenteeism("Mar", 6.0f),
    WeekdayAbsenteeism("Mié", 7.1f),
    WeekdayAbsenteeism("Jue", 7.1f),
    WeekdayAbsenteeism("Vie", 7.1f),
)

private val sampleAlerts = listOf(
    StayAlert("Estancia Arcoíris", "Personal insuficiente"),
    StayAlert("Estancia Los Girasoles", "Personal insuficiente"),
)

@Composable
private fun ExportPreviewContent(canDownload: Boolean, hasError: Boolean) {
    ExportContent(
        reportType = ReportType.Turnover,
        onReportTypeChange = {},
        estancia = "Todas (62)",
        estanciaOptions = listOf("Todas (62)", "Arcoíris", "Los Girasoles"),
        onEstanciaChange = {},
        period = ReportPeriod.Month,
        onPeriodChange = {},
        format = ReportFormat.Pdf,
        onFormatChange = {},
        canDownload = canDownload,
        hasError = hasError,
        onDownloadClick = {},
    )
}

@Preview(name = "Reportes · Estadísticas", showBackground = true, widthDp = 360, heightDp = 1500)
@Composable
private fun ReportsStatisticsPreview() {
    SonrisasAsistenciasTheme {
        ReportsScreen(view = ReportsView.Statistics, onViewChange = {}) {
            StatisticsContent(
                period = StatsPeriod.Today,
                onPeriodChange = {},
                stats = sampleStats,
                trend = sampleTrend,
                ranking = sampleRanking,
                alerts = sampleAlerts,
            )
        }
    }
}

@Preview(name = "Reportes · Exportar", showBackground = true, widthDp = 360, heightDp = 900)
@Composable
private fun ReportsExportPreview() {
    SonrisasAsistenciasTheme {
        ReportsScreen(view = ReportsView.Export, onViewChange = {}) {
            ExportPreviewContent(canDownload = true, hasError = false)
        }
    }
}

@Preview(name = "Reportes · Exportar sin conexión", showBackground = true, widthDp = 360, heightDp = 900)
@Composable
private fun ReportsExportOfflinePreview() {
    SonrisasAsistenciasTheme {
        ReportsScreen(view = ReportsView.Export, onViewChange = {}, offlineSince = "7:40") {
            ExportPreviewContent(canDownload = false, hasError = false)
        }
    }
}

@Preview(name = "Reportes · Error al exportar", showBackground = true, widthDp = 360, heightDp = 900)
@Composable
private fun ReportsExportErrorPreview() {
    SonrisasAsistenciasTheme {
        ReportsScreen(view = ReportsView.Export, onViewChange = {}) {
            ExportPreviewContent(canDownload = true, hasError = true)
        }
    }
}
