package mx.tec.sonrisas.asistencia.ui.screens.responsableScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasDateField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasDropdownField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasFilterChip
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

// Nueva persona (Alta de persona, RF-02 · RF-11 · CU03).

private val NewStaffTypes = listOf("Maestra titular", "Asistente", "Cocina", "Limpieza", "Administrativo")
private val NewStaffAreas = listOf("Lactantes", "Maternales", "Apoyo")

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewStaffScreen(
    modifier: Modifier = Modifier,
    centerName: String = "Arcoíris",
    initialName: String = "Patricia Gómez Ríos",
    initialType: String? = "Asistente",
    initialArea: String = "Maternales",
    startDate: String = "9 de noviembre de 2026",
) {
    var name by rememberSaveable { mutableStateOf(initialName) }
    var type by rememberSaveable { mutableStateOf(initialType) }
    var area by rememberSaveable { mutableStateOf(initialArea) }

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
            SonrisasTopBar(title = "Nueva persona", onBack = {})

            Spacer(Modifier.height(Spacing.lg))
            SonrisasFormField(label = "Nombre completo") {
                SonrisasInputField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = "Escribe el nombre completo",
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Done,
                    ),
                )
            }

            Spacer(Modifier.height(Spacing.lg))
            SonrisasFormField(label = "Tipo de personal") {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectableGroup(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                    verticalArrangement = Arrangement.spacedBy(Spacing.xs),
                ) {
                    NewStaffTypes.forEach { option ->
                        SonrisasFilterChip(
                            text = option,
                            selected = option == type,
                            onClick = { type = option },
                        )
                    }
                }
            }

            Spacer(Modifier.height(Spacing.lg))
            SonrisasFormField(label = "Área") {
                SonrisasDropdownField(
                    value = area,
                    options = NewStaffAreas,
                    onSelect = { area = it },
                    label = "Área",
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
                    "Con él crea su cuenta y queda ligada a $centerName.",
            )
            Spacer(Modifier.height(Spacing.xxl))
            SonrisasPrimaryButton(
                text = "Guardar y generar código",
                onClick = {},
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

@Preview(name = "Nueva persona · datos del mockup", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun NewStaffScreenPreview() {
    SonrisasAsistenciasTheme {
        NewStaffScreen()
    }
}

@Preview(name = "Nueva persona · vacío", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun NewStaffScreenEmptyPreview() {
    SonrisasAsistenciasTheme {
        NewStaffScreen(initialName = "", initialType = null)
    }
}
