package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite


//en staff se utiliza una tarjeta blanca pero con divisores sin embargo se estaba agreango divisores de borde con otro color y espciado
//este compoente es para que cada fila agregada tenga su propio padding y asi no perder texto
@Composable
fun <T> SonrisasListCard(
    items: List<T>,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceWhite),
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
