package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Schedule
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

interface SonrisasNavItem {
    val label: String
    val icon: ImageVector
}

enum class SonrisasTab(
    override val label: String,
    override val icon: ImageVector,
) : SonrisasNavItem {
    Inicio("Inicio", Icons.Outlined.Home),
    Asistencias("Asistencias", Icons.Outlined.CalendarMonth),
    Solicitudes("Solicitudes", Icons.Outlined.Description),
    Perfil("Perfil", Icons.Outlined.Person),
}

enum class ResponsableTab(
    override val label: String,
    override val icon: ImageVector,
) : SonrisasNavItem {
    Inicio("Inicio", Icons.Outlined.Home),
    Personal("Personal", Icons.Outlined.Groups),
    Turnos("Turnos", Icons.Outlined.Schedule),
    Solicitudes("Solicitudes", Icons.Outlined.Description),
}

@Composable
fun SonrisasBottomBar(
    selected: SonrisasTab,
    modifier: Modifier = Modifier,
    onTabClick: (SonrisasTab) -> Unit = {},
) {
    SonrisasNavBar(
        items = SonrisasTab.entries,
        selected = selected,
        modifier = modifier,
        onItemClick = onTabClick,
    )
}

@Composable
fun ResponsableBottomBar(
    selected: ResponsableTab,
    modifier: Modifier = Modifier,
    onTabClick: (ResponsableTab) -> Unit = {},
) {
    SonrisasNavBar(
        items = ResponsableTab.entries,
        selected = selected,
        modifier = modifier,
        onItemClick = onTabClick,
    )
}

@Composable
private fun <T : SonrisasNavItem> SonrisasNavBar(
    items: List<T>,
    selected: T,
    modifier: Modifier = Modifier,
    onItemClick: (T) -> Unit = {},
) {
    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(color = LineColor)
        NavigationBar(
            containerColor = SurfaceWhite,
            contentColor = InkSoft,
            tonalElevation = 0.dp,
        ) {
            items.forEach { item ->
                val isSelected = item == selected
                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onItemClick(item) },
                    icon = { Icon(imageVector = item.icon, contentDescription = null) },
                    label = {
                        Text(
                            text = item.label,
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

@Preview(name = "Personal", showBackground = true, widthDp = 360)
@Composable
private fun SonrisasBottomBarPreview() {
    SonrisasAsistenciasTheme {
        SonrisasBottomBar(selected = SonrisasTab.Inicio)
    }
}

@Preview(name = "Responsable de estancia", showBackground = true, widthDp = 360)
@Composable
private fun ResponsableBottomBarPreview() {
    SonrisasAsistenciasTheme {
        ResponsableBottomBar(selected = ResponsableTab.Inicio)
    }
}