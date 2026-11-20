package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasAvatar
import mx.tec.sonrisas.asistencia.ui.components.SonrisasNoticeBanner
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.AccentGreen
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.RadiusMd
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTypography
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceLilac
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

@Composable
fun CorrectRecordScreen(
    personName: String,
    personDetail: String,
    entryTime: String,
    exitTime: String,
    reasonOptions: List<String>,
    selectedReason: String?,
    onReasonSelected: (String) -> Unit,
    noteOptions: List<String>,
    selectedNote: String?,
    onNoteSelected: (String) -> Unit,
    onEntryClick: () -> Unit,
    onExitClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    exitHint: String? = null,
    onBackClick: () -> Unit = {},
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
            title = "Corregir registro",
            onBack = onBackClick,
        )
        Spacer(Modifier.height(Spacing.lg))
        PersonSummaryCard(name = personName, detail = personDetail)
        Spacer(Modifier.height(Spacing.lg))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            FieldLabel(text = "Entrada", modifier = Modifier.weight(1f))
            FieldLabel(text = "Salida", modifier = Modifier.weight(1f))
        }
        Spacer(Modifier.height(Spacing.sm))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            TimeField(
                time = entryTime,
                icon = Icons.Filled.Check,
                iconTint = AccentGreen,
                onClick = onEntryClick,
                modifier = Modifier.weight(1f),
            )
            TimeField(
                time = exitTime,
                icon = Icons.Outlined.Edit,
                iconTint = BrandPurple,
                onClick = onExitClick,
                modifier = Modifier.weight(1f),
            )
        }
        if (exitHint != null) {
            Spacer(Modifier.height(Spacing.sm))
            Text(
                text = exitHint,
                style = SonrisasTextStyle.Body,
                color = InkFaint,
            )
        }
        Spacer(Modifier.height(Spacing.lg))
        FieldLabel(text = "Motivo")
        Spacer(Modifier.height(Spacing.sm))
        OptionChips(
            options = reasonOptions,
            selected = selectedReason,
            onSelected = onReasonSelected,
        )
        Spacer(Modifier.height(Spacing.lg))
        FieldLabel(text = "Nota")
        Spacer(Modifier.height(Spacing.sm))
        OptionChips(
            options = noteOptions,
            selected = selectedNote,
            onSelected = onNoteSelected,
        )
        Spacer(Modifier.height(Spacing.lg))
        SonrisasNoticeBanner(
            icon = Icons.Outlined.Info,
            message = "Quedará en el historial que tú hiciste este cambio y cuándo.",
        )
        Spacer(Modifier.height(Spacing.xl))
        SonrisasPrimaryButton(
            text = "Guardar corrección",
            onClick = onSaveClick,
            enabled = selectedReason != null,
        )
        Spacer(Modifier.height(Spacing.xxl))
    }
}

@Composable
private fun PersonSummaryCard(
    name: String,
    detail: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceLilac)
            .padding(Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SonrisasAvatar(name = name, size = 64.dp)
        Spacer(Modifier.width(Spacing.lg))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                style = SonrisasTextStyle.Title,
                color = InkPrimary,
            )
            Text(
                text = detail,
                style = SonrisasTextStyle.Body,
                color = InkSoft,
            )
        }
    }
}

@Composable
private fun FieldLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = SonrisasTypography.titleLarge,
        color = InkFaint,
        modifier = modifier,
    )
}

@Composable
private fun TimeField(
    time: String,
    icon: ImageVector,
    iconTint: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .clip(RadiusMd)
            .background(SurfaceWhite)
            .border(BorderStroke(1.dp, LineColor), RadiusMd)
            .clickable(onClick = onClick)
            .padding(horizontal = Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = time,
            style = SonrisasTextStyle.Body,
            color = InkPrimary,
            modifier = Modifier.weight(1f),
        )
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(20.dp),
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun OptionChips(
    options: List<String>,
    selected: String?,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
        verticalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        options.forEach { option ->
            val isSelected = option == selected
            Box(
                modifier = Modifier
                    .clip(RadiusPill)
                    .background(if (isSelected) SurfaceLilac else SurfaceWhite)
                    .border(
                        border = BorderStroke(
                            width = if (isSelected) 1.5.dp else 1.dp,
                            color = if (isSelected) BrandPurple else LineColor,
                        ),
                        shape = RadiusPill,
                    )
                    .selectable(
                        selected = isSelected,
                        role = Role.RadioButton,
                        onClick = { onSelected(option) },
                    )
                    .padding(horizontal = Spacing.lg, vertical = Spacing.md),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = option,
                    style = SonrisasTypography.bodyLarge,
                    color = if (isSelected) BrandPurpleDeepText else InkSoft,
                )
            }
        }
    }
}

private val sampleReasons = listOf("Olvidó registrar", "Falla de GPS", "Otro")

private val sampleNotes = listOf(
    "Avisó por teléfono",
    "Lo confirmó su responsable",
    "Sin nota",
)

@Preview(showBackground = true)
@Composable
private fun CorrectRecordScreenPreview() {
    SonrisasAsistenciasTheme {
        var reason by remember { mutableStateOf<String?>("Olvidó registrar") }
        var note by remember { mutableStateOf<String?>("Avisó por teléfono") }

        CorrectRecordScreen(
            personName = "Carmen López Vega",
            personDetail = "Cocina · miércoles 7 oct",
            entryTime = "6:58",
            exitTime = "15:00",
            reasonOptions = sampleReasons,
            selectedReason = reason,
            onReasonSelected = { reason = it },
            noteOptions = sampleNotes,
            selectedNote = note,
            onNoteSelected = { note = it },
            onEntryClick = {},
            onExitClick = {},
            onSaveClick = {},
            exitHint = "La salida estaba vacía.",
        )
    }
}
