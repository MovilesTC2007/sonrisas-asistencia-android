package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import mx.tec.sonrisas.asistencia.ui.components.SonrisasAvatar
import mx.tec.sonrisas.asistencia.ui.components.SonrisasCoverageRow
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasNoticeBanner
import mx.tec.sonrisas.asistencia.ui.components.SonrisasStatCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTintedButton
import mx.tec.sonrisas.asistencia.ui.theme.AccentGreen
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.RadiusSm
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.StatusGreenTint

data class WeekdayAbsenteeism(
    val dayLabel: String,
    val absencePercent: Float,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagementDashboardScreen(
    directorName: String,
    dateLabel: String,
    updatedLabel: String,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    totalStaff: Int,
    presentToday: Int,
    hiresThisMonth: Int,
    departuresThisMonth: Int,
    monthName: String,
    alertCount: Int,
    worstCoverage: List<EstanciaListItem>,
    totalEstancias: Int,
    weeklyAbsenteeism: List<WeekdayAbsenteeism>,
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit = {},
    onAlertClick: () -> Unit = {},
    onEstanciaClick: (Int) -> Unit = {},
    onViewAllClick: () -> Unit = {},
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier
            .fillMaxSize()
            .background(BgCream),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.xl),
        ) {
            Spacer(Modifier.height(Spacing.md))
            DashboardHeader(
                directorName = directorName,
                dateLabel = dateLabel,
                updatedLabel = updatedLabel,
                onProfileClick = onProfileClick,
            )
            Spacer(Modifier.height(Spacing.lg))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.md),
            ) {
                DirectorStatCard(totalStaff, "Personal total", Modifier.weight(1f))
                DirectorStatCard(presentToday, "Presentes hoy", Modifier.weight(1f))
            }
            Spacer(Modifier.height(Spacing.md))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.md),
            ) {
                DirectorStatCard(hiresThisMonth, "Altas de $monthName", Modifier.weight(1f))
                DirectorStatCard(departuresThisMonth, "Bajas de $monthName", Modifier.weight(1f))
            }
            Spacer(Modifier.height(Spacing.xl))
            StaffingAlertBanner(alertCount = alertCount, onClick = onAlertClick)
            Spacer(Modifier.height(Spacing.xxl))
            Text(
                text = "Menor cobertura hoy",
                style = SonrisasTextStyle.Title,
                color = InkPrimary,
                modifier = Modifier.semantics { heading() },
            )
            Text(
                text = "Las ${worstCoverage.size} estancias con menos personal",
                style = SonrisasTextStyle.Caption,
                color = InkSoft,
            )
            Spacer(Modifier.height(Spacing.md))
            SonrisasListCard(items = worstCoverage) { item ->
                SonrisasCoverageRow(
                    label = item.name,
                    presentCount = item.presentCount,
                    requiredCount = item.requiredCount,
                    modifier = Modifier.clickable(
                        onClickLabel = "Abrir ${item.name}",
                        onClick = { onEstanciaClick(item.id) },
                    ),
                )
            }
            Spacer(Modifier.height(Spacing.md))
            SonrisasTintedButton(
                text = "Ver las $totalEstancias",
                onClick = onViewAllClick,
            )
            Spacer(Modifier.height(Spacing.xxl))
            Text(
                text = "Ausentismo promedio semanal",
                style = SonrisasTextStyle.Title,
                color = InkPrimary,
                modifier = Modifier.semantics { heading() },
            )
            Spacer(Modifier.height(Spacing.lg))
            WeeklyAbsenteeismChart(data = weeklyAbsenteeism)
            Spacer(Modifier.height(Spacing.xxl))
        }
    }
}

@Composable
private fun DashboardHeader(
    directorName: String,
    dateLabel: String,
    updatedLabel: String,
    onProfileClick: () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Buen día, ${directorName.substringBefore(' ')}",
                style = SonrisasTextStyle.Display,
                color = InkPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.semantics { heading() },
            )
            Text(
                text = dateLabel,
                style = SonrisasTextStyle.Body,
                color = InkSoft,
            )
            Text(
                text = updatedLabel,
                style = SonrisasTextStyle.Caption,
                color = InkSoft,
            )
        }
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .clickable(onClickLabel = "Abrir perfil", role = Role.Button, onClick = onProfileClick),
            contentAlignment = Alignment.Center,
        ) {
            SonrisasAvatar(
                name = directorName,
                size = 44.dp,
                containerColor = BrandPurpleLight,
                contentColor = BrandPurpleDeepText,
            )
        }
    }
}

