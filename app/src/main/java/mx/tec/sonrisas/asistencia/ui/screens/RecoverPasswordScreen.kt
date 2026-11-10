package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.VpnKey
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import mx.tec.sonrisas.asistencia.ui.components.SonrisasHeroBadge
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTextField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTextLink
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

@Composable
fun RecoverPasswordScreen(
    email: String,
    onEmailChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onSendCodeClick: () -> Unit = {},
    onBackToLoginClick: () -> Unit = {},
) {
    val isEmailValid = email.trim().let { it.contains("@") && it.contains(".") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgCream)
            .statusBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.xl),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(Spacing.md))
        SonrisasTopBar(title = "Recuperar contraseña", onBack = onBackClick)
        Spacer(Modifier.height(Spacing.xxl))
        SonrisasHeroBadge(icon = Icons.Outlined.VpnKey)
        Spacer(Modifier.height(Spacing.xxl))
        Text(
            text = "¿Olvidaste tu contraseña?",
            style = SonrisasTextStyle.Display,
            color = InkPrimary,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(Spacing.md))
        Text(
            text = "Escribe el correo con el que te registraste. Te enviaremos un código de 6 dígitos.",
            style = SonrisasTextStyle.Body,
            color = InkSoft,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(Spacing.xxl))
        EmailField(
            value = email,
            onValueChange = onEmailChange,
            onDone = { if (isEmailValid) onSendCodeClick() },
        )
        Spacer(Modifier.height(Spacing.xxl))
        SonrisasPrimaryButton(
            text = "Enviar código",
            onClick = onSendCodeClick,
            enabled = isEmailValid,
        )
        Spacer(Modifier.height(Spacing.sm))
        SonrisasTextLink(
            text = "Volver a iniciar sesión",
            onClick = onBackToLoginClick,
        )
        Spacer(Modifier.height(Spacing.xxl))
    }
}

@Composable
private fun EmailField(
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceWhite)
            .padding(horizontal = Spacing.lg, vertical = Spacing.sm),
    ) {
        SonrisasTextField(
            value = value,
            onValueChange = onValueChange,
            label = "Correo electrónico",
            icon = Icons.Outlined.Email,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(onDone = { onDone() }),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecoverPasswordScreenPreview() {
    SonrisasAsistenciasTheme {
        var email by remember { mutableStateOf("fernando@gmail.com") }
        RecoverPasswordScreen(
            email = email,
            onEmailChange = { email = it },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecoverPasswordScreenEmptyPreview() {
    SonrisasAsistenciasTheme {
        var email by remember { mutableStateOf("") }
        RecoverPasswordScreen(
            email = email,
            onEmailChange = { email = it },
        )
    }
}
