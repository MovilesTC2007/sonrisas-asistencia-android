package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.ResponsableBottomBar
import mx.tec.sonrisas.asistencia.ui.components.ResponsableTab
import mx.tec.sonrisas.asistencia.ui.components.SonrisasAddButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasSearchField
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.RadiusSm
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

private data class StaffEntry(
    val id: Int,
    val name: String,
    val role: String,
    val area: String,
)

private val StaffDirectory = listOf(
    StaffEntry(1, "Ana Martínez López", "Maestra", "Lactantes"),
    StaffEntry(2, "Lucía Hernández Ruiz", "Maestra", "Lactantes"),
    StaffEntry(3, "Rosa Pérez García", "Auxiliar", "Lactantes"),
    StaffEntry(4, "María Torres Díaz", "Maestra", "Maternales"),
    StaffEntry(5, "Sofía Ramírez Cruz", "Auxiliar", "Maternales"),
    StaffEntry(6, "Carmen López Vega", "Cocinera", "Apoyo"),
    StaffEntry(7, "Julia Morales Soto", "Limpieza", "Apoyo"),
)

@Composable
fun StaffScreen(modifier: Modifier = Modifier) {
    var query by rememberSaveable { mutableStateOf("") }
    var selectedTab by rememberSaveable { mutableStateOf(ResponsableTab.Personal) }

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
            StaffHeader()

            Spacer(Modifier.height(Spacing.lg))
            SonrisasSearchField(
                value = query,
                onValueChange = { query = it },
                placeholder = "Buscar nombre o área",
            )

            Spacer(Modifier.height(Spacing.lg))
            StaffDirectoryCard(staff = StaffDirectory)
            Spacer(Modifier.height(Spacing.lg))
        }

        StaffActionsRow(
            modifier = Modifier.padding(
                start = Spacing.xl,
                end = Spacing.xl,
                top = Spacing.sm,
                bottom = Spacing.lg,
            ),
        )

        ResponsableBottomBar(
            selected = selectedTab,
            onTabClick = { selectedTab = it },
        )
    }
}

@Composable
private fun StaffHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = "Personal",
            style = SonrisasTextStyle.Display,
            color = InkPrimary,
            modifier = Modifier.semantics { heading() },
        )
    }
}

@Composable
private fun StaffDirectoryCard(staff: List<StaffEntry>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceWhite),
    ) {
        staff.forEachIndexed { index, entry ->
            StaffDirectoryRow(entry = entry, onClick = {})
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
private fun StaffDirectoryRow(entry: StaffEntry, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 64.dp)
            .clickable(role = Role.Button, onClick = onClick)
            .padding(start = Spacing.lg, end = Spacing.sm, top = Spacing.md, bottom = Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = entry.name,
                style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.SemiBold),
                color = InkPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "${entry.role} · ${entry.area}",
                style = SonrisasTextStyle.Caption,
                color = InkSoft,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(Modifier.width(Spacing.sm))
        // Decorativo: la fila completa ya anuncia que es un botón.
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = null,
            tint = InkFaint,
            modifier = Modifier.size(28.dp),
        )
    }
}

@Composable
private fun StaffActionsRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Generar código de invitación",
            style = SonrisasTextStyle.Button,
            color = BrandPurple,
            modifier = Modifier
                .weight(1f)
                .clip(RadiusSm)
                .clickable(role = Role.Button, onClick = {})
                .heightIn(min = 48.dp)
                .padding(vertical = Spacing.md, horizontal = Spacing.xs),
        )
        Spacer(Modifier.width(Spacing.lg))
        SonrisasAddButton(contentDescription = "Agregar personal", onClick = {})
    }
}

@Preview(name = "Personal (p. 13)", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun StaffScreenPreview() {
    SonrisasAsistenciasTheme {
        StaffScreen()
    }
}