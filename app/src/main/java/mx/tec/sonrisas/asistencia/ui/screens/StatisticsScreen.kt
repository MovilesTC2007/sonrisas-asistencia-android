package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import mx.tec.sonrisas.asistencia.ui.components.AttendanceStatus
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasSegmentedControl
import mx.tec.sonrisas.asistencia.ui.components.SonrisasStatCard
import mx.tec.sonrisas.asistencia.ui.theme.AccentRed
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.StatusRedInk
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceLilac

data class StayAlert(
    val stayName: String,
    val message: String,
)

enum class StatsPeriod(
    val label: String,
    val coverageTitle: String,
    val attendanceTitle: String,
) {
    Today("Hoy", "Cobertura de hoy", "Asistencia de hoy"),
    Week("Semana", "Cobertura de la semana", "Asistencia de la semana"),
    Month("Mes", "Cobertura del mes", "Asistencia del mes"),
}

data class StatisticsSummary(
    val presentCount: Int,
    val expectedCount: Int,
    val onTimeCount: Int,
    val minorDelayCount: Int,
    val majorDelayCount: Int,
    val absentCount: Int,
)

private data class AttendanceSlice(
    val count: Int,
    val color: Color,
)

@Composable
fun StatisticsContent(
    period: StatsPeriod,
    onPeriodChange: (StatsPeriod) -> Unit,
    stats: StatisticsSummary,
    trend: List<WeekdayAbsenteeism>,
    ranking: List<EstanciaListItem>,
    alerts: List<StayAlert>,
    modifier: Modifier = Modifier,
    onEstanciaClick: (Int) -> Unit = {},
) {
    val coverageFraction =
        if (stats.expectedCount > 0) stats.presentCount.toFloat() / stats.expectedCount else 0f

    Column(modifier = modifier) {
        SonrisasSegmentedControl(
            options = StatsPeriod.entries,
            selected = period,
            onSelect = onPeriodChange,
            label = { it.label },
        )
        Spacer(Modifier.height(Spacing.lg))
        CoverageSummaryCard(
            title = period.coverageTitle,
            fraction = coverageFraction,
            presentCount = stats.presentCount,
            expectedCount = stats.expectedCount,
            slices = listOf(
                AttendanceSlice(stats.onTimeCount, AttendanceStatus.OnTime.accent),
                AttendanceSlice(stats.minorDelayCount, AttendanceStatus.MinorDelay.accent),
                AttendanceSlice(stats.majorDelayCount, AttendanceStatus.MajorDelay.accent),
                AttendanceSlice(stats.absentCount, AttendanceStatus.Absent.accent),
            ),
        )
        Spacer(Modifier.height(Spacing.xxl))
        StatsSectionTitle(text = period.attendanceTitle)
        Spacer(Modifier.height(Spacing.md))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            SonrisasStatCard(
                value = stats.onTimeCount,
                label = AttendanceStatus.OnTime.label,
                status = AttendanceStatus.OnTime,
                modifier = Modifier.weight(1f),
            )
            SonrisasStatCard(
                value = stats.minorDelayCount,
                label = AttendanceStatus.MinorDelay.label,
                status = AttendanceStatus.MinorDelay,
                modifier = Modifier.weight(1f),
            )
        }
        Spacer(Modifier.height(Spacing.md))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            SonrisasStatCard(
                value = stats.majorDelayCount,
                label = AttendanceStatus.MajorDelay.label,
                status = AttendanceStatus.MajorDelay,
                modifier = Modifier.weight(1f),
            )
            SonrisasStatCard(
                value = stats.absentCount,
                label = AttendanceStatus.Absent.label,
                status = AttendanceStatus.Absent,
                modifier = Modifier.weight(1f),
            )
        }
        Spacer(Modifier.height(Spacing.xxl))
        StatsSectionTitle(text = "Ausentismo por día")
        Spacer(Modifier.height(Spacing.lg))
        WeeklyAbsenteeismChart(data = trend)
        if (ranking.isNotEmpty()) {
            Spacer(Modifier.height(Spacing.xxl))
            StatsSectionTitle(text = "Ranking de asistencia")
            Spacer(Modifier.height(Spacing.md))
            SonrisasListCard(items = ranking.withIndex().toList()) { (index, item) ->
                RankingRow(
                    position = index + 1,
                    item = item,
                    onClick = { onEstanciaClick(item.id) },
                )
            }
        }
        if (alerts.isNotEmpty()) {
            Spacer(Modifier.height(Spacing.xxl))
            StatsSectionTitle(text = "Estancias con alertas")
            Spacer(Modifier.height(Spacing.md))
            SonrisasListCard(items = alerts) { alert ->
                StayAlertRow(alert = alert)
            }
        }
    }
}

