package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PersonRemove
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

@Composable
fun SonrisasOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        shape = RadiusPill,
        border = BorderStroke(1.5.dp, if (enabled) BrandPurple else LineColor),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = BrandPurple,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = InkFaint,
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
    ) {
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
            )
            Spacer(Modifier.width(Spacing.sm))
        }
        Text(text = text, style = SonrisasTextStyle.Button)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFBF6ED, widthDp = 360)
@Composable
private fun SonrisasOutlinedButtonPreview() {
    SonrisasAsistenciasTheme {
        Column(Modifier.padding(Spacing.xl)) {
            SonrisasOutlinedButton(text = "Trasladar", onClick = {}, leadingIcon = Icons.Outlined.SwapHoriz)
            Spacer(Modifier.height(Spacing.md))
            SonrisasOutlinedButton(text = "Dar de baja", onClick = {}, leadingIcon = Icons.Outlined.PersonRemove)
            Spacer(Modifier.height(Spacing.md))
            SonrisasOutlinedButton(text = "Deshabilitado", onClick = {}, enabled = false)
        }
    }
}