package mx.tec.sonrisas.asistencia.ui.screens.responsableScreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.LocationIssue
import mx.tec.sonrisas.asistencia.ui.components.SonrisasAvatar
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasLocationNotice
import mx.tec.sonrisas.asistencia.ui.components.SonrisasNoticeBanner
import mx.tec.sonrisas.asistencia.ui.components.SonrisasOutlinedButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
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
import mx.tec.sonrisas.asistencia.ui.theme.StatusYellowInk
import mx.tec.sonrisas.asistencia.ui.theme.StatusYellowTint
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceLilac
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

private data class RequestDetail(
    val label: String,
    val value: String,
)

@Composable
fun RequestReviewScreen(
    personName: String,
    roleLabel: String,
    requestType: String,
    whenLabel: String,
    reason: String,
    response: String,
    onResponseChange: (String) -> Unit,
    onApproveClick: () -> Unit,
    onRejectClick: () -> Unit,
    modifier: Modifier = Modifier,
    coverageWarning: String? = null,
    locationIssue: LocationIssue? = null,
    onBackClick: () -> Unit = {},
) {
    val details = listOf(
        RequestDetail("Tipo", requestType),
        RequestDetail("Cuándo", whenLabel),
        RequestDetail("Motivo", reason),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgCream)
            .statusBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.xl),
    ) {
        Spacer(Modifier.height(Spacing.md))
        SonrisasTopBar(
            title = "Solicitud",
            onBack = onBackClick,
        )
        if (locationIssue != null) {
            Spacer(Modifier.height(Spacing.lg))
            SonrisasLocationNotice(issue = locationIssue)
        }
        Spacer(Modifier.height(Spacing.lg))
        RequesterCard(name = personName, roleLabel = roleLabel)
        Spacer(Modifier.height(Spacing.lg))
        SonrisasListCard(items = details) { detail ->
            RequestDetailRow(detail = detail)
        }
        if (coverageWarning != null) {
            Spacer(Modifier.height(Spacing.lg))
            SonrisasNoticeBanner(
                icon = Icons.Outlined.ErrorOutline,
                message = coverageWarning,
            )
        }
        Spacer(Modifier.height(Spacing.lg))
        Text(
            text = "Respuesta (opcional)",
            style = SonrisasTypography.titleLarge,
            color = InkFaint,
        )
        Spacer(Modifier.height(Spacing.sm))
        ResponseField(
            value = response,
            onValueChange = onResponseChange,
            placeholder = "Escribe un mensaje para ${personName.substringBefore(' ')}",
        )
        Spacer(Modifier.height(Spacing.xl))
        SonrisasPrimaryButton(
            text = "Aprobar",
            onClick = onApproveClick,
        )
        Spacer(Modifier.height(Spacing.md))
        SonrisasOutlinedButton(
            text = "Rechazar",
            onClick = onRejectClick,
            destructive = true,
        )
        Spacer(Modifier.height(Spacing.xxl))
    }
}

@Composable
private fun RequesterCard(
    name: String,
    roleLabel: String,
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
                text = roleLabel,
                style = SonrisasTextStyle.Body,
                color = InkSoft,
            )
            Spacer(Modifier.height(Spacing.sm))
            Text(
                text = "Pendiente",
                style = SonrisasTypography.bodySmall,
                color = StatusYellowInk,
                maxLines = 1,
                modifier = Modifier
                    .clip(RadiusPill)
                    .background(StatusYellowTint)
                    .padding(horizontal = Spacing.md, vertical = Spacing.xs),
            )
        }
    }
}

@Composable
private fun RequestDetailRow(
    detail: RequestDetail,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
    ) {
        Text(
            text = detail.label,
            style = SonrisasTypography.titleLarge,
            color = InkFaint,
        )
        Text(
            text = detail.value,
            style = SonrisasTypography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            color = InkPrimary,
        )
    }
}

@Composable
private fun ResponseField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 96.dp)
            .clip(RadiusMd)
            .background(SurfaceWhite)
            .border(BorderStroke(1.dp, LineColor), RadiusMd)
            .padding(Spacing.lg),
        contentAlignment = Alignment.TopStart,
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = SonrisasTypography.bodyLarge.copy(color = InkPrimary),
            cursorBrush = SolidColor(BrandPurple),
            modifier = Modifier.fillMaxWidth(),
        )
        if (value.isEmpty()) {
            Text(
                text = placeholder,
                style = SonrisasTypography.bodyLarge,
                color = InkFaint,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RequestReviewScreenPreview() {
    SonrisasAsistenciasTheme {
        var response by remember { mutableStateOf("") }

        RequestReviewScreen(
            personName = "Fernando Anckerman Ramírez",
            roleLabel = "Maestro · Lactantes",
            requestType = "Permiso por horas",
            whenLabel = "Lunes 12 oct · 9:00 a 11:00",
            reason = "Tengo una cita médica de 9:00 a 11:00.",
            response = response,
            onResponseChange = { response = it },
            onApproveClick = {},
            onRejectClick = {},
            coverageWarning = "Lactantes queda con 2 de 3 personas de 9:00 a 11:00.",
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RequestReviewScreenNoWarningPreview() {
    SonrisasAsistenciasTheme {
        RequestReviewScreen(
            personName = "Fernando Anckerman Ramírez",
            roleLabel = "Maestro · Lactantes",
            requestType = "Permiso por horas",
            whenLabel = "Lunes 12 oct · 9:00 a 11:00",
            reason = "Tengo una cita médica de 9:00 a 11:00.",
            response = "",
            onResponseChange = {},
            onApproveClick = {},
            onRejectClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RequestReviewScreenLocationPreview() {
    SonrisasAsistenciasTheme {
        RequestReviewScreen(
            personName = "Fernando Anckerman Ramírez",
            roleLabel = "Maestro · Lactantes",
            requestType = "Permiso por horas",
            whenLabel = "Lunes 12 oct · 9:00 a 11:00",
            reason = "Tengo una cita médica de 9:00 a 11:00.",
            response = "",
            onResponseChange = {},
            onApproveClick = {},
            onRejectClick = {},
            coverageWarning = "Lactantes queda con 2 de 3 personas de 9:00 a 11:00.",
            locationIssue = LocationIssue.Suspicious,
        )
    }
}
