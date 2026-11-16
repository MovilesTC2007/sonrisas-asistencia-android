package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.StatusBlueInk
import mx.tec.sonrisas.asistencia.ui.theme.StatusBlueTint
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceLilac

// aviso informativo con icono, para explicar lo que pasara al guardar o confirmar
// no se mezcla con SonrisasNoticeBanner: ese es durazno + naranja y comunica estado (sin conexion, cobertura incompleta)
// este es informativo y es texto largo, por eso el icono se alinea con la primera linea y el interlineado es mas abierto
private val InfoCardLineHeight = 26.sp
private val InfoCardIconSize = 20.dp

enum class SonrisasInfoTone(
    val container: Color,
    val iconTint: Color,
    val text: Color,
) {
    Lilac(container = SurfaceLilac, iconTint = BrandPurple, text = InkPrimary),
    Blue(container = StatusBlueTint, iconTint = StatusBlueInk, text = InkPrimary),
}

@Composable
fun SonrisasInfoCard(
    message: String,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Outlined.Info,
    tone: SonrisasInfoTone = SonrisasInfoTone.Lilac,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(tone.container)
            .padding(horizontal = Spacing.xl, vertical = Spacing.lg),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(Spacing.md),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tone.iconTint,
            modifier = Modifier
                .padding(top = 3.dp)
                .size(InfoCardIconSize),
        )
        Text(
            text = message,
            style = SonrisasTextStyle.Body.copy(lineHeight = InfoCardLineHeight),
            color = tone.text,
            modifier = Modifier.weight(1f),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFBF6ED, widthDp = 360)
@Composable
private fun SonrisasInfoCardPreview() {
    SonrisasAsistenciasTheme {
        Column(
            modifier = Modifier.padding(Spacing.xl),
            verticalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            SonrisasInfoCard(
                message = "Al guardar se genera su código de invitación. Con él crea su cuenta y queda ligada a Arcoíris.",
            )
            SonrisasInfoCard(
                message = "Su historial se conserva y cuenta en el reporte de rotación.",
                tone = SonrisasInfoTone.Blue,
            )
        }
    }
}