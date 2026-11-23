package mx.tec.sonrisas.asistencia.ui.screens.direccionScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.PersonRemove
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.VpnKey
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasBackStyle
import mx.tec.sonrisas.asistencia.ui.components.SonrisasDropdownMenu
import mx.tec.sonrisas.asistencia.ui.components.SonrisasFilterChip
import mx.tec.sonrisas.asistencia.ui.components.SonrisasOutlinedButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.RadiusSm
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

const val ALL_ESTANCIAS = "Todas las estancias"
const val ALL_KINDS = "Todos los tipos"

enum class ChangeKind(val label: String, val icon: ImageVector) {
    Correction("Correcciones", Icons.Outlined.Edit),
    Delay("Retardos", Icons.Outlined.Schedule),
    GroupEntry("Entradas", Icons.Outlined.Group),
    Removal("Bajas", Icons.Outlined.PersonRemove),
    Addition("Altas", Icons.Outlined.PersonAdd),
    Location("Ubicación", Icons.Outlined.MyLocation),
    Access("Accesos de Dirección", Icons.Outlined.VpnKey),
}

enum class HistoryPeriod(val label: String, val maxDaysAgo: Int) {
    Today("Hoy", 0),
    Week("Esta semana", 6),
    Month("Este mes", 30),
}

data class ChangeEvent(
    val id: Int,
    val kind: ChangeKind,
    val description: String,
    val estancia: String?,
    val time: String,
    val daysAgo: Int,
    val actor: String,
    val oldValue: String? = null,
    val newValue: String? = null,
    val reason: String? = null,
    val device: String? = null,
)

// Los eventos de acceso no pertenecen a una estancia: solo salen con "Todas las estancias".
fun filterChanges(
    events: List<ChangeEvent>,
    estancia: String,
    period: HistoryPeriod,
    kind: ChangeKind?,
): List<ChangeEvent> = events.filter { event ->
    (estancia == ALL_ESTANCIAS || event.estancia == estancia) &&
        event.daysAgo <= period.maxDaysAgo &&
        (kind == null || event.kind == kind)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChangeHistoryScreen(
    events: List<ChangeEvent>,
    estanciaOptions: List<String>,
    estancia: String,
    onEstanciaChange: (String) -> Unit,
    period: HistoryPeriod,
    onPeriodChange: (HistoryPeriod) -> Unit,
    kind: ChangeKind?,
    onKindChange: (ChangeKind?) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onEventClick: (Int) -> Unit = {},
    onReportsClick: () -> Unit = {},
) {
    val kindOptions = listOf(ALL_KINDS) + ChangeKind.entries.map { it.label }

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
            SonrisasTopBar(title = "Historial de cambios", onBack = onBack, backStyle = SonrisasBackStyle.Plain)

            Spacer(Modifier.height(Spacing.md))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            ) {
                MenuFilterChip(
                    text = estancia,
                    active = estancia != ALL_ESTANCIAS,
                    options = estanciaOptions,
                    selected = estancia,
                    onSelect = onEstanciaChange,
                )
                MenuFilterChip(
                    text = period.label,
                    active = true,
                    options = HistoryPeriod.entries.map { it.label },
                    selected = period.label,
                    onSelect = { label ->
                        HistoryPeriod.entries.firstOrNull { it.label == label }?.let(onPeriodChange)
                    },
                )
                MenuFilterChip(
                    text = kind?.label ?: "Tipo",
                    active = kind != null,
                    options = kindOptions,
                    selected = kind?.label ?: ALL_KINDS,
                    onSelect = { label -> onKindChange(ChangeKind.entries.firstOrNull { it.label == label }) },
                )
            }

            Spacer(Modifier.height(Spacing.md))
            if (events.isEmpty()) {
                Text(
                    text = "No hay cambios con esos filtros.",
                    style = SonrisasTextStyle.Body,
                    color = InkSoft,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Spacing.xxl),
                )
            } else {
                ChangeLogCard(events = events, onEventClick = onEventClick)
            }
            Spacer(Modifier.height(Spacing.lg))
            SonrisasOutlinedButton(
                text = "Ver en reportes",
                onClick = onReportsClick,
                leadingIcon = Icons.Outlined.BarChart,
            )
            Spacer(Modifier.height(Spacing.xxl))
        }
    }
}

