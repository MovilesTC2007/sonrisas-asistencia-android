package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasCheckbox
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasSearchField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.RadiusMd
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

private data class StaffMember(
    val id: Int,
    val name: String,
    val role: String,
    val room: String,
)

private val SampleStaff = listOf(
    StaffMember(1, "Ana Martínez López", "Maestra", "Lactantes"),
    StaffMember(2, "Lucía Hernández Ruiz", "Maestra", "Lactantes"),
    StaffMember(3, "Rosa Pérez García", "Auxiliar", "Lactantes"),
    StaffMember(4, "María Torres Díaz", "Maestra", "Maternales"),
    StaffMember(5, "Sofía Ramírez Cruz", "Auxiliar", "Maternales"),
    StaffMember(6, "Carmen López Vega", "Cocinera", "Apoyo"),
)

private val CheckboxVisualSize = 24.dp
@Composable
fun RegisterStaffScreen(
    modifier: Modifier = Modifier,
    initialSelectedCount: Int = SampleStaff.size,
    onBack: () -> Unit = {},
    onRegisterEntry: (selectedIds: Set<Int>) -> Unit = {},
) {
    var query by rememberSaveable { mutableStateOf("") }
    var selectedIds by rememberSaveable {
        mutableStateOf<Set<Int>>(SampleStaff.take(initialSelectedCount).map { it.id }.toSet())
    }

    val total = SampleStaff.size
    val count = selectedIds.size
    val selectAllState = when (count) {
        0 -> ToggleableState.Off
        total -> ToggleableState.On
        else -> ToggleableState.Indeterminate
    }

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
            SonrisasTopBar(title = "Registrar personal", onBack = onBack)

            Spacer(Modifier.height(Spacing.lg))
            SonrisasSearchField(
                value = query,
                onValueChange = { query = it },
                placeholder = "Buscar por nombre",
            )

            Spacer(Modifier.height(Spacing.lg))
            SelectAllRow(
                state = selectAllState,
                count = count,
                total = total,
                onClick = {
                    selectedIds = if (count == total) emptySet() else SampleStaff.map { it.id }.toSet()
                },
            )

            Spacer(Modifier.height(Spacing.sm))
            StaffListCard(
                staff = SampleStaff,
                selectedIds = selectedIds,
                onToggle = { id ->
                    selectedIds = if (id in selectedIds) selectedIds - id else selectedIds + id
                },
            )
            Spacer(Modifier.height(Spacing.xxl))
        }

        SonrisasPrimaryButton(
            text = if (count > 0) "Registrar entrada ($count)" else "Registrar entrada",
            onClick = { onRegisterEntry(selectedIds) },
            enabled = count > 0,
            modifier = Modifier.padding(
                start = Spacing.xl,
                end = Spacing.xl,
                top = Spacing.sm,
                bottom = Spacing.lg,
            ),
        )
        //se quita barra idea
    }
}

@Composable
private fun SelectAllRow(
    state: ToggleableState,
    count: Int,
    total: Int,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .clip(RadiusMd)
            .triStateToggleable(state = state, role = Role.Checkbox, onClick = onClick)
            .padding(horizontal = Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SonrisasCheckbox(state = state, modifier = Modifier.size(CheckboxVisualSize))
        Spacer(Modifier.width(Spacing.md))
        Text(
            text = "Seleccionar todas",
            style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.SemiBold),
            color = BrandPurpleDeepText,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = "$count de $total",
            style = SonrisasTextStyle.Caption,
            color = InkSoft,
        )
    }
}

@Composable
private fun StaffListCard(
    staff: List<StaffMember>,
    selectedIds: Set<Int>,
    onToggle: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceWhite),
    ) {
        staff.forEachIndexed { index, member ->
            StaffRow(
                member = member,
                checked = member.id in selectedIds,
                onToggle = { onToggle(member.id) },
            )
            if (index < staff.lastIndex) {
                HorizontalDivider(
                    color = LineColor,
                    modifier = Modifier.padding(horizontal = Spacing.lg),
                )
            }
        }
    }
}

@Composable
private fun StaffRow(
    member: StaffMember,
    checked: Boolean,
    onToggle: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 64.dp)
            .toggleable(value = checked, role = Role.Checkbox, onValueChange = { onToggle() })
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SonrisasCheckbox(checked = checked, modifier = Modifier.size(CheckboxVisualSize))
        Spacer(Modifier.width(Spacing.md))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = member.name,
                style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.SemiBold),
                color = InkPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "${member.role} · ${member.room}",
                style = SonrisasTextStyle.Caption,
                color = InkSoft,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview(name = "Registrar personal · todas (p. 12)", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun RegisterStaffAllPreview() {
    SonrisasAsistenciasTheme {
        RegisterStaffScreen()
    }
}

@Preview(name = "Registrar personal · selección parcial", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun RegisterStaffPartialPreview() {
    SonrisasAsistenciasTheme {
        RegisterStaffScreen(initialSelectedCount = 3)
    }
}

@Preview(name = "Registrar personal · ninguna", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun RegisterStaffNonePreview() {
    SonrisasAsistenciasTheme {
        RegisterStaffScreen(initialSelectedCount = 0)
    }
}