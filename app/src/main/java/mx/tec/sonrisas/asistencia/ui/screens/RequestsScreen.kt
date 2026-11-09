package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.HourglassEmpty
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.AccentGreen
import mx.tec.sonrisas.asistencia.ui.theme.AccentRed
import mx.tec.sonrisas.asistencia.ui.theme.AccentYellow
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusMd
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

enum class RequestType(val label: String) {
    Absence("Falta"),
    Permission("Permiso"),
    ShiftChange("Cambio de turno"),
}


//se añade el estado para la solicitud con su respectivo diseño quitando los when que ya habia
enum class RequestStatus(
    val label: String,
    val icon: ImageVector,
    val accent: Color,
) {
    Pending("Pendiente", Icons.Outlined.HourglassEmpty, AccentYellow),
    Approved("Aprobada", Icons.Outlined.CheckCircle, AccentGreen),
    Rejected("Rechazada", Icons.Outlined.Cancel, AccentRed),
}

data class RequestHistoryItem(
    val title: String,
    val status: RequestStatus,
)

//cambie el nombre del archivo luego si se confunden las funciones
//se quito igual el Scaffold y se restructura como antes con columns y margenes chidos
//para los parametros de la solicitud se podran perder entonces sera necesaria el uso del rememberSaveable
@Composable
fun RequestsScreen(
    selectedType: RequestType,
    dateText: String,
    reason: String,
    history: List<RequestHistoryItem>,
    modifier: Modifier = Modifier,
    onTypeSelected: (RequestType) -> Unit = {},
    onDateClick: () -> Unit = {},
    onReasonChange: (String) -> Unit = {},
    onSendClick: () -> Unit = {},
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
        SonrisasTopBar(title = "Solicitudes")
        Spacer(Modifier.height(Spacing.sm))
        Text(
            text = "¿Qué necesitas avisar?",
            style = SonrisasTextStyle.Body,
            color = InkSoft,
        )
        Spacer(Modifier.height(Spacing.lg))
        RequestTypeSelector(
            selectedType = selectedType,
            onTypeSelected = onTypeSelected,
        )
        Spacer(Modifier.height(Spacing.xl))
        FieldLabel(text = "Fecha")
        Spacer(Modifier.height(Spacing.sm))
        DateField(text = dateText, onClick = onDateClick)
        Spacer(Modifier.height(Spacing.lg))
        FieldLabel(text = "Motivo")
        Spacer(Modifier.height(Spacing.sm))
        ReasonField(value = reason, onValueChange = onReasonChange)
        Spacer(Modifier.height(Spacing.xl))
        //deshabilitado para el boton
        SonrisasPrimaryButton(
            text = "Enviar solicitud",
            onClick = onSendClick,
            enabled = reason.isNotBlank(),
        )
        Spacer(Modifier.height(Spacing.xxl))
        Text(
            text = "Historial",
            style = SonrisasTextStyle.Title,
            color = InkPrimary,
        )
        Spacer(Modifier.height(Spacing.md))
        SonrisasListCard(items = history) { item ->
            HistoryRow(item = item)
        }
        Spacer(Modifier.height(Spacing.xxl))
    }
}

@Composable
private fun FieldLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = SonrisasTextStyle.Label,
        color = InkFaint,
        modifier = modifier,
    )
}

//se cambia el tipo de cada opcion de un box que se apretaba por un selectableGroup
//mas correcto para anunciar la seleccion a estaod 
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
            .padding(Spacing.xs)
            .selectableGroup(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RequestType.entries.forEach { type ->
            val isSelected = type == selectedType
            Box(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 48.dp)
                    .clip(RadiusPill)
                    .background(if (isSelected) BrandPurple else Color.Transparent)
                    .selectable(
                        selected = isSelected,
                        role = Role.Tab,
                        onClick = { onTypeSelected(type) },
                    )
                    .padding(horizontal = Spacing.sm),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = type.label,
                    style = SonrisasTextStyle.Button,
                    color = if (isSelected) SurfaceWhite else InkSoft,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

//roleboton para seleccion fecha y el icono en null para que no se lea dos veces
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
            .clickable(role = Role.Button, onClickLabel = "Seleccionar fecha", onClick = onClick)
            .padding(horizontal = Spacing.lg, vertical = Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = SonrisasTextStyle.Body,
            color = InkPrimary,
            modifier = Modifier.weight(1f),
        )
        Icon(
            imageVector = Icons.Outlined.CalendarToday,
            contentDescription = null,
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
        textStyle = SonrisasTextStyle.Body.copy(color = InkPrimary),
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
                        style = SonrisasTextStyle.Body,
                        color = InkFaint,
                    )
                }
                innerTextField()
            }
        },
    )
}

@Composable
private fun HistoryRow(
    item: RequestHistoryItem,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = item.title,
            style = SonrisasTextStyle.Body,
            color = InkPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
        Spacer(Modifier.width(Spacing.sm))
        Icon(
            imageVector = item.status.icon,
            contentDescription = item.status.label,
            tint = item.status.accent,
            modifier = Modifier.size(20.dp),
        )
    }
}

private val sampleHistory = listOf(
    RequestHistoryItem("Permiso · 12 oct", RequestStatus.Pending),
    RequestHistoryItem("Cambio de turno · 25 sep", RequestStatus.Approved),
    RequestHistoryItem("Falta · 10 ago", RequestStatus.Rejected),
)

//s¿los previews los deje con rememberSaveable para la idea de la navegacion mas adelante 
@Preview(name = "Solicitudes · permiso", showBackground = true, widthDp = 360, heightDp = 900)
@Composable
private fun RequestsPermissionPreview() {
    SonrisasAsistenciasTheme {
        var selectedType by rememberSaveable { mutableStateOf(RequestType.Permission) }
        var reason by rememberSaveable {
            mutableStateOf("Tengo una cita médica de 9:00 a 11:00. Solicito permiso por esas dos horas.")
        }
        RequestsScreen(
            selectedType = selectedType,
            dateText = "12 de octubre de 2026",
            reason = reason,
            history = sampleHistory,
            onTypeSelected = { selectedType = it },
            onReasonChange = { reason = it },
        )
    }
}

@Preview(name = "Solicitudes · sin motivo", showBackground = true, widthDp = 360, heightDp = 900)
@Composable
private fun RequestsEmptyReasonPreview() {
    SonrisasAsistenciasTheme {
        var selectedType by rememberSaveable { mutableStateOf(RequestType.Absence) }
        var reason by rememberSaveable { mutableStateOf("") }
        RequestsScreen(
            selectedType = selectedType,
            dateText = "12 de octubre de 2026",
            reason = reason,
            history = sampleHistory,
            onTypeSelected = { selectedType = it },
            onReasonChange = { reason = it },
        )
    }
}
