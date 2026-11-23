package mx.tec.sonrisas.asistencia.ui.screens.responsableScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import mx.tec.sonrisas.asistencia.ui.components.SonrisasBackStyle
import mx.tec.sonrisas.asistencia.ui.components.SonrisasButtonSize
import mx.tec.sonrisas.asistencia.ui.components.SonrisasFormField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasInfoCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasInputField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasStepper
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.components.rememberSonrisasToast
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.StatusRedInk

// Nueva área (se abre desde "Agregar área" en Áreas y mínimos).

private const val DEFAULT_MINIMUM = 1

@Composable
fun NewAreaScreen(
    existingNames: List<String>,
    onBack: () -> Unit,
    onSave: (name: String, detail: String, minimum: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var name by rememberSaveable { mutableStateOf("") }
    var detail by rememberSaveable { mutableStateOf("") }
    var minimum by rememberSaveable { mutableIntStateOf(DEFAULT_MINIMUM) }
    val showSavedToast = rememberSonrisasToast("Área agregada")

    val trimmedName = name.trim()
    val isDuplicate = existingNames.any { it.equals(trimmedName, ignoreCase = true) }
    val canSave = trimmedName.isNotEmpty() && !isDuplicate

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
            SonrisasTopBar(title = "Nueva área", onBack = onBack, backStyle = SonrisasBackStyle.Plain)

            Spacer(Modifier.height(Spacing.lg))
            SonrisasFormField(label = "Nombre del área") {
                SonrisasInputField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = "Ej. Sala 4",
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Next,
                    ),
                )
            }
            if (isDuplicate) {
                Spacer(Modifier.height(Spacing.xs))
                Text(
                    text = "Ya existe un área con ese nombre.",
                    style = SonrisasTextStyle.Caption,
                    color = StatusRedInk,
                    modifier = Modifier.padding(horizontal = Spacing.xs),
                )
            }

            Spacer(Modifier.height(Spacing.lg))
            SonrisasFormField(label = "Descripción (opcional)") {
                SonrisasInputField(
                    value = detail,
                    onValueChange = { detail = it },
                    placeholder = "Ej. Sala 4 · 4 a 5 años",
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done,
                    ),
                )
            }

            Spacer(Modifier.height(Spacing.lg))
            SonrisasFormField(label = "Mínimo de personal") {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Personas que debe haber siempre",
                        style = SonrisasTextStyle.Body,
                        color = InkPrimary,
                        modifier = Modifier.weight(1f),
                    )
                    SonrisasStepper(
                        value = minimum,
                        onValueChange = { minimum = it },
                        label = "mínimo de la nueva área",
                    )
                }
            }

            Spacer(Modifier.height(Spacing.xl))
            SonrisasInfoCard(message = "Si el área queda con menos personal que su mínimo, te avisamos.")

            Spacer(Modifier.height(Spacing.xxl))
            SonrisasPrimaryButton(
                text = "Guardar área",
                onClick = {
                    onSave(trimmedName, detail.trim(), minimum)
                    showSavedToast()
                },
                enabled = canSave,
                size = SonrisasButtonSize.Large,
                elevated = true,
            )
            Spacer(Modifier.height(Spacing.lg))
        }
    }
}

@Preview(name = "Nueva área", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun NewAreaScreenPreview() {
    SonrisasAsistenciasTheme {
        NewAreaScreen(
            existingNames = listOf("Lactantes", "Maternales"),
            onBack = {},
            onSave = { _, _, _ -> },
        )
    }
}
