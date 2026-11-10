package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GpsFixed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTypography
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.StatusYellowInk
import mx.tec.sonrisas.asistencia.ui.theme.StatusYellowTint

@Composable
fun LocationUnderReviewScreen(
    recordedTime: String,
    onBackToHomeClick: () -> Unit,
    modifier: Modifier = Modifier,
    isClockIn: Boolean = true,
) {
    val actionLabel = if (isClockIn) "entrada" else "salida"

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgCream)
            .systemBarsPadding()
            .padding(horizontal = Spacing.xl),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(Spacing.xxl))
            Text(
                text = "Registro guardado",
                style = SonrisasTypography.displayLarge,
                color = InkPrimary,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(Spacing.xxl))
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(StatusYellowTint),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Outlined.GpsFixed,
                    contentDescription = null,
                    tint = StatusYellowInk,
                    modifier = Modifier.size(64.dp),
                )
            }
            Spacer(modifier = Modifier.height(Spacing.xl))
            Text(
                text = "Tu $actionLabel\nquedó en revisión",
                style = SonrisasTypography.headlineMedium,
                color = InkPrimary,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(Spacing.lg))
            Text(
                text = recordedTime,
                style = SonrisasTextStyle.Hero,
                color = InkPrimary,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(Spacing.xl))
            ReviewStatusChip(text = "En revisión")
            Spacer(modifier = Modifier.height(Spacing.xl))
            Text(
                text = "Ubicación sospechosa. Registro guardado y enviado a revisión de tu responsable.",
                style = SonrisasTypography.bodyLarge,
                color = InkPrimary,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(Spacing.xl))
            Text(
                text = "Si usas una app que cambia tu ubicación, desactívala antes de registrar.",
                style = SonrisasTextStyle.Body,
                color = InkPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = Spacing.md),
            )
            Spacer(modifier = Modifier.height(Spacing.xxl))
        }
        SonrisasPrimaryButton(
            text = "Volver al inicio",
            onClick = onBackToHomeClick,
        )
        Spacer(modifier = Modifier.height(Spacing.xxl))
    }
}

@Composable
private fun ReviewStatusChip(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RadiusPill)
            .background(StatusYellowTint)
            .padding(horizontal = Spacing.md, vertical = Spacing.xs),
    ) {
        Text(
            text = text,
            style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.SemiBold),
            color = StatusYellowInk,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LocationUnderReviewClockInPreview() {
    SonrisasAsistenciasTheme {
        LocationUnderReviewScreen(
            recordedTime = "7:51",
            onBackToHomeClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LocationUnderReviewClockOutPreview() {
    SonrisasAsistenciasTheme {
        LocationUnderReviewScreen(
            recordedTime = "16:03",
            onBackToHomeClick = {},
            isClockIn = false,
        )
    }
}
