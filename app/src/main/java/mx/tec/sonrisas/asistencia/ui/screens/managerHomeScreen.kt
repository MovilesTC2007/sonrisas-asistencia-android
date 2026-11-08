package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.People
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.AttendanceStatus
import mx.tec.sonrisas.asistencia.ui.components.SonrisasStatCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.AccentGreen
import mx.tec.sonrisas.asistencia.ui.theme.AccentOrange
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

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
    onStaffClick: () -> Unit,
    onShiftsClick: () -> Unit,
    onRequestsClick: () -> Unit,
    onRegisterMultipleClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val requestsLabel = if (pendingRequestsCount>0) "Solicitudes ($pendingRequestsCount)" else "Solicitudes"

    Scaffold(
        modifier = modifier,
        containerColor = BgCream,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.xxl),
        ) {
            Spacer(modifier = Modifier.height(Spacing.xxl))
            SonrisasTopBar(title = "Buen día, $managerName", onBack = {})
            Spacer(modifier = Modifier.height(Spacing.xxl))
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
                    label = "Falta",
                    status = AttendanceStatus.Absent,
                    modifier = Modifier.weight(1f),
                )
            }
            Spacer(modifier = Modifier.height(Spacing.xxl))
            Text(
                text = "Cobertura de hoy",
                style = SonrisasTextStyle.Title,
                color = InkPrimary,
            )
            Spacer(modifier = Modifier.height(Spacing.sm))
            coverage.forEachIndexed { index, area ->
                CoverageRow(area = area)
                if (index<coverage.lastIndex) {
                    HorizontalDivider(color = LineColor)
                }
            }
            Spacer(modifier = Modifier.height(Spacing.xxl))
            Text(
                text = "Organiza tu estancia",
                style = SonrisasTextStyle.Title,
                color = InkPrimary,
            )
            Spacer(modifier = Modifier.height(Spacing.md))
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
            Spacer(modifier = Modifier.height(Spacing.xl))
            RegisterMultipleButton(onClick = onRegisterMultipleClick)
            Spacer(modifier = Modifier.height(Spacing.xxl))
        }
    }
}

@Composable
private fun CoverageRow(
    area: AreaCoverage,
    modifier: Modifier = Modifier,
) {
    val isComplete = area.presentCount>=area.requiredCount
    val statusColor = if (isComplete) AccentGreen else AccentOrange
    val statusIcon = if (isComplete) Icons.Filled.Check else Icons.Outlined.ErrorOutline

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = area.areaName,
            style = SonrisasTextStyle.Body,
            color = InkPrimary,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = "${area.presentCount}/${area.requiredCount} personas",
            style = SonrisasTextStyle.Body,
            color = InkFaint,
        )
        Spacer(modifier = Modifier.size(Spacing.sm))
        Icon(
            imageVector = statusIcon,
            contentDescription = null,
            tint = statusColor,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Composable
private fun ShortcutTile(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .height(104.dp)
            .clip(RadiusLg)
            .background(BrandPurpleLight)
            .clickable(onClick = onClick)
            .padding(Spacing.md),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = BrandPurpleDeepText,
            modifier = Modifier.size(22.dp),
        )
        Spacer(modifier = Modifier.height(Spacing.sm))
        Text(
            text = label,
            style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.SemiBold),
            color = BrandPurpleDeepText,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun RegisterMultipleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp,
                shape = RadiusPill,
                ambientColor = BrandPurple.copy(alpha = 0.35f),
                spotColor = BrandPurple.copy(alpha = 0.45f),
            )
            .clip(RadiusPill)
            .background(BrandPurple)
            .clickable(onClick = onClick)
            .padding(vertical = Spacing.xl),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Registrar varias personas",
            style = SonrisasTextStyle.Button.copy(fontWeight = FontWeight.Bold),
            color = SurfaceWhite,
        )
    }
}

private val sampleCoverage = listOf(
    AreaCoverage("Lactantes", 3, 3),
    AreaCoverage("Maternales", 1, 2),
    AreaCoverage("Preescolar", 5, 5),
    AreaCoverage("Apoyo", 7, 8),
)

@Preview(showBackground = true, heightDp = 900)
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
            onStaffClick = {},
            onShiftsClick = {},
            onRequestsClick = {},
            onRegisterMultipleClick = {},
        )
    }
}