package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

// tarjeta de ruta "De X ⇄ A Y" (Traslado entre estancias): origen a la izquierda, destino a la derecha alineado al final
// las dos columnas pesan igual y el icono va en medio, asi un nombre largo (Rayito de Sol) se parte en dos lineas sin empujar el icono
// el origen se centra vertical respecto al destino cuando este ocupa mas lineas
// se lee como un solo bloque en lector de pantalla; el icono es decorativo
// la sombra se aplica desde fuera: SonrisasRouteCard(modifier = Modifier.sonrisasCardShadow())
@Composable
fun SonrisasRouteCard(
    from: String,
    to: String,
    modifier: Modifier = Modifier,
    fromLabel: String = "De",
    toLabel: String = "A",
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceWhite)
            .padding(horizontal = Spacing.xl, vertical = Spacing.xl)
            .semantics(mergeDescendants = true) {},
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = fromLabel,
                style = SonrisasTextStyle.Body,
                color = InkSoft,
            )
            Text(
                text = from,
                style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.SemiBold),
                color = InkPrimary,
            )
        }
        Icon(
            imageVector = Icons.Outlined.SwapHoriz,
            contentDescription = null,
            tint = BrandPurple,
            modifier = Modifier
                .padding(horizontal = Spacing.lg)
                .size(24.dp),
        )
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End,
        ) {
            Text(
                text = toLabel,
                style = SonrisasTextStyle.Body,
                color = InkSoft,
                textAlign = TextAlign.End,
            )
            Text(
                text = to,
                style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.SemiBold),
                color = InkPrimary,
                textAlign = TextAlign.End,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFBF6ED, widthDp = 360)
@Composable
private fun SonrisasRouteCardPreview() {
    SonrisasAsistenciasTheme {
        Column(
            modifier = Modifier.padding(Spacing.xl),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg),
        ) {
            SonrisasRouteCard(
                from = "Arcoíris",
                to = "Rayito de Sol",
                modifier = Modifier.sonrisasCardShadow(),
            )
            SonrisasRouteCard(
                from = "Los Girasoles",
                to = "Luna Nueva",
                modifier = Modifier.sonrisasCardShadow(),
            )
        }
    }
}
