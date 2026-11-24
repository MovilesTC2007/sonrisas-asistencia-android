package mx.tec.sonrisas.asistencia.ui.screens.direccionScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasBackStyle
import mx.tec.sonrisas.asistencia.ui.components.SonrisasDateField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasDropdownField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasFormField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasInfoCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasInputField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

// Nuevo responsable (Dirección da de alta a quien dirige una estancia).
// Mismo flujo que con responsable -> empleado nuevo

private const val ESTANCIA_PLACEHOLDER = "Selecciona una estancia"

@Composable
fun NewResponsableScreen(
    estanciaOptions: List<String>,
    onBack: () -> Unit,
    onSaveClick: (name: String, estancia: String) -> Unit,
    modifier: Modifier = Modifier,
    startDate: String = "9 de noviembre de 2026",
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var estancia by rememberSaveable { mutableStateOf(ESTANCIA_PLACEHOLDER) }
    val canSave = name.isNotBlank() && estancia in estanciaOptions

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgCream)
            .statusBarsPadding(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.xl),
        ) {
            Spacer(Modifier.height(Spacing.md))
            SonrisasTopBar(title = "Nuevo responsable", onBack = onBack, backStyle = SonrisasBackStyle.Plain)

            Spacer(Modifier.height(Spacing.lg))
            SonrisasFormField(label = "Nombre completo") {
                SonrisasInputField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = "Escribe el nombre completo",
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next,
                    ),
                )
            }

            Spacer(Modifier.height(Spacing.lg))
            SonrisasFormField(label = "Correo de contacto") {
                SonrisasInputField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "correo@ejemplo.com",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done,
                    ),
                )
            }

            Spacer(Modifier.height(Spacing.lg))
            SonrisasFormField(label = "Estancia a cargo") {
                SonrisasDropdownField(
                    value = estancia,
                    options = estanciaOptions,
                    onSelect = { estancia = it },
                    label = "Estancia a cargo",
                )
            }

            Spacer(Modifier.height(Spacing.lg))
            SonrisasFormField(label = "Fecha de alta") {
                SonrisasDateField(
                    value = startDate,
                    label = "Fecha de alta",
                    onClick = {},
                )
            }

            Spacer(Modifier.height(Spacing.xl))
            SonrisasInfoCard(
                message = "Al guardar se genera su código de invitación. " +
                    "Con él crea su cuenta y queda como responsable de la estancia que elegiste.",
            )
            Spacer(Modifier.height(Spacing.xxl))
            SonrisasPrimaryButton(
                text = "Guardar y generar código",
                onClick = { onSaveClick(name.trim(), estancia) },
                enabled = canSave,
                modifier = Modifier.shadow(
                    elevation = 12.dp,
                    shape = RadiusPill,
                    ambientColor = BrandPurple.copy(alpha = 0.25f),
                    spotColor = BrandPurple.copy(alpha = 0.35f),
                ),
            )
            Spacer(Modifier.height(Spacing.lg))
        }
    }
}

@Preview(name = "Nuevo responsable", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun NewResponsableScreenPreview() {
    SonrisasAsistenciasTheme {
        NewResponsableScreen(
            estanciaOptions = listOf("Arcoíris", "Los Girasoles", "Luna Nueva"),
            onBack = {},
            onSaveClick = { _, _ -> },
        )
    }
}
