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
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
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
fun AddNewStaffScreen(
    facilityName: String,
    fullName: String,
    onFullNameChange: (String) -> Unit,
    staffTypes: List<String>,
    selectedStaffType: String,
    onStaffTypeSelected: (String) -> Unit,
    areas: List<String>,
    selectedArea: String,
    onAreaSelected: (String) -> Unit,
    hireDate: String,
    onHireDateClick: () -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        containerColor = BgCream,
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BgCream),
            ) {
                HorizontalDivider(color = LineColor)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spacing.xxl, vertical = Spacing.lg),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .clip(RadiusPill)
                            .background(BrandPurple)
                            .clickable(onClick = onSaveClick),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "Guardar y generar código",
                            style = SonrisasTypography.titleLarge,
                            color = SurfaceWhite,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.xxl),
        ) {
            Spacer(modifier = Modifier.height(Spacing.xxl))
            Row(verticalAlignment = Alignment.CenterVertically) {
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
                    text = "Nueva persona",
                    style = SonrisasTypography.displayMedium,
                    color = InkPrimary,
                )
            }
            Spacer(modifier = Modifier.height(Spacing.xl))
            FieldLabel(text = "Nombre completo")
            Spacer(modifier = Modifier.height(Spacing.sm))
            FormFieldContainer {
                BasicTextField(
                    value = fullName,
                    onValueChange = onFullNameChange,
                    singleLine = true,
                    textStyle = SonrisasTypography.bodyLarge.copy(color = InkPrimary),
                    cursorBrush = SolidColor(BrandPurple),
                    modifier = Modifier.weight(1f),
                )
            }
            Spacer(modifier = Modifier.height(Spacing.lg))
            FieldLabel(text = "Tipo de personal")
            Spacer(modifier = Modifier.height(Spacing.sm))
            StaffTypeChips(
                options = staffTypes,
                selected = selectedStaffType,
                onSelected = onStaffTypeSelected,
            )
            Spacer(modifier = Modifier.height(Spacing.lg))
            FieldLabel(text = "Área")
            Spacer(modifier = Modifier.height(Spacing.sm))
            AreaDropdown(
                areas = areas,
                selectedArea = selectedArea,
                onAreaSelected = onAreaSelected,
            )
            Spacer(modifier = Modifier.height(Spacing.lg))
            FieldLabel(text = "Fecha de alta")
            Spacer(modifier = Modifier.height(Spacing.sm))
            FormFieldContainer(onClick = onHireDateClick) {
                Text(
                    text = hireDate,
                    style = SonrisasTypography.bodyLarge,
                    color = InkPrimary,
                    modifier = Modifier.weight(1f),
                )
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = "Seleccionar fecha",
                    tint = InkFaint,
                    modifier = Modifier.size(22.dp),
                )
            }
            Spacer(modifier = Modifier.height(Spacing.xl))
            InvitationInfoBanner(facilityName = facilityName)
            Spacer(modifier = Modifier.height(Spacing.xxl))
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
private fun FormFieldContainer(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val clickableModifier = if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .clip(RadiusMd)
            .background(SurfaceWhite)
            .border(BorderStroke(1.dp, LineColor), RadiusMd)
            .then(clickableModifier)
            .padding(horizontal = Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StaffTypeChips(
    options: List<String>,
    selected: String,
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
                    .clickable { onSelected(option) }
                    .padding(horizontal = Spacing.lg, vertical = Spacing.md),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = option,
                    style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.SemiBold),
                    color = if (isSelected) BrandPurpleDeepText else InkSoft,
                )
            }
        }
    }
}

@Composable
private fun AreaDropdown(
    areas: List<String>,
    selectedArea: String,
    onAreaSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxWidth()) {
        FormFieldContainer(onClick = { expanded = true }) {
            Text(
                text = selectedArea,
                style = SonrisasTypography.bodyLarge,
                color = InkPrimary,
                modifier = Modifier.weight(1f),
            )
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "Desplegar áreas",
                tint = InkFaint,
                modifier = Modifier.size(24.dp),
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = SurfaceWhite,
        ) {
            areas.forEach { area ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = area,
                            style = SonrisasTypography.bodyLarge,
                            color = InkPrimary,
                        )
                    },
                    onClick = {
                        onAreaSelected(area)
                        expanded = false
                    },
                )
            }
        }
    }
}

@Composable
private fun InvitationInfoBanner(
    facilityName: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceLilac)
            .padding(horizontal = Spacing.lg, vertical = Spacing.lg),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(Spacing.md),
    ) {
        Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = null,
            tint = BrandPurple,
            modifier = Modifier
                .padding(top = 2.dp)
                .size(20.dp),
        )
        Text(
            text = "Al guardar se genera su código de invitación. Con él crea su cuenta y queda ligada a $facilityName.",
            style = SonrisasTypography.bodyLarge,
            color = InkPrimary,
            modifier = Modifier.weight(1f),
        )
    }
}

private val sampleStaffTypes = listOf(
    "Maestra titular",
    "Asistente",
    "Cocina",
    "Limpieza",
    "Administrativo",
)

private val sampleAreas = listOf(
    "Lactantes",
    "Maternales",
    "Preescolar",
)

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun AddNewStaffScreenPreview() {
    SonrisasAsistenciasTheme {
        var name by remember { mutableStateOf("Patricia Gómez Ríos") }
        var type by remember { mutableStateOf("Asistente") }
        var area by remember { mutableStateOf("Maternales") }

        AddNewStaffScreen(
            facilityName = "Arcoíris",
            fullName = name,
            onFullNameChange = { name = it },
            staffTypes = sampleStaffTypes,
            selectedStaffType = type,
            onStaffTypeSelected = { type = it },
            areas = sampleAreas,
            selectedArea = area,
            onAreaSelected = { area = it },
            hireDate = "9 de noviembre de 2026",
            onHireDateClick = {},
        )
    }
}
