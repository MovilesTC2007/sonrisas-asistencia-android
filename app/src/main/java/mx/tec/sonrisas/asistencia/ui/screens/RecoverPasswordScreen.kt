package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.VpnKey
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusMd
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTypography
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

@Composable
fun RecoverPasswordScreen(
    email: String,
    onEmailChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onSendCodeClick: () -> Unit,
    onBackToLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    val isEmailValid = email.trim().let { it.contains("@")&&it.contains(".") }
    Scaffold(
        modifier = modifier,
        containerColor = BgCream,
    ){innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .padding(horizontal = Spacing.xxl),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(Spacing.xxl))
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBackClick, modifier = Modifier.size(48.dp)) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(BrandPurpleLight),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Regresar",
                            tint = BrandPurple,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                }

                Spacer(Modifier.size(Spacing.sm))
                Text(
                    text = "Recuperar contraseña",
                    style = SonrisasTypography.displayMedium,
                    color = InkPrimary,
                    modifier = Modifier.weight(1f),
                )
            }

            Spacer(modifier = Modifier.height(Spacing.xxl))
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .background(BrandPurpleLight),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Outlined.VpnKey,
                    contentDescription = null,
                    tint = BrandPurple,
                    modifier = Modifier.size(50.dp),
                )
            }

            Spacer(modifier = Modifier.height(Spacing.xxl))
            Text(
                text = "¿Olvidaste tu contraseña?",
                style = SonrisasTypography.displayLarge,
                color = InkPrimary,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(Spacing.md))
            Text(
                text = "Escribe el correo con el que te registraste. Te enviaremos un código de 6 dígitos.",
                style = SonrisasTypography.titleLarge,
                color = InkSoft,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(Spacing.xxl))
            EmailField(
                value = email,
                onValueChange = onEmailChange,
                onDone = { if (isEmailValid) onSendCodeClick() },
            )

            Spacer(modifier = Modifier.height(Spacing.xxl))
            SendCodeButton(
                enabled = isEmailValid,
                onClick = onSendCodeClick,
            )

            Text(
                text = "Volver a iniciar sesión",
                style = SonrisasTypography.titleMedium,
                color = BrandPurple,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clip(RadiusPill)
                    .clickable(onClick = onBackToLoginClick)
                    .padding(horizontal = Spacing.md, vertical = Spacing.lg),
            )
        }
    }
}

@Composable
private fun SendCodeButton(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (enabled) 16.dp else 0.dp,
                shape = RadiusPill,
                ambientColor = BrandPurple.copy(alpha = 0.35f),
                spotColor = BrandPurple.copy(alpha = 0.45f),
            )
            .clip(RadiusPill)
            .background(if (enabled) BrandPurple else BrandPurple.copy(alpha = 0.4f))
            .clickable(enabled = enabled, onClick = onClick)
            .padding(vertical = Spacing.xl),
        contentAlignment = Alignment.Center,
    ){
        Text(
            text = "Enviar código",
            style = SonrisasTypography.titleLarge,
            color = Color.White,
        )
    }
}
@Composable
private fun EmailField(
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier,
){
    Column(modifier = modifier.fillMaxWidth()){
        Text(
            text = "Correo electrónico",
            style = SonrisasTypography.titleLarge,
            color = InkFaint,
        )

        Spacer(modifier = Modifier.height(Spacing.sm))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = SonrisasTypography.bodyLarge.copy(color = InkPrimary),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
        ),
            keyboardActions = KeyboardActions(onDone = { onDone() }),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RadiusMd)
                        .background(SurfaceWhite)
                        .border(width = 1.dp, color = LineColor, shape = RadiusMd)
                        .padding(horizontal = Spacing.lg, vertical = Spacing.lg),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                text = "correo@ejemplo.com",
                                style = SonrisasTypography.titleLarge,
                                color = InkFaint,
                            )
                        }
                        innerTextField()
                    }

                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = null,
                        tint = InkFaint,
                        modifier = Modifier.size(25.dp),
                    )
                }
            },
        )
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun RecoverPasswordScreenPreview() {
    SonrisasAsistenciasTheme {
        var email by remember { mutableStateOf("fernando@gmail.com") }
        RecoverPasswordScreen(
            email = email,
            onEmailChange = { email = it },
            onBackClick = {},
            onSendCodeClick = {},
            onBackToLoginClick = {},
        )
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun RecoverPasswordScreenEmptyPreview() {
    SonrisasAsistenciasTheme {
        var email by remember { mutableStateOf("") }
        RecoverPasswordScreen(
            email = email,
            onEmailChange = { email = it },
            onBackClick = {},
            onSendCodeClick = {},
            onBackToLoginClick = {},
        )
    }
}