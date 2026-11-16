package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

// resumen de la persona sobre la que se actua (Dar de baja): avatar con iniciales + nombre + "Puesto · Area"
// fondo morado claro (fondo tintado), igual que la tarjeta de identidad de Detalle de personal
// InkSoft y no InkFaint para el detalle: sobre morado claro InkFaint no llega al contraste AA
// se lee como un solo bloque en lector de pantalla (el avatar es decorativo)
@Composable
fun SonrisasPersonCard(
    name: String,
    detail: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(BrandPurpleLight)
            .padding(horizontal = Spacing.xl, vertical = Spacing.lg)
            .semantics(mergeDescendants = true) {},
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SonrisasAvatar(name = name)
        Spacer(Modifier.width(Spacing.lg))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                style = SonrisasTextStyle.Title,
                color = InkPrimary,
            )
            Spacer(Modifier.height(Spacing.xs))
            Text(
                text = detail,
                style = SonrisasTextStyle.Body,
                color = InkSoft,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFBF6ED, widthDp = 360)
@Composable
private fun SonrisasPersonCardPreview() {
    SonrisasAsistenciasTheme {
        SonrisasPersonCard(
            name = "Julia Morales Soto",
            detail = "Limpieza · Apoyo",
            modifier = Modifier.padding(Spacing.xl),
        )
    }
}