@Composable
private fun DirectorStatCard(
    value: Int,
    label: String,
    modifier: Modifier = Modifier,
) {
    SonrisasStatCard(
        value = value,
        label = label,
        containerColor = BrandPurpleLight,
        valueColor = BrandPurple,
        modifier = modifier,
    )
}

@Composable
private fun StaffingAlertBanner(
    alertCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (alertCount > 0) {
        val message = if (alertCount == 1) {
            "1 estancia con personal insuficiente"
        } else {
            "$alertCount estancias con personal insuficiente"
        }
        SonrisasNoticeBanner(
            icon = Icons.Outlined.ErrorOutline,
            message = message,
            onClick = onClick,
            modifier = modifier,
        )
    } else {
        SonrisasNoticeBanner(
            icon = Icons.Outlined.CheckCircle,
            message = "Todas las estancias tienen su personal completo",
            containerColor = StatusGreenTint,
            iconTint = AccentGreen,
            modifier = modifier,
        )
    }
}

@Composable
private fun WeeklyAbsenteeismChart(
    data: List<WeekdayAbsenteeism>,
    modifier: Modifier = Modifier,
) {
    val maxPercent = (data.maxOfOrNull { it.absencePercent } ?: 1f).coerceAtLeast(1f)
    val chartDescription = "Ausentismo promedio semanal: " +
        data.joinToString { "${it.dayLabel} ${it.absencePercent.roundToInt()} por ciento" }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clearAndSetSemantics { contentDescription = chartDescription },
        horizontalArrangement = Arrangement.spacedBy(Spacing.md),
    ) {
        data.forEach { item ->
            val fraction = item.absencePercent / maxPercent
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "${item.absencePercent.roundToInt()}%",
                    style = SonrisasTextStyle.Caption.copy(fontWeight = FontWeight.SemiBold),
                    color = InkPrimary,
                )
                Spacer(Modifier.height(Spacing.xs))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentAlignment = Alignment.BottomCenter,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(fraction)
                            .clip(RadiusSm)
                            .background(BrandPurple),
                    )
                }
                Spacer(Modifier.height(Spacing.sm))
                Text(
                    text = item.dayLabel,
                    style = SonrisasTextStyle.Caption,
                    color = InkSoft,
                )
            }
        }
    }
}

private val sampleWorst = listOf(
    EstanciaListItem(3, "Los Girasoles", "Apodaca, N.L.", 14, 18),
    EstanciaListItem(4, "Luna Nueva", "San Nicolás, N.L.", 8, 15),
    EstanciaListItem(1, "Arcoíris", "Monterrey, N.L.", 17, 18),
    EstanciaListItem(2, "Rayito de Sol", "Guadalupe, N.L.", 16, 16),
)

private val sampleAbsenteeism = listOf(
    WeekdayAbsenteeism("Lun", 7.1f),
    WeekdayAbsenteeism("Mar", 6.0f),
    WeekdayAbsenteeism("Mié", 7.1f),
    WeekdayAbsenteeism("Jue", 7.1f),
    WeekdayAbsenteeism("Vie", 7.1f),
)

@Preview(name = "Panel de Dirección", showBackground = true, widthDp = 360, heightDp = 1100)
@Composable
private fun ManagementDashboardScreenPreview() {
    SonrisasAsistenciasTheme {
        ManagementDashboardScreen(
            directorName = "Patricia Gómez Ríos",
            dateLabel = "Martes 17 de noviembre",
            updatedLabel = "Actualizado hace 2 min",
            isRefreshing = false,
            onRefresh = {},
            totalStaff = 480,
            presentToday = 442,
            hiresThisMonth = 4,
            departuresThisMonth = 2,
            monthName = "noviembre",
            alertCount = 3,
            worstCoverage = sampleWorst,
            totalEstancias = 62,
            weeklyAbsenteeism = sampleAbsenteeism,
        )
    }
}

@Preview(name = "Panel · sin alertas", showBackground = true, widthDp = 360, heightDp = 1100)
@Composable
private fun ManagementDashboardNoAlertsPreview() {
    SonrisasAsistenciasTheme {
        ManagementDashboardScreen(
            directorName = "Patricia Gómez Ríos",
            dateLabel = "Martes 17 de noviembre",
            updatedLabel = "Actualizado hace 2 min",
            isRefreshing = false,
            onRefresh = {},
            totalStaff = 480,
            presentToday = 480,
            hiresThisMonth = 4,
            departuresThisMonth = 2,
            monthName = "noviembre",
            alertCount = 0,
            worstCoverage = sampleWorst.takeLast(1),
            totalEstancias = 62,
            weeklyAbsenteeism = sampleAbsenteeism,
        )
    }
}