@Composable
private fun MenuFilterChip(
    text: String,
    active: Boolean,
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        SonrisasFilterChip(
            text = text,
            selected = active,
            onClick = { expanded = true },
            role = Role.DropdownList,
        )
        SonrisasDropdownMenu(
            expanded = expanded,
            onDismiss = { expanded = false },
            options = options,
            selected = selected,
            onSelect = {
                onSelect(it)
                expanded = false
            },
        )
    }
}

@Composable
private fun ChangeLogCard(
    events: List<ChangeEvent>,
    onEventClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceWhite)
            .padding(vertical = Spacing.xs),
    ) {
        events.forEachIndexed { index, event ->
            ChangeLogRow(event = event, onClick = { onEventClick(event.id) })
            if (index < events.lastIndex) {
                HorizontalDivider(
                    color = LineColor,
                    modifier = Modifier.padding(start = 72.dp, end = Spacing.lg),
                )
            }
        }
    }
}

@Composable
private fun ChangeLogRow(
    event: ChangeEvent,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClickLabel = "Ver detalle del cambio", onClick = onClick)
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RadiusSm)
                .background(BrandPurpleLight),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = event.kind.icon,
                contentDescription = null,
                tint = BrandPurple,
                modifier = Modifier.size(18.dp),
            )
        }
        Spacer(Modifier.width(Spacing.lg))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = event.description,
                style = SonrisasTextStyle.Body,
                color = InkPrimary,
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = listOfNotNull(event.estancia, event.time).joinToString(" · "),
                style = SonrisasTextStyle.Caption,
                color = InkSoft,
            )
        }
    }
}

private val sampleEvents = listOf(
    ChangeEvent(
        id = 1,
        kind = ChangeKind.Correction,
        description = "Elena Ruiz corrigió la salida de Carmen López",
        estancia = "Arcoíris",
        time = "hoy 9:14",
        daysAgo = 0,
        actor = "Elena Ruiz",
    ),
    ChangeEvent(
        id = 2,
        kind = ChangeKind.Removal,
        description = "Mónica Salinas dio de baja a Laura Ortiz",
        estancia = "Los Girasoles",
        time = "ayer 17:20",
        daysAgo = 1,
        actor = "Mónica Salinas",
    ),
    ChangeEvent(
        id = 3,
        kind = ChangeKind.Access,
        description = "Patricia Gómez inició sesión",
        estancia = null,
        time = "hoy 7:30",
        daysAgo = 0,
        actor = "Patricia Gómez",
    ),
)

@Preview(name = "Historial de cambios", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun ChangeHistoryScreenPreview() {
    SonrisasAsistenciasTheme {
        ChangeHistoryScreen(
            events = sampleEvents,
            estanciaOptions = listOf(ALL_ESTANCIAS, "Arcoíris", "Los Girasoles"),
            estancia = ALL_ESTANCIAS,
            onEstanciaChange = {},
            period = HistoryPeriod.Week,
            onPeriodChange = {},
            kind = null,
            onKindChange = {},
            onBack = {},
            onEventClick = {},
            onReportsClick = {},
        )
    }
}

@Preview(name = "Historial · sin resultados", showBackground = true, widthDp = 360, heightDp = 600)
@Composable
private fun ChangeHistoryEmptyPreview() {
    SonrisasAsistenciasTheme {
        ChangeHistoryScreen(
            events = emptyList(),
            estanciaOptions = listOf(ALL_ESTANCIAS, "Arcoíris"),
            estancia = "Arcoíris",
            onEstanciaChange = {},
            period = HistoryPeriod.Today,
            onPeriodChange = {},
            kind = ChangeKind.Access,
            onKindChange = {},
            onBack = {},
            onEventClick = {},
            onReportsClick = {},
        )
    }
}
