package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

//medidas extra para botones y acciones
internal val SonrisasButtonHeight = 48.dp
internal val SonrisasButtonIconSize = 20.dp

@Composable
fun SonrisasPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RadiusPill,
        colors = ButtonDefaults.buttonColors(
            containerColor = BrandPurple,
            contentColor = SurfaceWhite,
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(SonrisasButtonHeight),
    ) {
        leadingIcon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                modifier = Modifier.size(SonrisasButtonIconSize),
            )
            Spacer(Modifier.width(Spacing.sm))
        }
        Text(text = text, style = SonrisasTextStyle.Button)
    }
}

@Composable
fun SonrisasTintedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RadiusPill,
        colors = ButtonDefaults.buttonColors(
            containerColor = BrandPurpleLight,
            contentColor = BrandPurpleDeepText,
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(SonrisasButtonHeight),
    ) {
        Text(text = text, style = SonrisasTextStyle.Button)
    }
}

@Composable
fun SonrisasTextLink(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    //sin forzar el fillmax
    TextButton(onClick = onClick, modifier = modifier) {
        Text(text = text, style = SonrisasTextStyle.Button, color = BrandPurple)
    }
}