package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import mx.tec.sonrisas.asistencia.ui.theme.AccentGreen
import mx.tec.sonrisas.asistencia.ui.theme.AccentOrange
import mx.tec.sonrisas.asistencia.ui.theme.AccentRed
import mx.tec.sonrisas.asistencia.ui.theme.AccentYellow
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft

import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTypography
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceLilac
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

data class StayAlert(
    val stayName: String,
    val message: String,
)

@Composable
fun StatisticsScreen(
    presentCount: Int,
    expectedCount: Int,
    onTimeCount: Int,
    minorDelayCount: Int,
    majorDelayCount: Int,
    absentCount: Int,
    alerts: List<StayAlert>,
    modifier: Modifier = Modifier,
) {
    val coverageFraction = if (expectedCount>0) presentCount.toFloat()/expectedCount else 0f
    val delayedTextColor = lerp(AccentYellow, Color.Black, 0.25f)

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
            Text(
                text = "Estadísticas",
                style = SonrisasTypography.displayLarge,
                color = InkPrimary,
            )
            Spacer(modifier = Modifier.height(Spacing.xl))
            CoverageSummaryCard(
                fraction = coverageFraction,
                presentCount = presentCount,
                expectedCount = expectedCount,
            )
            Spacer(modifier = Modifier.height(Spacing.xxl))
            Text(
                text = "Asistencia de hoy",
                style = SonrisasTypography.titleLarge,
                color = InkPrimary,
            )
            Spacer(modifier = Modifier.height(Spacing.md))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.md),
            ) {
                AttendanceStatCard(
                    value = onTimeCount,
                    label = "A tiempo",
                    accentColor = AccentGreen,
                    modifier = Modifier.weight(1f),
                )
                AttendanceStatCard(
                    value = minorDelayCount,
                    label = "Retardo menor",
                    accentColor = AccentYellow,
                    modifier = Modifier.weight(1f),
                )
            }
            Spacer(modifier = Modifier.height(Spacing.md))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.md),
            ) {
                AttendanceStatCard(
                    value = majorDelayCount,
                    label = "Retardo mayor",
                    accentColor = AccentOrange,
                    modifier = Modifier.weight(1f),
                )
                AttendanceStatCard(
                    value = absentCount,
                    label = "Falta",
                    accentColor = AccentRed,
                    modifier = Modifier.weight(1f),
                )
            }
            if (alerts.isNotEmpty()) {
                Spacer(modifier = Modifier.height(Spacing.xxl))
                Text(
                    text = "Estancias con alertas",
                    style = SonrisasTypography.titleLarge,
                    color = InkPrimary,
                )
                Spacer(modifier = Modifier.height(Spacing.sm))
                alerts.forEachIndexed { index, alert ->
                    StayAlertRow(alert = alert)
                    if (index<alerts.lastIndex) {
                        HorizontalDivider(color = LineColor)
                    }
                }
            }
            Spacer(modifier = Modifier.height(Spacing.xxl))
        }
    }
}

@Composable
private fun CoverageSummaryCard(
    fraction: Float,
    presentCount: Int,
    expectedCount: Int,
    modifier: Modifier = Modifier,
) {
    val percent = (fraction*100).roundToInt()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceLilac)
            .padding(Spacing.xl),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.xl),
    ) {
        CoverageDonut(
            fraction = fraction,
            modifier = Modifier.semantics { contentDescription = "Cobertura de hoy: $percent por ciento" },
        )
        Column {
            Text(
                text = "Cobertura de hoy",
                style = SonrisasTypography.titleLarge,
                color = BrandPurple,
            )
            Text(
                text = "$percent%",
                style = SonrisasTypography.displayLarge,
                color = InkPrimary,
            )
            Text(
                text = "$presentCount de $expectedCount presentes",
                style = SonrisasTextStyle.Body,
                color = InkSoft,
            )
        }
    }
}

@Composable
private fun CoverageDonut(
    fraction: Float,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier.size(104.dp)) {
        val strokeWidth = 24    .dp.toPx()
        val arcSize = Size(size.width-strokeWidth, size.height-strokeWidth)
        val arcTopLeft = Offset(strokeWidth/2, strokeWidth/2)

        drawArc(
            color = InkFaint.copy(alpha = 0.3f),
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = arcTopLeft,
            size = arcSize,
            style = Stroke(width = strokeWidth),
        )
        drawArc(
            color = BrandPurple,
            startAngle = -90f,
            sweepAngle = 360f*fraction.coerceIn(0f, 1f),
            useCenter = false,
            topLeft = arcTopLeft,
            size = arcSize,
            style = Stroke(width = strokeWidth),
        )
        drawCircle(
            color = SurfaceLilac,
            radius = (size.minDimension-2*strokeWidth)/2,
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
            .padding(vertical = Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = alert.stayName,
                style = SonrisasTypography.bodyLarge,
                color = InkPrimary,
            )
            Text(
                text = alert.message,
                style = SonrisasTypography.bodyLarge,
                color=AccentRed
            )
        }
        Icon(
            imageVector = Icons.Outlined.ErrorOutline,
            contentDescription = null,
            tint = AccentRed,
            modifier = Modifier.size(24.dp),
        )
    }
}
@Composable
private fun AttendanceStatCard(
    value: Int,
    label: String,
    accentColor: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RadiusLg)
            .background(accentColor.copy(alpha = 0.18f))
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
    ) {
        Text(
            text = value.toString(),
            style = SonrisasTextStyle.Display,
            color = lerp(accentColor, Color.Black, 0.4f),
        )
        Text(
            text = label,
            style = SonrisasTextStyle.Body,
            color = lerp(accentColor, Color.Black, 0.4f),
        )
    }
}

private val sampleAlerts = listOf(
    StayAlert("Estancia arcoíris", "Personal insuficiente"),
    StayAlert("Estancia Las Rosas", "Personal insuficiente"),
    StayAlert("Estancia Los Girasoles", "Personal insuficiente"),
)

@Preview(showBackground = true, heightDp = 1000)
@Composable
private fun StatisticsPreview() {
    SonrisasAsistenciasTheme {
        StatisticsScreen(
            presentCount = 442,
            expectedCount = 480,
            onTimeCount = 384,
            minorDelayCount = 15,
            majorDelayCount = 8,
            absentCount = 33,
            alerts = sampleAlerts,
        )
    }
}


