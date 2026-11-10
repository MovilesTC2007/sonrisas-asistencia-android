package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTextField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTextLink
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.AccentGreen
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.RadiusSm
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

private const val CODE_LENGTH = 6
private const val MIN_PASSWORD_LENGTH = 8

private data class PasswordRule(
    val label: String,
    val isMet: Boolean,
)

@Composable
fun NewPasswordScreen(
    maskedEmail: String,
    code: String,
    onCodeChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    resendSecondsRemaining: Int,
    modifier: Modifier = Modifier,
    onResendClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
) {
    val rules = listOf(
        PasswordRule("Al menos 8 caracteres", password.length >= MIN_PASSWORD_LENGTH),
        PasswordRule("Un número", password.any { it.isDigit() }),
        PasswordRule("Un carácter especial", password.any { !it.isLetterOrDigit() }),
    )
    val canSave = code.length == CODE_LENGTH && rules.all { it.isMet }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgCream)
            .statusBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.xl),
    ) {
        Spacer(Modifier.height(Spacing.md))
        SonrisasTopBar(title = "Recuperar contraseña", onBack = onBackClick)
        Spacer(Modifier.height(Spacing.lg))
        Text(
            text = "Crea tu\nnueva contraseña",
            style = SonrisasTextStyle.Display,
            color = InkPrimary,
        )
        Spacer(Modifier.height(Spacing.md))
        Text(
            text = "Enviamos un código a $maskedEmail. Escríbelo abajo.",
            style = SonrisasTextStyle.Body,
            color = InkSoft,
        )
        Spacer(Modifier.height(Spacing.xl))
        CodeInput(value = code, onValueChange = onCodeChange)
        Spacer(Modifier.height(Spacing.xxl))
        ResendCodeText(
            secondsRemaining = resendSecondsRemaining,
            onResendClick = onResendClick,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Spacer(Modifier.height(Spacing.xl))
        PasswordField(
            value = password,
            onValueChange = onPasswordChange,
            onDone = { if (canSave) onSaveClick() },
        )
        Spacer(Modifier.height(Spacing.lg))
        rules.forEach { rule ->
            PasswordRuleRow(rule = rule)
        }
        Spacer(Modifier.height(Spacing.xl))
        SonrisasPrimaryButton(
            text = "Guardar contraseña",
            onClick = onSaveClick,
            enabled = canSave,
        )
        Spacer(Modifier.height(Spacing.xxl))
    }
}

@Composable
private fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

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
            label = "Nueva contraseña",
            icon = Icons.Outlined.Lock,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(onDone = { onDone() }),
            trailingContent = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                        tint = InkFaint,
                        modifier = Modifier.size(20.dp),
                    )
                }
            },
        )
    }
}

@Composable
private fun PasswordRuleRow(
    rule: PasswordRule,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(vertical = Spacing.xs),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = null,
            tint = if (rule.isMet) AccentGreen else InkFaint.copy(alpha = 0.4f),
            modifier = Modifier.size(16.dp),
        )
        Text(
            text = rule.label,
            style = SonrisasTextStyle.Body,
            color = InkSoft,
        )
    }
}

@Composable
private fun ResendCodeText(
    secondsRemaining: Int,
    onResendClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (secondsRemaining > 0) {
        val minutes = secondsRemaining / 60
        val seconds = (secondsRemaining % 60).toString().padStart(2, '0')
        Text(
            text = "¿No te llegó? Reenviar en $minutes:$seconds",
            style = SonrisasTextStyle.Body,
            color = InkFaint,
            modifier = modifier,
        )
    } else {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "¿No te llegó?",
                style = SonrisasTextStyle.Body,
                color = InkFaint,
            )
            SonrisasTextLink(text = "Reenviar código", onClick = onResendClick)
        }
    }
}

@Composable
private fun CodeInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicTextField(
        value = value,
        onValueChange = { input ->
            onValueChange(input.filter { it.isDigit() }.take(CODE_LENGTH))
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Next,
        ),
        modifier = modifier.fillMaxWidth(),
        decorationBox = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            ) {
                repeat(CODE_LENGTH) { index ->
                    val digit = value.getOrNull(index)?.toString().orEmpty()
                    val isFilled = index < value.length

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(64.dp)
                            .clip(RadiusSm)
                            .background(SurfaceWhite)
                            .border(
                                width = 2.dp,
                                color = if (isFilled) BrandPurple else LineColor,
                                shape = RadiusSm,
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = digit,
                            style = SonrisasTextStyle.Display,
                            color = InkPrimary,
                        )
                    }
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun NewPasswordCompletePreview() {
    SonrisasAsistenciasTheme {
        var code by remember { mutableStateOf("482917") }
        var password by remember { mutableStateOf("Sonrisa#2026") }
        NewPasswordScreen(
            maskedEmail = "fer•••@gmail.com",
            code = code,
            onCodeChange = { code = it },
            password = password,
            onPasswordChange = { password = it },
            resendSecondsRemaining = 42,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NewPasswordEmptyPreview() {
    SonrisasAsistenciasTheme {
        var code by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        NewPasswordScreen(
            maskedEmail = "fer•••@gmail.com",
            code = code,
            onCodeChange = { code = it },
            password = password,
            onPasswordChange = { password = it },
            resendSecondsRemaining = 0,
        )
    }
}
