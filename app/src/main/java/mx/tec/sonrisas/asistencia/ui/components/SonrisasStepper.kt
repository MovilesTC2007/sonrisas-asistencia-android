package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

private val StepperTouchArea = 48.dp
private val StepperCircle = 36.dp
private val StepperIconSize = 20.dp
private val StepperValueWidth = 36.dp

@Composable
fun SonrisasStepper(
    value: Int,
    onValueChange: (Int) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    valueRange: IntRange = 0..99,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StepperButton(
            icon = Icons.Rounded.Remove,
            description = "Disminuir $label",
            enabled = value > valueRange.first,
            onClick = { onValueChange((value - 1).coerceIn(valueRange)) },
        )
        Text(
            text = value.toString(),
            style = SonrisasTextStyle.Title,
            color = InkPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(StepperValueWidth)
                .semantics { liveRegion = LiveRegionMode.Polite },
        )
        StepperButton(
            icon = Icons.Rounded.Add,
            description = "Aumentar $label",
            enabled = value < valueRange.last,
            onClick = { onValueChange((value + 1).coerceIn(valueRange)) },
        )
    }
}

@Composable
private fun StepperButton(
    icon: ImageVector,
    description: String,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.size(StepperTouchArea),
    ) {
        Box(
            modifier = Modifier
                .alpha(if (enabled) 1f else 0.4f)
                .size(StepperCircle)
                .clip(CircleShape)
                .background(BrandPurpleLight),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = description,
                tint = BrandPurpleDeepText,
                modifier = Modifier.size(StepperIconSize),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 220)
@Composable
private fun SonrisasStepperPreview() {
    SonrisasAsistenciasTheme {
        var count by remember { mutableStateOf(3) }
        Column(
            modifier = Modifier.padding(Spacing.lg),
            verticalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            SonrisasStepper(value = count, onValueChange = { count = it }, label = "minimo de Lactantes")
            // en el limite inferior el boton de menos se atenua
            SonrisasStepper(value = 0, onValueChange = {}, label = "minimo de Apoyo")
        }
    }
}