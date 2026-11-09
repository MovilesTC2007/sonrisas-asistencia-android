package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.WifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.AccentOrange
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfacePeach

//generado para la parte de anuncios como el de sin conexcion y el de cobertura completa
//se utilizaba asi para Attendance y Dashboard para unificarlos
@Composable
fun SonrisasNoticeBanner(
    icon: ImageVector,
    message: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfacePeach)
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.md),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = AccentOrange,
            modifier = Modifier.size(20.dp),
        )
        Text(
            text = message,
            style = SonrisasTextStyle.Body,
            color = InkPrimary,
            modifier = Modifier.weight(1f),
        )
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun SonrisasNoticeBannerPreview() {
    SonrisasAsistenciasTheme {
        Column(
            modifier = Modifier.padding(Spacing.xl),
            verticalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            SonrisasNoticeBanner(
                icon = Icons.Outlined.WifiOff,
                message = "Sin conexión · 3 registros se enviarán al reconectar",
            )
            SonrisasNoticeBanner(
                icon = Icons.Outlined.ErrorOutline,
                message = "2 estancias con cobertura incompleta hoy",
            )
        }
    }
}
