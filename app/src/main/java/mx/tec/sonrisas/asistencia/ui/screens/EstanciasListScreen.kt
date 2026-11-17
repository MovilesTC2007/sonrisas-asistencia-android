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
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.CoverageLevel
import mx.tec.sonrisas.asistencia.ui.components.SonrisasFilterChip
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasSearchField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.components.coverageLevel
import mx.tec.sonrisas.asistencia.ui.theme.AccentGreen
import mx.tec.sonrisas.asistencia.ui.theme.AccentOrange
import mx.tec.sonrisas.asistencia.ui.theme.AccentRed
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

data class EstanciaListItem(
    val id: Int,
    val name: String,
    val city: String,
    val presentCount: Int,
    val requiredCount: Int,
) {
    val level: CoverageLevel
        get() = coverageLevel(presentCount, requiredCount)
}

enum class EstanciaFilter(val label: String) {
    All("Todas"),
    WithAlert("Con alerta"),
    Complete("Completas"),
}

// Busqueda y aplicacion del filtro (considerar primero las que tienen formato de alerta)
fun filterEstancias(
    items: List<EstanciaListItem>,
    query: String,
    filter: EstanciaFilter,
): List<EstanciaListItem> {
    val text = query.trim()
    return items
        .filter {
            text.isEmpty() ||
                it.name.contains(text, ignoreCase = true) ||
                it.city.contains(text, ignoreCase = true)
        }
        .filter {
            when (filter) {
                EstanciaFilter.All -> true
                EstanciaFilter.WithAlert -> it.level != CoverageLevel.Complete
                EstanciaFilter.Complete -> it.level == CoverageLevel.Complete
            }
        }
        .sortedWith(compareByDescending<EstanciaListItem> { it.level.ordinal }.thenBy { it.name })
}

@Composable
fun EstanciasListScreen(
    estancias: List<EstanciaListItem>,
    query: String,
    onQueryChange: (String) -> Unit,
    selectedFilter: EstanciaFilter,
    onFilterChange: (EstanciaFilter) -> Unit,
    modifier: Modifier = Modifier,
    onEstanciaClick: (Int) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgCream)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.xl),
    ) {
        Spacer(Modifier.height(Spacing.md))
        SonrisasTopBar(title = "Estancias")
        Spacer(Modifier.height(Spacing.lg))
        SonrisasSearchField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = "Buscar estancia o municipio",
        )
        Spacer(Modifier.height(Spacing.md))
        Row(
            modifier = Modifier.selectableGroup(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
        ) {
            EstanciaFilter.entries.forEach { filter ->
                SonrisasFilterChip(
                    text = filter.label,
                    selected = filter == selectedFilter,
                    onClick = { onFilterChange(filter) },
                )
            }
        }
        Spacer(Modifier.height(Spacing.lg))
        if (estancias.isEmpty()) {
            Text(
                text = "No hay estancias con esos filtros.",
                style = SonrisasTextStyle.Body,
                color = InkSoft,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Spacing.xxl),
            )
        } else {
            SonrisasListCard(items = estancias) { item ->
                EstanciaRow(item = item, onClick = { onEstanciaClick(item.id) })
            }
        }
        Spacer(Modifier.height(Spacing.xxl))
    }
}

@Composable
private fun EstanciaRow(
    item: EstanciaListItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val level = item.level
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClickLabel = "Abrir ${item.name}", onClick = onClick)
            .heightIn(min = 64.dp)
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.name,
                style = SonrisasTextStyle.Body,
                color = InkPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "${item.city} · ${item.presentCount}/${item.requiredCount} presentes",
                style = SonrisasTextStyle.Caption,
                color = InkSoft,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(Modifier.width(Spacing.sm))
        Icon(
            imageVector =
            if (level == CoverageLevel.Complete) {
                Icons.Outlined.CheckCircle
            } else {
                Icons.Outlined.ErrorOutline
            },
            contentDescription = when (level) {
                CoverageLevel.Complete -> "Cobertura completa"
                CoverageLevel.Partial -> "Cobertura incompleta"
                CoverageLevel.Critical -> "Cobertura crítica"
            },
            tint = when (level) {
                CoverageLevel.Complete -> AccentGreen
                CoverageLevel.Partial -> AccentOrange
                CoverageLevel.Critical -> AccentRed
            },
            modifier = Modifier.size(20.dp),
        )
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = null,
            tint = InkFaint,
            modifier = Modifier.size(24.dp),
        )
    }
}

private val sampleEstancias = listOf(
    EstanciaListItem(1, "Arcoíris", "Monterrey, N.L.", 17, 18),
    EstanciaListItem(2, "Rayito de Sol", "Guadalupe, N.L.", 16, 16),
    EstanciaListItem(3, "Los Girasoles", "Apodaca, N.L.", 14, 18),
    EstanciaListItem(4, "Luna Nueva", "San Nicolás, N.L.", 8, 15),
)

@Preview(name = "Lista de estancias", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun EstanciasListScreenPreview() {
    SonrisasAsistenciasTheme {
        EstanciasListScreen(
            estancias = filterEstancias(sampleEstancias, "", EstanciaFilter.All),
            query = "",
            onQueryChange = {},
            selectedFilter = EstanciaFilter.All,
            onFilterChange = {},
        )
    }
}

@Preview(name = "Lista de estancias · sin resultados", showBackground = true, widthDp = 360, heightDp = 600)
@Composable
private fun EstanciasListEmptyPreview() {
    SonrisasAsistenciasTheme {
        EstanciasListScreen(
            estancias = emptyList(),
            query = "zzz",
            onQueryChange = {},
            selectedFilter = EstanciaFilter.WithAlert,
            onFilterChange = {},
        )
    }
}
