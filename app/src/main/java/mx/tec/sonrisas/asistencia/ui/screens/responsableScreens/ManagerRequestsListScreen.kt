package mx.tec.sonrisas.asistencia.ui.screens.responsableScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import mx.tec.sonrisas.asistencia.ui.components.SonrisasBackStyle
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasStatusChip
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.StatusGreenInk
import mx.tec.sonrisas.asistencia.ui.theme.StatusGreenTint
import mx.tec.sonrisas.asistencia.ui.theme.StatusRedInk
import mx.tec.sonrisas.asistencia.ui.theme.StatusRedTint
import mx.tec.sonrisas.asistencia.ui.theme.StatusYellowInk
import mx.tec.sonrisas.asistencia.ui.theme.StatusYellowTint

// Lista de solicitudes del personal (Responsable). Al tocar una pendiente se abre RequestReviewScreen.

enum class ManagerRequestStatus(val label: String, val tint: Color, val ink: Color) {
    Pending("Pendiente", StatusYellowTint, StatusYellowInk),
    Approved("Aprobada", StatusGreenTint, StatusGreenInk),
    Rejected("Rechazada", StatusRedTint, StatusRedInk),
}

data class ManagerRequest(
    val id: Int,
    val personName: String,
    val roleLabel: String,
    val requestType: String,
    val whenLabel: String,
    val reason: String,
    val status: ManagerRequestStatus,
    val coverageWarning: String? = null,
)

@Composable
fun ManagerRequestsListScreen(
    requests: List<ManagerRequest>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onRequestClick: (Int) -> Unit = {},
) {
    val pending = requests.filter { it.status == ManagerRequestStatus.Pending }
    val resolved = requests.filter { it.status != ManagerRequestStatus.Pending }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgCream)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.xl),
    ) {
        Spacer(Modifier.height(Spacing.md))
        SonrisasTopBar(title = "Solicitudes", onBack = onBack, backStyle = SonrisasBackStyle.Plain)

        Spacer(Modifier.height(Spacing.lg))
        SectionTitle(text = "Pendientes (${pending.size})")
        Spacer(Modifier.height(Spacing.md))
        if (pending.isEmpty()) {
            Text(
                text = "No tienes solicitudes pendientes.",
                style = SonrisasTextStyle.Body,
                color = InkSoft,
                modifier = Modifier.padding(horizontal = Spacing.xs),
            )
        } else {
            SonrisasListCard(items = pending) { request ->
                RequestRow(request = request, onClick = { onRequestClick(request.id) })
            }
        }

        if (resolved.isNotEmpty()) {
            Spacer(Modifier.height(Spacing.xxl))
            SectionTitle(text = "Resueltas")
            Spacer(Modifier.height(Spacing.md))
            SonrisasListCard(items = resolved) { request ->
                RequestRow(request = request, onClick = null)
            }
        }
        Spacer(Modifier.height(Spacing.xxl))
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = SonrisasTextStyle.Title,
        color = InkPrimary,
        modifier = Modifier
            .padding(horizontal = Spacing.xs)
            .semantics { heading() },
    )
}

@Composable
private fun RequestRow(
    request: ManagerRequest,
    onClick: (() -> Unit)?,
) {
    val rowModifier = if (onClick != null) {
        Modifier.clickable(onClickLabel = "Revisar solicitud de ${request.personName}", onClick = onClick)
    } else {
        Modifier
    }
    Row(
        modifier = rowModifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.lg, vertical = Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = request.personName,
                style = SonrisasTextStyle.Body,
                color = InkPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "${request.requestType} · ${request.whenLabel}",
                style = SonrisasTextStyle.Caption,
                color = InkSoft,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(Modifier.width(Spacing.md))
        SonrisasStatusChip(
            text = request.status.label,
            tint = request.status.tint,
            ink = request.status.ink,
        )
    }
}

@Preview(name = "Solicitudes del personal", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun ManagerRequestsListScreenPreview() {
    SonrisasAsistenciasTheme {
        ManagerRequestsListScreen(
            requests = listOf(
                ManagerRequest(
                    1,
                    "Fernando Anckerman Ramírez",
                    "Maestro · Lactantes",
                    "Permiso por horas",
                    "Lun 12 oct · 9:00 a 11:00",
                    "Cita médica.",
                    ManagerRequestStatus.Pending,
                ),
                ManagerRequest(
                    2,
                    "Rosa Pérez García",
                    "Auxiliar · Lactantes",
                    "Falta justificada",
                    "Vie 2 oct",
                    "Consulta médica.",
                    ManagerRequestStatus.Approved,
                ),
            ),
            onBack = {},
        )
    }
}
