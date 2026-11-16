package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasBackStyle
import mx.tec.sonrisas.asistencia.ui.components.SonrisasBottomBar
import mx.tec.sonrisas.asistencia.ui.components.SonrisasButtonSize
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasStepper
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTintedButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.components.sonrisasCardShadow
import mx.tec.sonrisas.asistencia.ui.navigation.SonrisasTab
import mx.tec.sonrisas.asistencia.ui.navigation.UserRole
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

// Áreas y mínimos (RF-21 · CU04 paso 1).

data class StaffArea(
    val name: String,
    val detail: String,
    val minimum: Int,
)

@Composable
fun AreasMinimumsScreen(
    modifier: Modifier = Modifier,
    areas: List<StaffArea> = listOf(
        StaffArea("Lactantes", "Sala 1 · 0 a 18 meses", minimum = 3),
        StaffArea("Maternales", "Sala 2 · 18 meses a 3 años", minimum = 2),
        StaffArea("Preescolar", "Sala 3 · 3 a 6 años", minimum = 4),
        StaffArea("Apoyo", "Cocina y limpieza", minimum = 6),
    ),
) {
    val minimums = remember(areas) {
        mutableStateListOf<Int>().apply { addAll(areas.map { it.minimum }) }
    }
    var selectedTab by remember { mutableStateOf(SonrisasTab.Turnos) }

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
            SonrisasTopBar(
                title = "Áreas y mínimos",
                onBack = {},
                backStyle = SonrisasBackStyle.Plain,
            )

            Spacer(Modifier.height(Spacing.lg))
            Text(
                text = "Si una sala queda con menos personal que su mínimo, te avisamos.",
                style = SonrisasTextStyle.Body.copy(lineHeight = 26.sp),
                color = InkSoft,
            )

            Spacer(Modifier.height(Spacing.xl))
            SonrisasListCard(
                items = areas.indices.toList(),
                modifier = Modifier.sonrisasCardShadow(),
            ) { index ->
                AreaRow(
                    area = areas[index],
                    minimum = minimums[index],
                    onMinimumChange = { minimums[index] = it },
                )
            }

            Spacer(Modifier.height(Spacing.xxl))
            SonrisasTintedButton(
                text = "Agregar área",
                onClick = {},
                leadingIcon = Icons.Rounded.Add,
                size = SonrisasButtonSize.Large,
            )

            Spacer(Modifier.height(Spacing.xl + Spacing.sm))
            SonrisasPrimaryButton(
                text = "Guardar cambios",
                onClick = {},
                size = SonrisasButtonSize.Large,
                elevated = true,
            )
            Spacer(Modifier.height(Spacing.lg))
        }

        SonrisasBottomBar(
            items = UserRole.Responsable.tabs,
            selected = selectedTab,
            onItemClick = { selectedTab = it },
        )
    }
}

@Composable
private fun AreaRow(
    area: StaffArea,
    minimum: Int,
    onMinimumChange: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = Spacing.lg, end = Spacing.md, top = Spacing.lg, bottom = Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = area.name,
                style = SonrisasTextStyle.Body.copy(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                ),
                color = InkPrimary,
            )
            Text(
                text = area.detail,
                style = SonrisasTextStyle.Body.copy(fontSize = 16.sp, lineHeight = 24.sp),
                color = InkSoft,
            )
        }
        Spacer(Modifier.width(Spacing.sm))
        SonrisasStepper(
            value = minimum,
            onValueChange = onMinimumChange,
            label = "mínimo de ${area.name}",
        )
    }
}

@Preview(name = "Áreas y mínimos", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun AreasMinimumsScreenPreview() {
    SonrisasAsistenciasTheme {
        AreasMinimumsScreen()
    }
}
