package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import mx.tec.sonrisas.asistencia.ui.components.AttendanceStatus
import mx.tec.sonrisas.asistencia.ui.components.CoverageLevel
import mx.tec.sonrisas.asistencia.ui.components.SonrisasAvatar
import mx.tec.sonrisas.asistencia.ui.components.SonrisasBackStyle
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasOutlinedButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasStatusChip
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.components.coverageLevel
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

data class EstanciaAreaCoverage(val area: String, val present: Int, val expected: Int) {
    val level: CoverageLevel
        get() = coverageLevel(present, expected)
}

data class MonthMetric(val value: String, val label: String)

data class EstanciaPerson(
    val id: Int,
    val name: String,
    val area: String,
    val clockIn: String?,
    val status: AttendanceStatus,
)

data class EstanciaDetail(
    val id: Int,
    val name: String,
    val present: Int,
    val total: Int,
    val manager: String,
    val city: String,
    val areas: List<EstanciaAreaCoverage>,
    val month: String,
    val metrics: List<MonthMetric>,
    val people: List<EstanciaPerson>,
)

// Datos de muestra (previews y MockData). Cuando haya backend se reemplaza por la respuesta real.
val SampleEstanciaDetail = EstanciaDetail(
    id = 3,
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
    month = "Noviembre",
    metrics = listOf(
        MonthMetric("1", "Alta"),
        MonthMetric("3", "Bajas"),
        MonthMetric("9%", "Ausentismo"),
    ),
    people = listOf(
        EstanciaPerson(1, "Ana Martínez", "Lactantes", "7:55", AttendanceStatus.OnTime),
        EstanciaPerson(2, "Rosa Pérez", "Lactantes", "8:22", AttendanceStatus.MajorDelay),
        EstanciaPerson(3, "María Torres", "Maternales", "8:09", AttendanceStatus.MinorDelay),
        EstanciaPerson(4, "Sofía Ramírez", "Maternales", null, AttendanceStatus.Absent),
        EstanciaPerson(5, "Carmen López", "Apoyo", "6:58", AttendanceStatus.OnTime),
    ),
)

private val MetricNumberStyle = SonrisasTextStyle.Display.copy(fontSize = 22.sp, lineHeight = 28.sp)

private val CoverageLevel.icon: ImageVector
    get() = if (this == CoverageLevel.Complete) Icons.Outlined.CheckCircle else Icons.Outlined.ErrorOutline

private val CoverageLevel.tint: Color
    get() = when (this) {
        CoverageLevel.Complete -> AccentGreen
        CoverageLevel.Partial -> AccentOrange
        CoverageLevel.Critical -> AccentRed
    }

private val CoverageLevel.description: String
    get() = when (this) {
        CoverageLevel.Complete -> "cobertura completa"
        CoverageLevel.Partial -> "cobertura incompleta"
        CoverageLevel.Critical -> "cobertura crítica"
    }

@Composable
fun EstanciaDetailScreen(
    estancia: EstanciaDetail,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onPersonClick: (Int) -> Unit = {},
    onViewReportsClick: () -> Unit = {},
    onViewHistoryClick: () -> Unit = {},
) {
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
            SonrisasTopBar(title = estancia.name, onBack = onBack, backStyle = SonrisasBackStyle.Plain)

            Spacer(Modifier.height(Spacing.lg))
            AttendanceIsland(estancia = estancia)

            Spacer(Modifier.height(Spacing.xxl))
            SectionTitle(text = "Cobertura por área")
            Spacer(Modifier.height(Spacing.sm))
            CoverageCard(areas = estancia.areas)

            Spacer(Modifier.height(Spacing.xxl))
            SectionTitle(text = "Personal")
            Spacer(Modifier.height(Spacing.sm))
            SonrisasListCard(items = estancia.people) { person ->
                PersonRow(person = person, onClick = { onPersonClick(person.id) })
            }

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
            SonrisasOutlinedButton(
                text = "Ver estadísticas y reportes",
                onClick = onViewReportsClick,
                leadingIcon = Icons.Outlined.BarChart,
            )
            Spacer(Modifier.height(Spacing.md))
            SonrisasOutlinedButton(
                text = "Ver historial de cambios",
                onClick = onViewHistoryClick,
                leadingIcon = Icons.Outlined.History,
            )
            Spacer(Modifier.height(Spacing.xxl))
        }
    }
}

@Composable
private fun AttendanceIsland(estancia: EstanciaDetail) {
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
    val level = area.level
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
            imageVector = level.icon,
            contentDescription = level.description,
            tint = level.tint,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Composable
private fun PersonRow(
    person: EstanciaPerson,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClickLabel = "Ver a ${person.name}", onClick = onClick)
            .heightIn(min = 64.dp)
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SonrisasAvatar(
            name = person.name,
            size = 40.dp,
            containerColor = BrandPurpleLight,
            contentColor = BrandPurpleDeepText,
        )
        Spacer(Modifier.width(Spacing.md))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = person.name,
                style = SonrisasTextStyle.Body,
                color = InkPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "${person.area} · ${person.clockIn ?: "Sin registro"}",
                style = SonrisasTextStyle.Caption,
                color = InkSoft,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(Modifier.width(Spacing.sm))
        SonrisasStatusChip(
            text = person.status.label,
            tint = person.status.tint,
            ink = person.status.ink,
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

@Preview(name = "Detalle de estancia", showBackground = true, widthDp = 360, heightDp = 1100)
@Composable
private fun EstanciaDetailScreenPreview() {
    SonrisasAsistenciasTheme {
        EstanciaDetailScreen(estancia = SampleEstanciaDetail, onBack = {})
    }
}
