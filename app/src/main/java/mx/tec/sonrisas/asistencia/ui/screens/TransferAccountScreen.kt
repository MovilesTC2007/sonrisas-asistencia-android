package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasBackStyle
import mx.tec.sonrisas.asistencia.ui.components.SonrisasFormField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasInfoCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasInputField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasNoticeBanner
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTextField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.AccentGreen
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.StatusGreenTint

enum class TransferStep { Details, Verify, Done }

const val TRANSFER_CODE_LENGTH = 6

// Transferir la cuenta de Dirección en 3 pasos: datos de la persona, contraseña + código y resultado.
@Composable
fun TransferAccountScreen(
    step: TransferStep,
    newName: String,
    onNewNameChange: (String) -> Unit,
    newEmail: String,
    onNewEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    code: String,
    onCodeChange: (String) -> Unit,
    canContinue: Boolean,
    onContinue: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgCream)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.xl),
    ) {
        Spacer(Modifier.height(Spacing.md))
        SonrisasTopBar(
            title = "Transferir cuenta",
            onBack = if (step == TransferStep.Done) null else onBack,
            backStyle = SonrisasBackStyle.Plain,
        )
        Spacer(Modifier.height(Spacing.lg))
        when (step) {
            TransferStep.Details -> DetailsStep(
                newName = newName,
                onNewNameChange = onNewNameChange,
                newEmail = newEmail,
                onNewEmailChange = onNewEmailChange,
            )

            TransferStep.Verify -> VerifyStep(
                password = password,
                onPasswordChange = onPasswordChange,
                code = code,
                onCodeChange = onCodeChange,
            )

            TransferStep.Done -> DoneStep(newName = newName, newEmail = newEmail)
        }
        Spacer(Modifier.height(Spacing.xl))
        SonrisasPrimaryButton(
            text = when (step) {
                TransferStep.Details -> "Continuar"
                TransferStep.Verify -> "Confirmar transferencia"
                TransferStep.Done -> "Entendido"
            },
            onClick = onContinue,
            enabled = canContinue,
        )
        Spacer(Modifier.height(Spacing.xxl))
    }
}

@Composable
private fun DetailsStep(
    newName: String,
    onNewNameChange: (String) -> Unit,
    newEmail: String,
    onNewEmailChange: (String) -> Unit,
) {
    Text(
        text = "¿Quién va a ocupar la cuenta de Dirección?",
        style = SonrisasTextStyle.Title,
        color = InkPrimary,
    )
    Spacer(Modifier.height(Spacing.lg))
    SonrisasFormField(label = "Nombre completo") {
        SonrisasInputField(
            value = newName,
            onValueChange = onNewNameChange,
            placeholder = "Nombre y apellidos",
        )
    }
    Spacer(Modifier.height(Spacing.lg))
    SonrisasFormField(label = "Correo") {
        SonrisasInputField(
            value = newEmail,
            onValueChange = onNewEmailChange,
            placeholder = "correo@ejemplo.com",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
    }
    Spacer(Modifier.height(Spacing.lg))
    SonrisasInfoCard(
        message = "Solo existe una cuenta de Dirección. Al confirmar, tu acceso se cierra y la nueva " +
            "persona recibe una contraseña temporal por correo.",
    )
}

@Composable
private fun VerifyStep(
    password: String,
    onPasswordChange: (String) -> Unit,
    code: String,
    onCodeChange: (String) -> Unit,
) {
    Text(
        text = "Confirma que eres tú",
        style = SonrisasTextStyle.Title,
        color = InkPrimary,
    )
    Spacer(Modifier.height(Spacing.lg))
    SonrisasTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = "Tu contraseña",
        icon = Icons.Outlined.Lock,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    )
    Spacer(Modifier.height(Spacing.lg))
    SonrisasFormField(label = "Código de verificación") {
        SonrisasInputField(
            value = code,
            onValueChange = onCodeChange,
            placeholder = "$TRANSFER_CODE_LENGTH dígitos",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        )
    }
    Spacer(Modifier.height(Spacing.lg))
    SonrisasNoticeBanner(
        icon = Icons.Outlined.ErrorOutline,
        message = "Esta acción no se puede deshacer. Perderás el acceso a la cuenta.",
    )
}

@Composable
private fun DoneStep(
    newName: String,
    newEmail: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(StatusGreenTint),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.CheckCircle,
                contentDescription = null,
                tint = AccentGreen,
                modifier = Modifier.size(36.dp),
            )
        }
        Spacer(Modifier.height(Spacing.lg))
        Text(
            text = "Transferencia enviada",
            style = SonrisasTextStyle.Title,
            color = InkPrimary,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(Spacing.sm))
        Text(
            text = "$newName recibirá una contraseña temporal en $newEmail. " +
                "Tu sesión se cerrará al continuar.",
            style = SonrisasTextStyle.Body,
            color = InkSoft,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(name = "Transferir · datos", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun TransferDetailsPreview() {
    SonrisasAsistenciasTheme {
        TransferAccountScreen(
            step = TransferStep.Details,
            newName = "Laura Méndez",
            onNewNameChange = {},
            newEmail = "laura@correo.com",
            onNewEmailChange = {},
            password = "",
            onPasswordChange = {},
            code = "",
            onCodeChange = {},
            canContinue = true,
            onContinue = {},
            onBack = {},
        )
    }
}

@Preview(name = "Transferir · verificación", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun TransferVerifyPreview() {
    SonrisasAsistenciasTheme {
        TransferAccountScreen(
            step = TransferStep.Verify,
            newName = "Laura Méndez",
            onNewNameChange = {},
            newEmail = "laura@correo.com",
            onNewEmailChange = {},
            password = "secreto123",
            onPasswordChange = {},
            code = "4829",
            onCodeChange = {},
            canContinue = false,
            onContinue = {},
            onBack = {},
        )
    }
}

@Preview(name = "Transferir · resultado", showBackground = true, widthDp = 360, heightDp = 700)
@Composable
private fun TransferDonePreview() {
    SonrisasAsistenciasTheme {
        TransferAccountScreen(
            step = TransferStep.Done,
            newName = "Laura Méndez",
            onNewNameChange = {},
            newEmail = "laura@correo.com",
            onNewEmailChange = {},
            password = "",
            onPasswordChange = {},
            code = "",
            onCodeChange = {},
            canContinue = true,
            onContinue = {},
            onBack = {},
        )
    }
}
