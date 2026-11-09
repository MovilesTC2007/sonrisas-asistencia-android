package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasCheckbox
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTextField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.RadiusMd
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

private val CheckboxVisualSize = 24.dp

//para los eventos solo son {} sin la necesidad de pasarles algo
//ya que para la navegacion es preferible nunca tocar el diseño ya generad
@Composable
fun CreateAccountScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onActivate: (code: String, fullName: String, email: String, password: String) -> Unit = { _, _, _, _ -> },
    onReadPrivacyNotice: () -> Unit = {},
) {
    //eliminacion de datos para comnezar vacios para acciones directas
    var invitationCode by rememberSaveable { mutableStateOf("") }
    var fullName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var privacyAccepted by rememberSaveable { mutableStateOf(false) }

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
            SonrisasTopBar(title = "Activar cuenta", onBack = onBack)

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
                    // AUDITORÍA: "Siguiente" en el teclado pasa al próximo campo en vez de cerrar el teclado.
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
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
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                )
                Spacer(Modifier.height(Spacing.md))
                SonrisasTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Correo electrónico",
                    icon = Icons.Outlined.Email,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                    ),
                )
                Spacer(Modifier.height(Spacing.md))
                SonrisasTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Contraseña",
                    icon = Icons.Outlined.Lock,
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
            PrivacyConsentCard(
                checked = privacyAccepted,
                onCheckedChange = { privacyAccepted = it },
                onReadNotice = onReadPrivacyNotice,
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

        //solo habilitarlo cuando se acepta lo indicado (tal vez yo no era el indicado para ella)
        SonrisasPrimaryButton(
            text = "Activar cuenta",
            onClick = { onActivate(invitationCode, fullName, email, password) },
            enabled = privacyAccepted,
            modifier = Modifier.padding(start = Spacing.xl, end = Spacing.xl, top = Spacing.sm, bottom = Spacing.lg),
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

//Cambio primordial para la tarjeta de privacidad:
//se reutiliza el nuevo checkbox definido en componentes, el leer aviso se pasa a textbutton y no a box como antes
@Composable
private fun PrivacyConsentCard(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onReadNotice: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceWhite)
            .padding(Spacing.sm),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .clip(RadiusMd)
                .toggleable(value = checked, role = Role.Checkbox, onValueChange = onCheckedChange)
                .padding(horizontal = Spacing.sm),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SonrisasCheckbox(checked = checked, modifier = Modifier.size(CheckboxVisualSize))
            Spacer(Modifier.width(Spacing.md))
            Text(
                text = "Acepto el aviso de privacidad",
                style = SonrisasTextStyle.Body,
                color = InkPrimary,
            )
        }
        TextButton(
            onClick = onReadNotice,
            contentPadding = PaddingValues(horizontal = Spacing.sm),
            modifier = Modifier.padding(start = CheckboxVisualSize + Spacing.md),
        ) {
            Text(
                text = "Leer aviso de privacidad",
                style = SonrisasTextStyle.Caption.copy(fontWeight = FontWeight.SemiBold),
                color = BrandPurple,
            )
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
