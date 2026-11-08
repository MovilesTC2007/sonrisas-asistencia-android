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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.ErrorOutline
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.AccentGreen
import mx.tec.sonrisas.asistencia.ui.theme.AccentRed
import mx.tec.sonrisas.asistencia.ui.theme.AccentYellow
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusMd
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTypography
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

enum class RequestType(val label: String) {
    Absence("Falta"),
    Permission("Permiso"),
    ShiftChange("Cambio de turno"),
}

enum class RequestStatus { Pending, Approved, Rejected }

data class RequestHistoryItem(
    val title: String,
    val status: RequestStatus,
)

@Composable
fun RequestsScreen(
    selectedType: RequestType,
    onTypeSelected: (RequestType) -> Unit,
    dateText: String,
    onDateClick: () -> Unit,
    reason: String,
    onReasonChange: (String) -> Unit,
    history: List<RequestHistoryItem>,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        containerColor = BgCream,
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
                IconButton(onClick = {}, modifier = Modifier.size(48.dp)) {
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
                    text = "Solicitudes",
                    style = SonrisasTypography.displayMedium,
                    color = InkPrimary,
                )
            }
            Spacer(modifier = Modifier.height(Spacing.sm))
            Text(
                text = "¿Qué necesitas avisar?",
                style = SonrisasTypography.bodyLarge,
                color = InkPrimary,
            )
            Spacer(modifier = Modifier.height(Spacing.lg))
            RequestTypeSelector(
                selectedType = selectedType,
                onTypeSelected = onTypeSelected,
            )
            Spacer(modifier = Modifier.height(Spacing.xl))
            FieldLabel(text = "Fecha")
            Spacer(modifier = Modifier.height(Spacing.sm))
            DateField(text = dateText, onClick = onDateClick)
            Spacer(modifier = Modifier.height(Spacing.lg))
            FieldLabel(text = "Motivo")
            Spacer(modifier = Modifier.height(Spacing.sm))
            ReasonField(value = reason, onValueChange = onReasonChange)
            Spacer(modifier = Modifier.height(Spacing.xl))
            SendRequestButton(
                enabled = reason.isNotBlank(),
                onClick = onSendClick,
            )
            Spacer(modifier = Modifier.height(Spacing.xxl))
            Text(
                text = "Historial",
                style = SonrisasTypography.titleLarge,
                color = InkPrimary,
            )
            Spacer(modifier = Modifier.height(Spacing.sm))
            history.forEachIndexed { index, item ->
                HistoryRow(item = item)
                if (index<history.lastIndex) {
                    HorizontalDivider(color = LineColor)
                }
            }
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
        style = SonrisasTypography.titleMedium,
        color = InkFaint,
        modifier = modifier,
    )
}

@Composable
private fun RequestTypeSelector(
    selectedType: RequestType,
    onTypeSelected: (RequestType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusPill)
            .background(LineColor)
            .padding(Spacing.xs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RequestType.values().forEach { type ->
            val isSelected = type==selectedType
            Box(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 52.dp)
                    .clip(RadiusPill)
                    .background(if (isSelected) BrandPurple else Color.Transparent)
                    .clickable { onTypeSelected(type) }
                    .padding(horizontal = Spacing.sm),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = type.label,
                    style = SonrisasTypography.titleMedium,
                    color = if (isSelected) Color.White else InkFaint,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun DateField(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusMd)
            .background(SurfaceWhite)
            .border(width = 1.dp, color = LineColor, shape = RadiusMd)
            .clickable(onClick = onClick)
            .padding(horizontal = Spacing.lg, vertical = Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = SonrisasTypography.bodyLarge,
            color = InkPrimary,
            modifier = Modifier.weight(1f),
        )
        Icon(
            imageVector = Icons.Outlined.CalendarToday,
            contentDescription = "Seleccionar fecha",
            tint = InkFaint,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Composable
private fun ReasonField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = SonrisasTypography.bodyLarge,
        cursorBrush = SolidColor(BrandPurple),
        modifier = modifier.fillMaxWidth(),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 112.dp)
                    .clip(RadiusMd)
                    .background(SurfaceWhite)
                    .border(width = 1.dp, color = LineColor, shape = RadiusMd)
                    .padding(Spacing.lg),
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = "Describe brevemente el motivo",
                        style = SonrisasTypography.bodyLarge,
                        color = InkFaint,
                    )
                }
                innerTextField()
            }
        },
    )
}

@Composable
private fun SendRequestButton(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
    ) {
        Text(
            text = "Enviar solicitud",
            style = SonrisasTextStyle.Button.copy(fontWeight = FontWeight.Bold),
            color = Color.White,
        )
    }
}

@Composable
private fun HistoryRow(
    item: RequestHistoryItem,
    modifier: Modifier = Modifier,
) {
    val statusColor = when (item.status) {
        RequestStatus.Pending -> AccentYellow
        RequestStatus.Approved -> AccentGreen
        RequestStatus.Rejected -> AccentRed
    }
    val statusIcon = when (item.status) {
        RequestStatus.Pending -> Icons.Outlined.ErrorOutline
        RequestStatus.Approved -> Icons.Filled.Check
        RequestStatus.Rejected -> Icons.Outlined.Cancel
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = item.title,
            style = SonrisasTypography.bodyLarge,
            color = InkPrimary,
            modifier = Modifier.weight(1f),
        )
        Icon(
            imageVector = statusIcon,
            contentDescription = null,
            tint = statusColor,
            modifier = Modifier.size(20.dp),
        )
    }
}

private val sampleHistory = listOf(
    RequestHistoryItem("Permiso · 12 oct", RequestStatus.Pending),
    RequestHistoryItem("Cambio de turno · 25 sep", RequestStatus.Approved),
    RequestHistoryItem("Falta · 10 ago", RequestStatus.Rejected),
)

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun RequestsPermissionPreview() {
    SonrisasAsistenciasTheme {
        var selectedType by remember { mutableStateOf(RequestType.Permission) }
        var reason by remember {
            mutableStateOf("Tengo una cita médica de 9:00 a 11:00. Solicito permiso por esas dos horas.")
        }
        RequestsScreen(
            selectedType = selectedType,
            onTypeSelected = { selectedType = it },
            dateText = "12 de octubre de 2026",
            onDateClick = {},
            reason = reason,
            onReasonChange = { reason = it },
            history = sampleHistory,
            onSendClick = {},
        )
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun RequestsEmptyReasonPreview() {
    SonrisasAsistenciasTheme {
        var selectedType by remember { mutableStateOf(RequestType.Absence) }
        var reason by remember { mutableStateOf("") }
        RequestsScreen(
            selectedType = selectedType,
            onTypeSelected = { selectedType = it },
            dateText = "12 de octubre de 2026",
            onDateClick = {},
            reason = reason,
            onReasonChange = { reason = it },
            history = sampleHistory,
            onSendClick = {},
        )
    }
}
