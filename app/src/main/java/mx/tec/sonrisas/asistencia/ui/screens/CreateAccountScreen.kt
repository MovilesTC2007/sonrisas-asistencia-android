package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTextField
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.RadiusSm
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

@Composable
fun CreateAccountScreen(modifier: Modifier = Modifier) {
    // Valores iniciales tomados del mockup.
    var invitationCode by rememberSaveable { mutableStateOf("SC8A2M6P") }
    var fullName by rememberSaveable { mutableStateOf("Fernando Anckerman Ramírez") }
    var email by rememberSaveable { mutableStateOf("fernando@gmail.com") }
    var password by rememberSaveable { mutableStateOf("contrasena") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var privacyAccepted by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgCream)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.xl),
        ) {
            Spacer(Modifier.height(Spacing.md))
            CreateAccountHeader()

            Spacer(Modifier.height(Spacing.md))
            Text(
                text = "Tu responsable te comparte el código para unirte a tu estancia.",
                style = SonrisasTextStyle.Body,
                color = InkSoft,
            )

            // Tarjeta 1: Código Invitación
            Spacer(Modifier.height(Spacing.xl))
            FormCard {
                SonrisasTextField(
                    value = invitationCode,
                    onValueChange = { invitationCode = it },
                    label = "Código de invitación",
                    icon = Icons.Outlined.Key,
                )
            }
            Spacer(Modifier.height(Spacing.sm))
            EstanciaCaption(estancia = "Mi Segunda Casita")

            // Tarjeta 2: Datos de la Persona
            Spacer(Modifier.height(Spacing.lg))
            FormCard {
                SonrisasTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = "Nombre completo",
                    icon = Icons.Outlined.Person,
                )
                Spacer(Modifier.height(Spacing.md))
                SonrisasTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Correo electrónico",
                    icon = Icons.Outlined.Email,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                )
                Spacer(Modifier.height(Spacing.md))
                SonrisasTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Contraseña",
                    icon = Icons.Outlined.Lock,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingContent = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) {
                                    Icons.Outlined.Visibility
                                } else {
                                    Icons.Outlined.VisibilityOff
                                },
                                contentDescription = if (passwordVisible) {
                                    "Ocultar contraseña"
                                } else {
                                    "Mostrar contraseña"
                                },
                                tint = InkFaint,
                                modifier = Modifier.size(20.dp),
                            )
                        }
                    },
                )
            }
            Spacer(Modifier.height(Spacing.sm))
            Text(
                text = "Usa al menos 8 caracteres, incluye al menos un carácter especial y un número.",
                style = SonrisasTextStyle.Caption,
                color = InkFaint,
                modifier = Modifier.padding(horizontal = Spacing.xs),
            )

            // Aviso de privacidad
            Spacer(Modifier.height(Spacing.xl))
            PrivacyConsentRow(
                checked = privacyAccepted,
                onCheckedChange = { privacyAccepted = it },
            )
            Spacer(Modifier.height(Spacing.md))
            Text(
                text = "La aceptación es necesaria para activar tu cuenta.",
                style = SonrisasTextStyle.Caption,
                color = InkFaint,
                modifier = Modifier.padding(horizontal = Spacing.xs),
            )
            Spacer(Modifier.height(Spacing.xxl))
        }

        SonrisasPrimaryButton(
            text = "Activar cuenta",
            onClick = {},
            modifier = Modifier.padding(start = Spacing.xl, end = Spacing.xl, top = Spacing.sm, bottom = Spacing.lg),
        )
    }
}

@Composable
private fun CreateAccountHeader() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        // Área táctil de 48 dp; el chip visible mide 40 dp.
        IconButton(onClick = {}, modifier = Modifier.size(48.dp)) {
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
            text = "Activar cuenta",
            style = SonrisasTextStyle.Display,
            color = InkPrimary,
        )
    }
}

@Composable
private fun FormCard(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceWhite)
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
        verticalArrangement = Arrangement.Top,
    ) {
        content()
    }
}

@Composable
private fun EstanciaCaption(estancia: String) {
    Text(
        text = buildAnnotatedString {
            append("Estancia: ")
            withStyle(SpanStyle(color = InkPrimary, fontWeight = FontWeight.SemiBold)) {
                append("“$estancia”")
            }
        },
        style = SonrisasTextStyle.Caption,
        color = InkSoft,
        modifier = Modifier.padding(horizontal = Spacing.xs),
    )
}

@Composable
private fun PrivacyConsentRow(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceWhite)
            .padding(horizontal = Spacing.sm, vertical = Spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = BrandPurple,
                uncheckedColor = InkFaint,
                checkmarkColor = SurfaceWhite,
            ),
            modifier = Modifier.size(48.dp),
        )
        Spacer(Modifier.size(Spacing.xs))
        Column {
            Text(
                text = "Acepto el aviso de privacidad",
                style = SonrisasTextStyle.Body,
                color = InkPrimary,
            )
            Spacer(Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .clip(RadiusSm)
                    .clickable(onClick = {})
                    .padding(vertical = 2.dp),
            ) {
                Text(
                    text = "Leer aviso de privacidad",
                    style = SonrisasTextStyle.Caption.copy(fontWeight = FontWeight.SemiBold),
                    color = BrandPurple,
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun CreateAccountScreenPreview() {
    SonrisasAsistenciasTheme {
        CreateAccountScreen()
    }
}