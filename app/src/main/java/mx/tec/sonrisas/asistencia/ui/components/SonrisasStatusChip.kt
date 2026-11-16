package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.StatusGreenInk
import mx.tec.sonrisas.asistencia.ui.theme.StatusGreenTint
import mx.tec.sonrisas.asistencia.ui.theme.StatusYellowInk
import mx.tec.sonrisas.asistencia.ui.theme.StatusYellowTint

// pildora de estado: texto en tinta del estado sobre su tinte (Pendiente, Usado...)
// el color solo acompaña al texto, nunca va solo; igual que en los chips de estado de la guia de estilo
// quien lo use pasa el par tint / ink (por ejemplo StatusYellowTint / StatusYellowInk)
@Composable
fun SonrisasStatusChip(
    text: String,
    tint: Color,
    ink: Color,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.SemiBold),
        color = ink,
        maxLines = 1,
        modifier = modifier
            .clip(RadiusPill)
            .background(tint)
            .padding(horizontal = Spacing.md, vertical = 6.dp),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SonrisasStatusChipPreview() {
    SonrisasAsistenciasTheme {
        Row(
            modifier = Modifier.padding(Spacing.xl),
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SonrisasStatusChip(text = "Pendiente", tint = StatusYellowTint, ink = StatusYellowInk)
            SonrisasStatusChip(text = "Usado", tint = StatusGreenTint, ink = StatusGreenInk)
        }
    }
}