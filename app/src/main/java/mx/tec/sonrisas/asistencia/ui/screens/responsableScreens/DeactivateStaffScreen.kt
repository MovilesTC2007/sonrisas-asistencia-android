package mx.tec.sonrisas.asistencia.ui.screens.responsableScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PersonRemove
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasBackStyle
import mx.tec.sonrisas.asistencia.ui.components.SonrisasButtonSize
import mx.tec.sonrisas.asistencia.ui.components.SonrisasDateField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasFormField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasInfoCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasInfoTone
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasOutlinedButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPersonCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasRadioButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.components.rememberSonrisasToast
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeep
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

// Dar de baja (RF-11 · CU03).

private val DeactivationReasons = listOf(
    "Renuncia",
    "Término de contrato",
    "Abandono de trabajo",
    "Otro",
)

@Composable
fun DeactivateStaffScreen(
    modifier: Modifier = Modifier,
    personName: String = "Julia Morales Soto",
    personDetail: String = "Limpieza · Apoyo",
    initialReason: String = "Renuncia",
    lastDay: String = "30 de octubre de 2026",
    onSaveClick: () -> Unit = {},
    onBack: () -> Unit = {},
) {
    var reason by rememberSaveable { mutableStateOf(initialReason) }
    val showSavedToast = rememberSonrisasToast()
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
            SonrisasTopBar(
                title = "Dar de baja",
                onBack = onBack,
                backStyle = SonrisasBackStyle.Plain,
            )

            Spacer(Modifier.height(Spacing.lg))
            SonrisasPersonCard(name = personName, detail = personDetail)

            Spacer(Modifier.height(Spacing.xxl))
            SonrisasFormField(label = "Motivo") {
                SonrisasListCard(
                    items = DeactivationReasons,
                    verticalPadding = Spacing.xs,
                    modifier = Modifier
                        .shadow(
                            elevation = 8.dp,
                            shape = RadiusLg,
                            ambientColor = BrandPurpleDeep.copy(alpha = 0.08f),
                            spotColor = BrandPurpleDeep.copy(alpha = 0.12f),
                        )
                        .selectableGroup(),
                ) { option ->
                    ReasonRow(
                        text = option,
                        selected = option == reason,
                        onSelect = { reason = option },
                    )
                }
            }

            Spacer(Modifier.height(Spacing.xxl))
            SonrisasFormField(label = "Último día") {
                SonrisasDateField(
                    value = lastDay,
                    label = "Último día",
                    onClick = {},
                )
            }

            Spacer(Modifier.height(Spacing.xl))
            SonrisasInfoCard(
                message = "Su historial se conserva y cuenta en el reporte de rotación.",
                tone = SonrisasInfoTone.Blue,
            )

            Spacer(Modifier.height(Spacing.xxl + Spacing.sm))
            SonrisasOutlinedButton(
                text = "Confirmar baja",
                onClick = {
                    onSaveClick()
                    showSavedToast()
                },
                leadingIcon = Icons.Outlined.PersonRemove,
                destructive = true,
                size = SonrisasButtonSize.Large,
            )
            Spacer(Modifier.height(Spacing.lg))
        }
    }
}

@Composable
private fun ReasonRow(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 52.dp)
            .selectable(selected = selected, role = Role.RadioButton, onClick = onSelect)
            .padding(horizontal = Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SonrisasRadioButton(selected = selected)
        Spacer(Modifier.width(Spacing.lg))
        Text(
            text = text,
            style = SonrisasTextStyle.Body.copy(
                fontSize = 16.sp,
                lineHeight = 22.sp,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
            ),
            color = InkPrimary,
        )
    }
}

@Preview(name = "Dar de baja", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun DeactivateStaffScreenPreview() {
    SonrisasAsistenciasTheme {
        DeactivateStaffScreen()
    }
}

@Preview(name = "Dar de baja · otro motivo", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun DeactivateStaffScreenOtherReasonPreview() {
    SonrisasAsistenciasTheme {
        DeactivateStaffScreen(initialReason = "Otro")
    }
}
