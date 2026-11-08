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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasCoverageRow
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasNoticeBanner
import mx.tec.sonrisas.asistencia.ui.components.SonrisasStatCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.RadiusSm
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import kotlin.math.roundToInt

data class WeekdayAbsenteeism(
    val dayLabel: String,
    val absencePercent: Float,
)

//se quita flecha de regresar y su Scaffold
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
    val incompleteCount = facilitiesCoverage.count { it.presentCount < it.requiredCount }
    val presentTotal = facilitiesCoverage.sumOf { it.presentCount }
    val requiredTotal = facilitiesCoverage.sumOf { it.requiredCount }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgCream)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.xl),
    ) {
        Spacer(Modifier.height(Spacing.md))
        SonrisasTopBar(title = "Panel de Dirección")
        Spacer(Modifier.height(Spacing.lg))
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
        Spacer(Modifier.height(Spacing.md))
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
        if (incompleteCount > 0) {
            Spacer(Modifier.height(Spacing.xl))
            IncompleteCoverageBanner(incompleteCount = incompleteCount)
        }
        Spacer(Modifier.height(Spacing.xxl))
        //reacomodacion de texto para que no ocupe tanto espacio como ella en mi corazon
        Text(
            text = "Cobertura de hoy",
            style = SonrisasTextStyle.Title,
            color = InkPrimary,
        )
        Text(
            text = "$presentTotal de $requiredTotal presentes",
            style = SonrisasTextStyle.Caption,
            color = InkSoft,
        )
        Spacer(Modifier.height(Spacing.md))
        SonrisasListCard(items = facilitiesCoverage) { facility ->
            SonrisasCoverageRow(
                label = facility.areaName,
                presentCount = facility.presentCount,
                requiredCount = facility.requiredCount,
            )
        }
        Spacer(Modifier.height(Spacing.xxl))
        Text(
            text = "Ausentismo promedio semanal",
            style = SonrisasTextStyle.Title,
            color = InkPrimary,
        )
        Spacer(Modifier.height(Spacing.lg))
        WeeklyAbsenteeismChart(data = weeklyAbsenteeism)
        Spacer(Modifier.height(Spacing.xxl))
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
    val message = if (incompleteCount == 1) {
        "1 estancia con cobertura incompleta hoy"
    } else {
        "$incompleteCount estancias con cobertura incompleta hoy"
    }
    SonrisasNoticeBanner(
        icon = Icons.Outlined.ErrorOutline,
        message = message,
        modifier = modifier,
    )
}


//aqui se cambia ya que existe un bug porque se sobreponian dos colores crema en un solo punto uno siendo el porcentaje
//y luego las brarras de texto. se cambia grafica para que sea una descripcion solamente, cambio de indentación (al chile muy IA ese pedo que habia antes aqui)
//habia un tofloat incluso donde ya habia un float que rollo
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

@Preview(name = "Inicio dirección", showBackground = true, widthDp = 360, heightDp = 1000)
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
