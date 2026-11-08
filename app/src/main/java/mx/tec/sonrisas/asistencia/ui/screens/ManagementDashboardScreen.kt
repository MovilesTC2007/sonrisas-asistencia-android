package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasStatCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.AccentGreen
import mx.tec.sonrisas.asistencia.ui.theme.AccentOrange
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.RadiusSm
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfacePeach
import kotlin.math.roundToInt

data class WeekdayAbsenteeism(
    val dayLabel: String,
    val absencePercent: Float,
)

@Composable
fun ManagementDashboardScreen(
    totalStaff: Int,
    absencesToday: Int,
    hiresThisMonth: Int,
    departuresThisMonth: Int,
    monthName: String,
    facilitiesCoverage: List<AreaCoverage>,
    weeklyAbsenteeism: List<WeekdayAbsenteeism>,
    modifier: Modifier = Modifier,
) {
    val incompleteCount = facilitiesCoverage.count { it.presentCount<it.requiredCount }
    val presentTotal = facilitiesCoverage.sumOf { it.presentCount }
    val requiredTotal = facilitiesCoverage.sumOf { it.requiredCount }

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
            SonrisasTopBar(title = "Panel de Dirección", onBack = {})
            Spacer(modifier = Modifier.height(Spacing.xl))
            //se regresan a valores de theme's ya definidos
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.md),
            ) {
                DirectorStatCard(
                    value = totalStaff,
                    label = "Personal total",
                    modifier = Modifier.weight(1f),
                )
                DirectorStatCard(
                    value = absencesToday,
                    label = "Ausencias de hoy",
                    modifier = Modifier.weight(1f),
                )
            }
            Spacer(modifier = Modifier.height(Spacing.md))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.md),
            ) {
                DirectorStatCard(
                    value = hiresThisMonth,
                    label = "Altas de $monthName",
                    modifier = Modifier.weight(1f),
                )
                DirectorStatCard(
                    value = departuresThisMonth,
                    label = "Bajas de $monthName",
                    modifier = Modifier.weight(1f),
                )
            }
            if (incompleteCount>0) {
                Spacer(modifier = Modifier.height(Spacing.xl))
                IncompleteCoverageBanner(incompleteCount = incompleteCount)
            }
            Spacer(modifier = Modifier.height(Spacing.xxl))
            Text(
                text = "Cobertura de hoy · $presentTotal de $requiredTotal presentes",
                style = SonrisasTextStyle.Title,
                color = InkPrimary,
            )
            Spacer(modifier = Modifier.height(Spacing.sm))
            facilitiesCoverage.forEachIndexed { index, facility ->
                FacilityCoverageRow(facility = facility)
                if (index<facilitiesCoverage.lastIndex) {
                    HorizontalDivider(color = LineColor)
                }
            }
            Spacer(modifier = Modifier.height(Spacing.xxl))
            Text(
                text = "Ausentismo promedio semanal",
                style = SonrisasTextStyle.Title,
                color = InkPrimary,
            )
            Spacer(modifier = Modifier.height(Spacing.lg))
            WeeklyAbsenteeismChart(data = weeklyAbsenteeism)
            Spacer(modifier = Modifier.height(Spacing.xxl))
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
private fun IncompleteCoverageBanner(
    incompleteCount: Int,
    modifier: Modifier = Modifier,
) {
    val message = if (incompleteCount==1) {
        "1 estancia con cobertura incompleta hoy"
    } else {
        "$incompleteCount estancias con cobertura incompleta hoy"
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfacePeach)
            .padding(horizontal = Spacing.lg, vertical = Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.md),
    ) {
        Icon(
            imageVector = Icons.Outlined.ErrorOutline,
            contentDescription = null,
            tint = AccentOrange,
            modifier = Modifier.size(25.dp),
        )
        Text(
            text = message,
            style = SonrisasTextStyle.Body,
            color = InkPrimary,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun FacilityCoverageRow(
    facility: AreaCoverage,
    modifier: Modifier = Modifier,
) {
    val isComplete = facility.presentCount>=facility.requiredCount
    val statusColor = if (isComplete) AccentGreen else AccentOrange
    val statusIcon = if (isComplete) Icons.Filled.Check else Icons.Outlined.ErrorOutline

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = facility.areaName,
            style = SonrisasTextStyle.Body,
            color = InkPrimary,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = "${facility.presentCount}/${facility.requiredCount}",
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
private fun WeeklyAbsenteeismChart(
    data: List<WeekdayAbsenteeism>,
    modifier: Modifier = Modifier,
) {
    val maxPercent = (data.maxOfOrNull { it.absencePercent } ?: 1f).coerceAtLeast(1f)

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            data.forEach { item ->
                val fraction = item.absencePercent.toFloat() / maxPercent
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

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
                        Text(
                            text = "${item.absencePercent.roundToInt()}%",
                            style = SonrisasTextStyle.Title,
                            color= BgCream
                        )

                    }
                    Spacer(modifier = Modifier.height(Spacing.sm))
                    Text(
                        text = item.dayLabel,
                        style = SonrisasTextStyle.Body,
                        color = InkPrimary,
                    )
                }
            }
        }
    }

private val sampleFacilities = listOf(
    AreaCoverage("Arcoíris", 17, 18),
    AreaCoverage("Rayito de Sol", 16, 16),
    AreaCoverage("Luna Nueva", 15, 15),
    AreaCoverage("Amanecer", 16, 17),
    AreaCoverage("Los Girasoles", 14, 18),
)

private val sampleAbsenteeism = listOf(
    WeekdayAbsenteeism("Lun", 7.1f),
    WeekdayAbsenteeism("Mar", 6.0f),
    WeekdayAbsenteeism("Mié", 7.1f),
    WeekdayAbsenteeism("Jue", 7.1f),
    WeekdayAbsenteeism("Vie", 7.1f),
)

@Preview(showBackground = true, heightDp = 1000)
@Composable
private fun ManagementDashboardScreenPreview() {
    SonrisasAsistenciasTheme {
        ManagementDashboardScreen(
            totalStaff = 84,
            absencesToday = 6,
            hiresThisMonth = 4,
            departuresThisMonth = 2,
            monthName = "noviembre",
            facilitiesCoverage = sampleFacilities,
            weeklyAbsenteeism = sampleAbsenteeism,
        )
    }
}
