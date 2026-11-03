package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

/** The solid pill button — primary action on a screen (Entrar, Comenzar, …). */
@Composable
fun SonrisasPrimaryButton(
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
            containerColor = BrandPurple,
            contentColor = SurfaceWhite,
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
    ) {
        Text(text = text, style = SonrisasTextStyle.Button)
    }
}

/** The tinted pill button — secondary action (Código de invitación, …). */
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
            .height(46.dp),
    ) {
        Text(text = text, style = SonrisasTextStyle.Button)
    }
}

/** A plain text link, centered — ¿Olvidaste tu contraseña?, and similar. */
@Composable
fun SonrisasTextLink(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(onClick = onClick, modifier = modifier.fillMaxWidth()) {
        Text(text = text, style = SonrisasTextStyle.Button, color = BrandPurple)
    }
}
