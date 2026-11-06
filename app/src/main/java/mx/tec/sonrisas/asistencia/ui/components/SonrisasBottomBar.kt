package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

/** Las cuatro secciones principales de la app (mockups p. 7 y 8). */
enum class SonrisasTab(val label: String, val icon: ImageVector) {
    Inicio("Inicio", Icons.Outlined.Home),
    Asistencias("Asistencias", Icons.Outlined.CalendarMonth),
    Solicitudes("Solicitudes", Icons.Outlined.Description),
    Perfil("Perfil", Icons.Outlined.Person),
}

/**
 * Barra de navegación inferior.
 *
 * Sigue las reglas de color del sistema: la pestaña activa usa morado claro como
 * fondo del indicador y [BrandPurpleDeepText] para icono y texto (regla "texto/iconos
 * sobre fondos tintados"); las inactivas usan [InkSoft]. La superficie es blanca con
 * una línea sutil arriba, para separarla del fondo crema sin usar sombra.
 *
 * Solo vista: [onTabClick] no hace nada por defecto y la pestaña activa la define quien llama.
 */
@Composable
fun SonrisasBottomBar(
    selected: SonrisasTab,
    modifier: Modifier = Modifier,
    onTabClick: (SonrisasTab) -> Unit = {},
) {
    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(color = LineColor)
        NavigationBar(
            containerColor = SurfaceWhite,
            contentColor = InkSoft,
            tonalElevation = 0.dp,
        ) {
            SonrisasTab.entries.forEach { tab ->
                val isSelected = tab == selected
                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onTabClick(tab) },
                    icon = { Icon(imageVector = tab.icon, contentDescription = null) },
                    label = {
                        Text(
                            text = tab.label,
                            style = SonrisasTextStyle.Caption.copy(
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                            ),
                            maxLines = 1,
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = BrandPurpleDeepText,
                        selectedTextColor = BrandPurpleDeepText,
                        indicatorColor = BrandPurpleLight,
                        unselectedIconColor = InkSoft,
                        unselectedTextColor = InkSoft,
                    ),
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun SonrisasBottomBarPreview() {
    SonrisasAsistenciasTheme {
        SonrisasBottomBar(selected = SonrisasTab.Inicio)
    }
}