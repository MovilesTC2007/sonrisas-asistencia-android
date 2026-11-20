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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.tec.sonrisas.asistencia.ui.theme.AccentGreen
import mx.tec.sonrisas.asistencia.ui.theme.AccentOrange
import mx.tec.sonrisas.asistencia.ui.theme.AccentRed
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeep
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.RadiusMd
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceLilac
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

private enum class Coverage(val icon: ImageVector, val tint: Color, val description: String) {
    Complete(Icons.Outlined.CheckCircle, AccentGreen, "cobertura completa"),
    Partial(Icons.Outlined.ErrorOutline, AccentOrange, "cobertura incompleta"),
    Critical(Icons.Outlined.ErrorOutline, AccentRed, "cobertura crítica"),
}

private data class EstanciaAreaCoverage(val area: String, val present: Int, val expected: Int) {
    val coverage: Coverage
        get() = when {
            present >= expected -> Coverage.Complete
            present.toFloat() / expected < 0.7f -> Coverage.Critical // menos del 70 % (p. ej. 2/3)
            else -> Coverage.Partial
        }
}

private data class MonthMetric(val value: String, val label: String)

private data class EstanciaSummary(
    val name: String,
    val present: Int,
    val total: Int,
    val manager: String,
    val city: String,
    val areas: List<EstanciaAreaCoverage>,
    val month: String,
    val metrics: List<MonthMetric>,
)

private val SampleEstancia = EstanciaSummary(
    name = "Los Girasoles",
    present = 14,
    total = 18,
    manager = "Mónica Salinas",
    city = "Apodaca, N.L.",
    areas = listOf(
        EstanciaAreaCoverage("Lactantes", 2, 3),
        EstanciaAreaCoverage("Maternales", 4, 5),
        EstanciaAreaCoverage("Preescolar", 5, 6),
        EstanciaAreaCoverage("Apoyo", 3, 4),
    ),
    month = "Octubre",
    metrics = listOf(
        MonthMetric("1", "Alta"),
        MonthMetric("3", "Bajas"),
        MonthMetric("9%", "Ausentismo"),
    ),
)

private val MetricNumberStyle = SonrisasTextStyle.Display.copy(fontSize = 22.sp, lineHeight = 28.sp)

@Composable
fun EstanciaDetailScreen(modifier: Modifier = Modifier) {
    val estancia = SampleEstancia

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgCream)
            .statusBarsPadding(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.xl),
        ) {
            Spacer(Modifier.height(Spacing.md))
            EstanciaDetailHeader(title = estancia.name)

            Spacer(Modifier.height(Spacing.lg))
            AttendanceIsland(estancia = estancia)

            Spacer(Modifier.height(Spacing.xxl))
            SectionTitle(text = "Cobertura por área")
            Spacer(Modifier.height(Spacing.sm))
            CoverageCard(areas = estancia.areas)

            Spacer(Modifier.height(Spacing.xxl))
            SectionTitle(text = estancia.month)
            Spacer(Modifier.height(Spacing.sm))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            ) {
                estancia.metrics.forEach { metric ->
                    MetricCard(metric = metric, modifier = Modifier.weight(1f))
                }
            }
            Spacer(Modifier.height(Spacing.xxl))
        }
    }
}

@Composable
private fun EstanciaDetailHeader(title: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {}, modifier = Modifier.size(48.dp)) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                contentDescription = "Regresar",
                tint = InkPrimary,
                modifier = Modifier.size(32.dp),
            )
        }
        Spacer(Modifier.width(Spacing.xs))
        Text(
            text = title,
            style = SonrisasTextStyle.Display,
            color = InkPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.semantics { heading() },
        )
    }
}

@Composable
private fun AttendanceIsland(estancia: EstanciaSummary) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(BrandPurpleDeep)
            .semantics(mergeDescendants = true) {}
            .padding(horizontal = Spacing.xl, vertical = Spacing.lg),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${estancia.present} de ${estancia.total} presentes",
                style = SonrisasTextStyle.Display,
                color = SurfaceWhite,
                modifier = Modifier.weight(1f),
            )
            Spacer(Modifier.width(Spacing.sm))
            ReadOnlyChip()
        }
        Spacer(Modifier.height(Spacing.xs))
        Text(
            text = "Responsable: ${estancia.manager} · ${estancia.city}",
            style = SonrisasTextStyle.Body,
            color = SurfaceLilac,
        )
    }
}

@Composable
private fun ReadOnlyChip() {
    Row(
        modifier = Modifier
            .clip(RadiusPill)
            .background(SurfaceWhite.copy(alpha = 0.12f))
            .padding(horizontal = Spacing.md, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Outlined.Lock,
            contentDescription = null,
            tint = SurfaceWhite,
            modifier = Modifier.size(14.dp),
        )
        Spacer(Modifier.width(Spacing.xs))
        Text(
            text = "Solo lectura",
            style = SonrisasTextStyle.Caption.copy(fontWeight = FontWeight.SemiBold),
            color = SurfaceWhite,
            maxLines = 1,
        )
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.SemiBold),
        color = InkPrimary,
        modifier = Modifier
            .padding(horizontal = Spacing.xs)
            .semantics { heading() },
    )
}

@Composable
private fun CoverageCard(areas: List<EstanciaAreaCoverage>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceWhite),
    ) {
        areas.forEachIndexed { index, area ->
            CoverageRow(area = area)
            if (index < areas.lastIndex) {
                HorizontalDivider(
                    color = LineColor,
                    modifier = Modifier.padding(horizontal = Spacing.lg),
                )
            }
        }
    }
}

@Composable
private fun CoverageRow(area: EstanciaAreaCoverage) {
    val coverage = area.coverage
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 52.dp)
            .semantics(mergeDescendants = true) {}
            .padding(horizontal = Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = area.area,
            style = SonrisasTextStyle.Body.copy(fontSize = 16.sp, lineHeight = 22.sp),
            color = InkPrimary,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = "${area.present}/${area.expected}",
            style = SonrisasTextStyle.Caption,
            color = InkSoft,
        )
        Spacer(Modifier.width(Spacing.sm))
        Icon(
            imageVector = coverage.icon,
            contentDescription = coverage.description,
            tint = coverage.tint,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Composable
private fun MetricCard(metric: MonthMetric, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RadiusMd)
            .background(BrandPurpleLight)
            .semantics(mergeDescendants = true) {}
            .padding(horizontal = Spacing.md, vertical = Spacing.md),
    ) {
        Text(
            text = metric.value,
            style = MetricNumberStyle,
            color = BrandPurpleDeepText,
        )
        Spacer(Modifier.height(Spacing.xs))
        Text(
            text = metric.label,
            style = SonrisasTextStyle.Caption,
            color = BrandPurpleDeepText,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(name = "Detalle de estancia", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun EstanciaDetailScreenPreview() {
    SonrisasAsistenciasTheme {
        EstanciaDetailScreen()
    }
}
