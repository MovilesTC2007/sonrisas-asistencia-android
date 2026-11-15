package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.PersonRemove
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasDropdownMenu
import mx.tec.sonrisas.asistencia.ui.components.SonrisasFilterChip
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

private enum class ChangeKind(val icon: ImageVector) {
    Correction(Icons.Outlined.Edit),
    Delay(Icons.Outlined.Schedule),
    GroupEntry(Icons.Outlined.Group),
    Removal(Icons.Outlined.PersonRemove),
    Addition(Icons.Outlined.PersonAdd),
    Location(Icons.Outlined.MyLocation),
}

private data class ChangeEntry(
    val kind: ChangeKind,
    val description: String,
    val estancia: String,
    val time: String,
)

private val SampleChanges = listOf(
    ChangeEntry(ChangeKind.Correction, "Elena Ruiz corrigió la salida de Carmen López", "Arcoíris", "hoy 9:14"),
    ChangeEntry(ChangeKind.Delay, "Elena Ruiz registró retardo mayor de Rosa Pérez", "Arcoíris", "hoy 8:40"),
    ChangeEntry(ChangeKind.GroupEntry, "Lucía Hernández registró la entrada de 6 personas", "Arcoíris", "hoy 7:58"),
    ChangeEntry(ChangeKind.Removal, "Mónica Salinas dio de baja a Laura Ortiz", "Los Girasoles", "ayer 17:20"),
    ChangeEntry(ChangeKind.Location, "El sistema marcó una ubicación para revisión", "Luna Nueva", "ayer 7:51"),
    ChangeEntry(ChangeKind.Addition, "Mónica Salinas dio de alta a Paola Ríos", "Los Girasoles", "lun 10:05"),
    ChangeEntry(ChangeKind.Correction, "Sandra Vega corrigió la entrada de Julia Morales", "Luna Nueva", "lun 8:12"),
)

private val EstanciaOptions = listOf("Todas las estancias", "Arcoíris", "Los Girasoles", "Luna Nueva")
private val PeriodOptions = listOf("Hoy", "Esta semana", "Este mes")

private const val ALL_KINDS = "Todos los tipos"
private val KindOptions = listOf(
    ALL_KINDS, "Correcciones", "Retardos", "Entradas", "Altas", "Bajas", "Ubicación",
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChangeHistoryScreen(modifier: Modifier = Modifier) {
    var estancia by rememberSaveable { mutableStateOf(EstanciaOptions[0]) }
    var period by rememberSaveable { mutableStateOf("Esta semana") }
    var kind by rememberSaveable { mutableStateOf(ALL_KINDS) }

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
            ChangeHistoryHeader()

            Spacer(Modifier.height(Spacing.md))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            ) {
                MenuFilterChip(
                    text = estancia,
                    active = true,
                    options = EstanciaOptions,
                    selected = estancia,
                    onSelect = { estancia = it },
                )
                MenuFilterChip(
                    text = period,
                    active = true,
                    options = PeriodOptions,
                    selected = period,
                    onSelect = { period = it },
                )
                MenuFilterChip(
                    text = if (kind == ALL_KINDS) "Tipo" else kind,
                    active = kind != ALL_KINDS,
                    options = KindOptions,
                    selected = kind,
                    onSelect = { kind = it },
                )
            }

            Spacer(Modifier.height(Spacing.md))
            ChangeLogCard(entries = SampleChanges)
            Spacer(Modifier.height(Spacing.xxl))
        }
    }
}

@Composable
private fun ChangeHistoryHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = "Historial de cambios",
            style = SonrisasTextStyle.Display,
            color = InkPrimary,
            modifier = Modifier.semantics { heading() },
        )
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
private fun ChangeLogCard(entries: List<ChangeEntry>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceWhite)
            .padding(vertical = Spacing.xs),
    ) {
        entries.forEachIndexed { index, entry ->
            ChangeLogRow(entry = entry)
            if (index < entries.lastIndex) {
                HorizontalDivider(
                    color = LineColor,
                    modifier = Modifier.padding(start = 72.dp, end = Spacing.lg),
                )
            }
        }
    }
}

@Composable
private fun ChangeLogRow(entry: ChangeEntry) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) {}
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
                imageVector = entry.kind.icon,
                contentDescription = null,
                tint = BrandPurple,
                modifier = Modifier.size(18.dp),
            )
        }
        Spacer(Modifier.width(Spacing.lg))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = entry.description,
                style = SonrisasTextStyle.Body,
                color = InkPrimary,
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "${entry.estancia} · ${entry.time}",
                style = SonrisasTextStyle.Caption,
                color = InkSoft,
            )
        }
    }
}

@Preview(name = "Historial de cambios", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun ChangeHistoryScreenPreview() {
    SonrisasAsistenciasTheme {
        ChangeHistoryScreen()
    }
}
