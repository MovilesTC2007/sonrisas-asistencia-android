package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.navigation.SonrisasTab
import mx.tec.sonrisas.asistencia.ui.navigation.UserRole
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

interface SonrisasNavItem {
    val label: String
    val icon: ImageVector
}

//esta barra de navegacion no sabe nada en posiciones o rutas, es solamente componente de front y diseseños
//con IA se agregaron las transciones entre cada item de la NavigationBarItem 
//se pone un role.tab para la seleccion de una pestaña y su pocion para correcto consumo
@Composable
fun <T : SonrisasNavItem> SonrisasBottomBar(
    items: List<T>,
    selected: T?,
    onItemClick: (T) -> Unit,
    modifier: Modifier = Modifier,
    badgeCount: (T) -> Int = { 0 },
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(BgCream),
    ) {
        HorizontalDivider(color = LineColor)
        NavigationBar(
            containerColor = BgCream,
            contentColor = InkSoft,
            tonalElevation = 0.dp,
        ) {
            items.forEach { item ->
                val isSelected = item == selected
                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onItemClick(item) },
                    icon = {
                        TabIcon(
                            icon = item.icon,
                            selected = isSelected,
                            badgeCount = badgeCount(item),
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            style = SonrisasTextStyle.Caption.copy(
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                            ),
                            maxLines = 1,
                        )
                    },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = BrandPurpleDeepText,
                        selectedTextColor = BrandPurpleDeepText,
                        unselectedIconColor = InkSoft,
                        unselectedTextColor = InkSoft,
                        indicatorColor = Color.Transparent,
                    ),
                )
            }
        }
    }
}

@Composable
private fun TabIcon(
    icon: ImageVector,
    selected: Boolean,
    badgeCount: Int,
) {
    //`Animatable` para animacion manual
    val scale = remember { Animatable(1f) }
    var wasSelected by remember { mutableStateOf(selected) } //mutable tambien para que no se mueva varias veces

    LaunchedEffect(selected) {
        val justSelected = selected && !wasSelected
        wasSelected = selected
        if (justSelected) {
            scale.animateTo(1.2f, animationSpec = tween(durationMillis = 110))   
            scale.animateTo(
                1f,
                animationSpec = spring(                                           
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium,
                ),
            )
        } else {
            scale.snapTo(1f) //para media animacion
        }
    }

    BadgedBox(
        badge = {
            if (badgeCount > 0) {
                Badge(
                    containerColor = BrandPurple,
                    contentColor = SurfaceWhite,
                ) {
                    val text = if (badgeCount > 9) "9+" else badgeCount.toString()
                    Text(
                        text = text,
                        modifier = Modifier.semantics { contentDescription = "$badgeCount pendientes" },
                    )
                }
            }
        },
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
            },
        )
    }
}

//roles 

@Preview(name = "Barra · Empleado", showBackground = true, widthDp = 360)
@Composable
private fun EmpleadoBottomBarPreview() {
    SonrisasAsistenciasTheme {
        SonrisasBottomBar(
            items = UserRole.Empleado.tabs,
            selected = SonrisasTab.Inicio,
            onItemClick = {},
        )
    }
}

@Preview(name = "Barra · Responsable (con pendientes)", showBackground = true, widthDp = 360)
@Composable
private fun ResponsableBottomBarPreview() {
    SonrisasAsistenciasTheme {
        SonrisasBottomBar(
            items = UserRole.Responsable.tabs,
            selected = SonrisasTab.Personal,
            onItemClick = {},
            badgeCount = { if (it == SonrisasTab.Solicitudes) 2 else 0 },
        )
    }
}

@Preview(name = "Barra · Dirección", showBackground = true, widthDp = 360)
@Composable
private fun DireccionBottomBarPreview() {
    SonrisasAsistenciasTheme {
        SonrisasBottomBar(
            items = UserRole.Direccion.tabs,
            selected = SonrisasTab.Reportes,
            onItemClick = {},
        )
    }
}