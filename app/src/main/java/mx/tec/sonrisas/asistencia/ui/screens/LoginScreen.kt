package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import mx.tec.sonrisas.asistencia.ui.components.LockIcon
import mx.tec.sonrisas.asistencia.ui.components.MailIcon
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTextField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTextLink
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTintedButton
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeep
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

@Composable
fun LoginScreen(
    onLogin: (email: String, password: String) -> Unit = { _, _ -> },
    onForgotPassword: () -> Unit = {},
    onInviteCode: () -> Unit = {},
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.xl),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Spacing.xxl, bottom = Spacing.lg)
                .clip(RadiusLg)
                .background(BrandPurpleDeep)
                .padding(Spacing.lg),
        ) {
            Text(
                text = "Hola de nuevo",
                style = SonrisasTextStyle.Title,
                color = SurfaceWhite,
            )
            Text(
                text = "Sonrisas de Cristal · Personal",
                style = SonrisasTextStyle.Caption,
                color = SurfaceWhite.copy(alpha = 0.72f),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RadiusLg)
                .background(SurfaceWhite)
                .padding(horizontal = Spacing.lg, vertical = Spacing.sm),
        ) {
            SonrisasTextField(
                value = email,
                onValueChange = { email = it },
                label = "Correo electrónico",
                icon = MailIcon,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
            )
            SonrisasTextField(
                value = password,
                onValueChange = { password = it },
                label = "Contraseña",
                icon = LockIcon,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                visualTransformation = if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingContent = {
                    Text(
                        text = if (passwordVisible) "Ocultar" else "Mostrar",
                        style = SonrisasTextStyle.Caption,
                        color = BrandPurple,
                        modifier = Modifier.clickable { passwordVisible = !passwordVisible },
                    )
                },
            )
        }

        Spacer(modifier = Modifier.height(Spacing.lg))
        SonrisasPrimaryButton(text = "Entrar", onClick = { onLogin(email, password) })
        Spacer(modifier = Modifier.height(Spacing.sm))
        SonrisasTextLink(text = "¿Olvidaste tu contraseña?", onClick = onForgotPassword)
        Spacer(modifier = Modifier.height(Spacing.sm))
        SonrisasTintedButton(text = "Código de invitación", onClick = onInviteCode)
        Spacer(modifier = Modifier.height(Spacing.xxl))
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun LoginScreenPreview() {
    SonrisasAsistenciasTheme {
        LoginScreen()
    }
}