@Composable
private fun StatsSectionTitle(text: String) {
    Text(
        text = text,
        style = SonrisasTextStyle.Title,
        color = InkPrimary,
        modifier = Modifier.semantics { heading() },
    )
}

@Composable
private fun CoverageSummaryCard(
    title: String,
    fraction: Float,
    presentCount: Int,
    expectedCount: Int,
    slices: List<AttendanceSlice>,
    modifier: Modifier = Modifier,
) {
    val percent = (fraction * 100).roundToInt()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceLilac)
            .padding(Spacing.xl),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.lg),
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = SonrisasTextStyle.Title,
                color = InkPrimary,
            )
            Text(
                text = "$percent%",
                style = SonrisasTextStyle.Stat,
                color = BrandPurple,
            )
            Text(
                text = "$presentCount de $expectedCount presentes",
                style = SonrisasTextStyle.Body,
                color = InkSoft,
            )
        }
        CoverageDonut(
            fraction = fraction,
            slices = slices,
            total = expectedCount,
            modifier = Modifier.semantics { contentDescription = "$title: $percent por ciento" },
        )
    }
}

@Composable
private fun CoverageDonut(
    fraction: Float,
    slices: List<AttendanceSlice>,
    total: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.size(130.dp),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 14.dp.toPx()
            val arcSize = Size(size.width - strokeWidth, size.height - strokeWidth)
            val arcTopLeft = Offset(strokeWidth / 2, strokeWidth / 2)

            drawArc(
                color = InkFaint.copy(alpha = 0.25f),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = arcTopLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            )
            drawArc(
                color = BrandPurple,
                startAngle = -90f,
                sweepAngle = 360f * fraction.coerceIn(0f, 1f),
                useCenter = false,
                topLeft = arcTopLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            )
        }
        Column(
            modifier = Modifier.width(76.dp),
            verticalArrangement = Arrangement.spacedBy(Spacing.sm),
        ) {
            slices.forEach { slice ->
                val barFraction = if (total > 0) slice.count.toFloat() / total else 0f
                DonutBar(
                    fraction = if (slice.count > 0) barFraction.coerceAtLeast(0.12f) else 0f,
                    color = slice.color,
                )
            }
        }
    }
}

@Composable
private fun DonutBar(
    fraction: Float,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(10.dp)
            .clip(RadiusPill)
            .background(InkFaint.copy(alpha = 0.2f)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction.coerceIn(0f, 1f))
                .fillMaxHeight()
                .clip(RadiusPill)
                .background(color),
        )
    }
}

@Composable
private fun RankingRow(
    position: Int,
    item: EstanciaListItem,
    onClick: () -> Unit,
) {
    val percent =
        if (item.requiredCount > 0) (item.presentCount * 100f / item.requiredCount).roundToInt() else 0

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClickLabel = "Abrir ${item.name}", onClick = onClick)
            .heightIn(min = 56.dp)
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "$position",
            style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.SemiBold),
            color = BrandPurple,
            modifier = Modifier.width(28.dp),
        )
        Text(
            text = item.name,
            style = SonrisasTextStyle.Body,
            color = InkPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
        Spacer(Modifier.width(Spacing.sm))
        Text(
            text = "$percent %",
            style = SonrisasTextStyle.Body,
            color = InkSoft,
        )
    }
}

@Composable
private fun StayAlertRow(
    alert: StayAlert,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 64.dp)
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = alert.stayName,
                style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.SemiBold),
                color = InkPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = alert.message,
                style = SonrisasTextStyle.Caption,
                color = StatusRedInk,
            )
        }
        Spacer(Modifier.width(Spacing.sm))
        Icon(
            imageVector = Icons.Outlined.ErrorOutline,
            contentDescription = null,
            tint = AccentRed,
            modifier = Modifier.size(20.dp),
        )
    }
}
