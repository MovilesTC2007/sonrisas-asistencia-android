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
import mx.tec.sonrisas.asistencia.ui.theme.StatusRedInk

@Composable
fun SonrisasOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    enabled: Boolean = true,
    //para acciones importantes como dar de baja y cosas asi conviene este tipo de diseseños y contraste
    destructive: Boolean = false,
) {
    val accent = if (destructive) StatusRedInk else BrandPurple
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        shape = RadiusPill,
        border = BorderStroke(1.5.dp, if (enabled) accent else LineColor),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = accent,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = InkFaint,
        ),
        //medidas correctas para boton
        modifier = modifier
            .fillMaxWidth()
            .height(SonrisasButtonHeight),
    ) {
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                modifier = Modifier.size(SonrisasButtonIconSize),
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
            SonrisasOutlinedButton(text = "Dar de baja", onClick = {}, leadingIcon = Icons.Outlined.PersonRemove, destructive = true)
            Spacer(Modifier.height(Spacing.md))
            SonrisasOutlinedButton(text = "Deshabilitado", onClick = {}, enabled = false)
        }
    }
}