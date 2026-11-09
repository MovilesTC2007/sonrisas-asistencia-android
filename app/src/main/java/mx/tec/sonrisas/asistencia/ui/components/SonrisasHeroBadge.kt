package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

//para el circulo grande de ubicacion y confirmacion para ser reutilizado sin tener que crearlo otra vez pues
@Composable
fun SonrisasHeroBadge(
    icon: ImageVector,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(88.dp)
            .clip(CircleShape)
            .background(BrandPurpleLight),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = BrandPurple,
            modifier = Modifier.size(40.dp),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFBF6ED)
@Composable
private fun SonrisasHeroBadgePreview() {
    SonrisasAsistenciasTheme {
        Box(Modifier.padding(Spacing.xl)) {
            SonrisasHeroBadge(icon = Icons.Outlined.LocationOn)
        }
    }
}
