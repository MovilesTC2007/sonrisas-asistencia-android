package mx.tec.sonrisas.asistencia.ui.screens.responsableScreens

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
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.AttendanceStatus
import mx.tec.sonrisas.asistencia.ui.components.LocationIssue
import mx.tec.sonrisas.asistencia.ui.components.SonrisasCoverageRow
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasLocationNotice
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasStatCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTintedButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.AccentRed
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.StatusRedTint

data class AreaCoverage(
    val areaName: String,
    val presentCount: Int,
    val requiredCount: Int,
)

data class StaffingAlert(
    val areaName: String,
    val presentCount: Int,
    val requiredCount: Int,
    val missingPersonName: String,
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
    staffingAlert: StaffingAlert? = null,
    locationIssue: LocationIssue? = null,
    onMovePersonClick: () -> Unit = {},
    onViewTodayListClick: () -> Unit = {},
    onIncidenceClick: () -> Unit = {},
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
        if (locationIssue != null) {
            Spacer(Modifier.height(Spacing.lg))
            SonrisasLocationNotice(issue = locationIssue)
        }
        Spacer(Modifier.height(Spacing.lg))
        if (staffingAlert != null) {
            StaffingAlertCard(alert = staffingAlert)
            Spacer(Modifier.height(Spacing.xl))
            SonrisasPrimaryButton(
                text = "Mover a alguien de área",
                onClick = onMovePersonClick,
            )
            Spacer(Modifier.height(Spacing.md))
            SonrisasTintedButton(
                text = "Ver lista de hoy",
                onClick = onViewTodayListClick,
            )
            Spacer(Modifier.height(Spacing.xxl))
        }
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
                icon = Icons.Outlined.WarningAmber,
                label = "Incidencia",
                onClick = onIncidenceClick,
                modifier = Modifier.weight(1f),
            )
            ShortcutTile(
                icon = Icons.Outlined.CheckCircle,
                label = "Reg. Varias Personas",
                onClick = onRegisterMultipleClick,
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
    }
}

@Composable
private fun StaffingAlertCard(
    alert: StaffingAlert,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(StatusRedTint)
            .padding(Spacing.lg),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(Spacing.md),
    ) {
        Icon(
            imageVector = Icons.Outlined.WarningAmber,
            contentDescription = null,
            tint = AccentRed,
            modifier = Modifier
                .padding(top = 2.dp)
                .size(20.dp),
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${alert.areaName} está por debajo del mínimo",
                style = SonrisasTextStyle.Title,
                color = InkPrimary,
            )
            Spacer(Modifier.height(Spacing.xs))
            Text(
                text = "Hay ${alert.presentCount} de ${alert.requiredCount} personas. " +
                    "${alert.missingPersonName} no ha registrado entrada.",
                style = SonrisasTextStyle.Body,
                color = InkSoft,
            )
        }
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

@Preview(showBackground = true)
@Composable
private fun ManagerHomeScreenAlertPreview() {
    SonrisasAsistenciasTheme {
        ManagerHomeScreen(
            managerName = "Elena",
            presentCount = 17,
            delayedCount = 2,
            absentCount = 1,
            coverage = sampleCoverage,
            pendingRequestsCount = 2,
            staffingAlert = StaffingAlert(
                areaName = "Maternales",
                presentCount = 1,
                requiredCount = 2,
                missingPersonName = "Sofía Ramírez",
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ManagerHomeScreenLocationPreview() {
    SonrisasAsistenciasTheme {
        ManagerHomeScreen(
            managerName = "Elena",
            presentCount = 17,
            delayedCount = 2,
            absentCount = 1,
            coverage = sampleCoverage,
            pendingRequestsCount = 2,
            staffingAlert = StaffingAlert(
                areaName = "Maternales",
                presentCount = 1,
                requiredCount = 2,
                missingPersonName = "Sofía Ramírez",
            ),
            locationIssue = LocationIssue.Suspicious,
        )
    }
}
