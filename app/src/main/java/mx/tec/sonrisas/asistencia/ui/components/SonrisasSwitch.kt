package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

// interruptor (encendido / apagado): pista pastilla de 52x32 con pulgar blanco de 24 que se desliza
// encendido = pista morada de marca; apagado = pista LineColor y el pulgar lleva un aro tenue para que el estado
// no dependa solo del color (la pista apagada sobre blanco tiene poco contraste)
// el pulgar mide lo mismo en los dos estados (el Switch de Material lo achica apagado y no es lo que muestran las capturas)
//
// onCheckedChange = null (por defecto) lo vuelve decorativo, igual que SonrisasRadioButton: la fila que lo contiene
// es el area tactil (toggleable con Role.Switch) y el interruptor solo se dibuja. Si se pasa onCheckedChange, el interruptor
// mismo se vuelve tocable (Role.Switch); en ese caso quien lo use debe darle un area de 48 dp (por ejemplo con padding)
private val SwitchTrackWidth = 52.dp
private val SwitchTrackHeight = 32.dp
private val SwitchThumbSize = 24.dp
private val SwitchThumbInset = (SwitchTrackHeight - SwitchThumbSize) / 2

@Composable
fun SonrisasSwitch(
    checked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    enabled: Boolean = true,
) {
    val thumbOffset by animateDpAsState(
        targetValue = if (checked) SwitchTrackWidth - SwitchThumbSize - SwitchThumbInset else SwitchThumbInset,
        label = "switchThumb",
    )
    val track by animateColorAsState(
        targetValue = if (checked) BrandPurple else LineColor,
        label = "switchTrack",
    )
    val interactive = if (onCheckedChange != null) {
        Modifier.toggleable(
            value = checked,
            enabled = enabled,
            role = Role.Switch,
            onValueChange = onCheckedChange,
        )
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .alpha(if (enabled) 1f else 0.4f)
            .size(SwitchTrackWidth, SwitchTrackHeight)
            .clip(RadiusPill)
            .then(interactive)
            .background(track),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = thumbOffset)
                .size(SwitchThumbSize)
                .clip(CircleShape)
                .background(SurfaceWhite)
                .then(
                    if (checked) {
                        Modifier
                    } else {
                        Modifier.border(1.dp, InkFaint.copy(alpha = 0.5f), CircleShape)
                    },
                ),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SonrisasSwitchPreview() {
    SonrisasAsistenciasTheme {
        Row(
            modifier = Modifier.padding(Spacing.lg),
            horizontalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            SonrisasSwitch(checked = true)
            SonrisasSwitch(checked = false)
            SonrisasSwitch(checked = true, enabled = false)
            SonrisasSwitch(checked = false, enabled = false)
        }
    }
}
