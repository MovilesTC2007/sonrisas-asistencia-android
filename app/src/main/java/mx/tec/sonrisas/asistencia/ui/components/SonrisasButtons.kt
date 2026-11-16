package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

// medidas extra para botones y acciones
internal val SonrisasButtonHeight = 48.dp
internal val SonrisasButtonIconSize = 20.dp

enum class SonrisasButtonSize(
    val height: Dp,
    val textStyle: TextStyle,
    val iconSize: Dp,
    val iconGap: Dp,
) {
    Regular(
        height = SonrisasButtonHeight,
        textStyle = SonrisasTextStyle.Button,
        iconSize = SonrisasButtonIconSize,
        iconGap = Spacing.sm,
    ),
    Large(
        height = 64.dp,
        textStyle = SonrisasTextStyle.Button.copy(fontSize = 18.sp, lineHeight = 22.sp),
        iconSize = 22.dp,
        iconGap = Spacing.md,
    ),
}

@Composable
fun SonrisasPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    size: SonrisasButtonSize = SonrisasButtonSize.Regular,
    elevated: Boolean = false,
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
            .height(size.height)
            .then(
                if (elevated) {
                    Modifier.shadow(
                        elevation = 12.dp,
                        shape = RadiusPill,
                        ambientColor = BrandPurple.copy(alpha = 0.25f),
                        spotColor = BrandPurple.copy(alpha = 0.35f),
                    )
                } else {
                    Modifier
                },
            ),
    ) {
        SonrisasButtonContent(text = text, leadingIcon = leadingIcon, size = size)
    }
}

@Composable
fun SonrisasTintedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    size: SonrisasButtonSize = SonrisasButtonSize.Regular,
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
            .height(size.height),
    ) {
        SonrisasButtonContent(text = text, leadingIcon = leadingIcon, size = size)
    }
}

@Composable
fun SonrisasTextLink(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // sin forzar el fillmax
    TextButton(onClick = onClick, modifier = modifier) {
        Text(text = text, style = SonrisasTextStyle.Button, color = BrandPurple)
    }
}

@Composable
private fun RowScope.SonrisasButtonContent(
    text: String,
    leadingIcon: ImageVector?,
    size: SonrisasButtonSize,
) {
    leadingIcon?.let {
        Icon(
            imageVector = it,
            contentDescription = null,
            modifier = Modifier.size(size.iconSize),
        )
        Spacer(Modifier.width(size.iconGap))
    }
    Text(text = text, style = size.textStyle)
}
