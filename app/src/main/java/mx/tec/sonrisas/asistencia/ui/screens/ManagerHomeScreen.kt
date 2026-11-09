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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.People
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.AttendanceStatus
import mx.tec.sonrisas.asistencia.ui.components.SonrisasCoverageRow
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasStatCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

data class AreaCoverage(
    val areaName: String,
    val presentCount: Int,
    val requiredCount: Int,
)

@Composable
fun ManagerHomeScreen(
    managerName: String,
    presentCount: Int,
    delayedCount: Int,
    absentCount: Int,
    coverage: List<AreaCoverage>,
    pendingRequestsCount: Int,
    modifier: Modifier = Modifier,
    onStaffClick: () -> Unit = {},
    onShiftsClick: () -> Unit = {},
    onRequestsClick: () -> Unit = {},
    onRegisterMultipleClick: () -> Unit = {},
) {
    val requestsLabel = if (pendingRequestsCount > 0) "Solicitudes ($pendingRequestsCount)" else "Solicitudes"

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgCream)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.xl),
    ) {
        Spacer(Modifier.height(Spacing.md))
        SonrisasTopBar(title = "Buen día, $managerName")
        Spacer(Modifier.height(Spacing.lg))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            SonrisasStatCard(
                value = presentCount,
                label = "Presentes",
                status = AttendanceStatus.OnTime,
                modifier = Modifier.weight(1f),
            )
            SonrisasStatCard(
                value = delayedCount,
                label = "Retardos",
                status = AttendanceStatus.MinorDelay,
                modifier = Modifier.weight(1f),
            )
            SonrisasStatCard(
                value = absentCount,
                label = "Faltas",
                status = AttendanceStatus.Absent,
                modifier = Modifier.weight(1f),
            )
        }
        Spacer(Modifier.height(Spacing.xxl))
        Text(
            text = "Cobertura de hoy",
            style = SonrisasTextStyle.Title,
            color = InkPrimary,
        )
        Spacer(Modifier.height(Spacing.md))
        SonrisasListCard(items = coverage) { area ->
            SonrisasCoverageRow(
                label = area.areaName,
                presentCount = area.presentCount,
                requiredCount = area.requiredCount,
                unit = "personas",
            )
        }
        Spacer(Modifier.height(Spacing.xxl))
        Text(
            text = "Organiza tu estancia",
            style = SonrisasTextStyle.Title,
            color = InkPrimary,
        )
        Spacer(Modifier.height(Spacing.md))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            ShortcutTile(
                icon = Icons.Outlined.People,
                label = "Personal",
                onClick = onStaffClick,
                modifier = Modifier.weight(1f),
            )
            ShortcutTile(
                icon = Icons.Outlined.CalendarToday,
                label = "Turnos",
                onClick = onShiftsClick,
                modifier = Modifier.weight(1f),
            )
            ShortcutTile(
                icon = Icons.Outlined.Description,
                label = requestsLabel,
                onClick = onRequestsClick,
                modifier = Modifier.weight(1f),
            )
        }
        Spacer(Modifier.height(Spacing.xl))
        SonrisasPrimaryButton(
            text = "Registrar varias personas",
            onClick = onRegisterMultipleClick,
        )
        Spacer(Modifier.height(Spacing.xxl))
    }
}

@Composable
private fun ShortcutTile(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = onClick,
        shape = RadiusLg,
        color = BrandPurpleLight,
        contentColor = BrandPurpleDeepText,
        modifier = modifier.heightIn(min = 104.dp),
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(22.dp),
            )
            Spacer(Modifier.height(Spacing.sm))
            Text(
                text = label,
                style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.SemiBold),
                textAlign = TextAlign.Center,
            )
        }
    }
}

private val sampleCoverage = listOf(
    AreaCoverage("Lactantes", 3, 3),
    AreaCoverage("Maternales", 1, 2),
    AreaCoverage("Preescolar", 5, 5),
    AreaCoverage("Apoyo", 7, 8),
)

@Preview(showBackground = true)
@Composable
private fun ManagerHomeScreenPreview() {
    SonrisasAsistenciasTheme {
        ManagerHomeScreen(
            managerName = "Elena",
            presentCount = 17,
            delayedCount = 2,
            absentCount = 1,
            coverage = sampleCoverage,
            pendingRequestsCount = 2,
        )
    }
}