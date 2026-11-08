package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.AccentGreen
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusMd
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.RadiusSm
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTypography
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite


private const val codeLength=6
private const val minPasswordLength=8

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
    onResendClick: () -> Unit,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    val rules = listOf(
        PasswordRule("Al menos 8 caracteres", password.length>=minPasswordLength),
        PasswordRule("Un número", password.any { it.isDigit() }),
        PasswordRule("Un carácter especial", password.any { !it.isLetterOrDigit() }),
    )

    val canSave = code.length==codeLength&&rules.all { it.isMet }

    Scaffold(
        modifier = modifier,
        containerColor = BgCream,
    ){innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = Spacing.xxl),
        ){
            Spacer(modifier = Modifier.height(Spacing.xxl))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
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

            Spacer(modifier = Modifier.height(Spacing.lg))
            Text(
                text = "Crea tu\nnueva contraseña",
                style = SonrisasTypography.displayMedium,
                color = InkPrimary,
            )

            Spacer(modifier = Modifier.height(Spacing.md))
            Text(
                text = "Enviamos un código a $maskedEmail. Escríbelo abajo.",
                style = SonrisasTypography.bodyLarge,
                color = InkSoft,
            )

            Spacer(modifier = Modifier.height(Spacing.xl))
            CodeInput(value = code, onValueChange = onCodeChange)

            Spacer(modifier = Modifier.height(Spacing.xxl))
            ResendCodeText(
                secondsRemaining = resendSecondsRemaining,
                onResendClick = onResendClick,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

            Spacer(modifier = Modifier.height(Spacing.xl))
            PasswordField(
                value = password,
                onValueChange = onPasswordChange,
                onDone = { if (canSave) onSaveClick() },
            )

            Spacer(modifier = Modifier.height(Spacing.lg))
            rules.forEach { rule ->
                PasswordRuleRow(rule = rule)
            }

            Spacer(modifier = Modifier.height(Spacing.xl))

            SaveButton(enabled = canSave, onClick = onSaveClick)
            Spacer(modifier = Modifier.height(Spacing.xxl))
        }
    }

}

@Composable
private fun SaveButton(
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
            text = "Guardar contraseña",
            style = SonrisasTypography.titleLarge,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
    }
}
@Composable
private fun PasswordRuleRow(
    rule: PasswordRule,
    modifier: Modifier = Modifier,
){
    Row(
        modifier = modifier.padding(vertical = Spacing.xs),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
    ){
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = null,
            tint = if (rule.isMet) AccentGreen else InkFaint.copy(alpha = 0.4f),
            modifier = Modifier.size(16.dp),
        )

        Text(
            text = rule.label,
            style = SonrisasTypography.bodyLarge,
            color = InkSoft,
        )
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
        modifier = modifier.fillMaxWidth()
    ){
        Text(
            text = "Nueva contraseña",
            style = SonrisasTypography.titleLarge,
            color = InkFaint,
        )

        Spacer(modifier = Modifier.height(Spacing.sm))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = SonrisasTypography.bodyLarge.copy(color = InkPrimary),
            cursorBrush = SolidColor(BrandPurple),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),

            keyboardActions = KeyboardActions(onDone = { onDone() }),
            modifier = Modifier.fillMaxWidth(),

            decorationBox = {innerTextField->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RadiusMd)
                        .background(SurfaceWhite)
                        .border(width = 1.dp, color = LineColor, shape = RadiusMd)
                        .padding(start = Spacing.lg, top = Spacing.xs, bottom = Spacing.xs, end = Spacing.xs),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Box(
                        modifier = Modifier.weight(1f)
                    ){
                        innerTextField()
                    }
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }){
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                            contentDescription = if (isPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                            tint = InkFaint,
                            modifier = Modifier.size(20.dp),
                        )

                    }

                }

            }

            )
    }
}

@Composable
private fun ResendCodeText(
    secondsRemaining: Int,
    onResendClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    if (secondsRemaining>0) {
        val minutes = secondsRemaining/60
        val seconds = (secondsRemaining%60).toString().padStart(2, '0')
        Text(
            text = "¿No te llegó? Reenviar en $minutes:$seconds",
            style = SonrisasTypography.bodyMedium,
            color = InkFaint,
            modifier = modifier,
        )
    } else {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "¿No te llegó? ",
                style = SonrisasTypography.bodyMedium,
                color = InkFaint,
            )
            Text(
                text = "Reenviar código",
                style = SonrisasTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = BrandPurple,
                modifier = Modifier
                    .clip(RadiusPill)
                    .clickable(onClick = onResendClick)
                    .padding(horizontal = Spacing.xs, vertical = Spacing.xs),
            )
        }
    }

}


@Composable
private fun CodeInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
){
    BasicTextField(
        value = value,
        onValueChange = { input ->
            onValueChange(input.filter { it.isDigit() }.take(codeLength))
        },

        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Next,
        ),

        modifier = modifier.fillMaxWidth(),
        decorationBox ={
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            ){
                repeat(codeLength){index->
                    val digit = value.getOrNull(index)?.toString().orEmpty()
                    val isFilled = index<value.length

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
                    ){
                        Text(
                            text = digit,
                            style = SonrisasTypography.displaySmall,
                            color = InkPrimary,
                        )
                    }
                }
            }
        }
    )
}


@Preview(showBackground = true, heightDp = 900)
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
            onResendClick = {},
            onBackClick = {},
            onSaveClick = {},
        )
    }
}

@Preview(showBackground = true, heightDp = 900)
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
            onResendClick = {},
            onBackClick = {},
            onSaveClick = {},
        )
    }
}