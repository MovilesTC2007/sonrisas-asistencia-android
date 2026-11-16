package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite


// en staff se utiliza una tarjeta blanca pero con divisores sin embargo se estaba agreango divisores de borde con otro color y espciado
// este compoente es para que cada fila agregada tenga su propio padding y asi no perder texto
// verticalPadding es el aire de la tarjeta arriba de la primera fila y abajo de la ultima (0 por defecto, como siempre)
@Composable
fun <T> SonrisasListCard(
    items: List<T>,
    modifier: Modifier = Modifier,
    verticalPadding: Dp = 0.dp,
    itemContent: @Composable (T) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceWhite)
            .padding(vertical = verticalPadding),
    ) {
        items.forEachIndexed { index, item ->
            itemContent(item)
            if (index < items.lastIndex) {
                HorizontalDivider(
                    color = LineColor,
                    modifier = Modifier.padding(horizontal = Spacing.lg),
                )
            }
        }
    }
}