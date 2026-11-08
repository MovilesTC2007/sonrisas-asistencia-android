package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GpsFixed
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.tec.sonrisas.asistencia.ui.theme.AccentYellow
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTypography
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

private val reviewAccentTextColor = lerp(AccentYellow, Color.Black, 0.45f)

@Composable
fun LocationUnderReviewScreen(
    recordedTime: String,
    onBackToHomeClick: () -> Unit,
    modifier: Modifier = Modifier,
    isClockIn: Boolean = true,
) {
    val actionLabel = if (isClockIn) "entrada" else "salida"

    Scaffold(
        modifier = modifier,
        containerColor = BgCream,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = Spacing.xxl),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
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
                        .background(AccentYellow.copy(alpha = 0.25f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.GpsFixed,
                        contentDescription = null,
                        tint = reviewAccentTextColor,
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
                    style = SonrisasTypography.displayLarge.copy(
                        fontSize = 48.sp,
                        lineHeight = 58.sp,
                    ),
                    color = InkPrimary,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(Spacing.xl))
                ReviewStatusChip(text = "En revisión")

                Spacer(modifier = Modifier.height(Spacing.xl))
                Text(
                    text = "La ubicación de tu teléfono no parece real. Tu registro se guardó y tu responsable lo va a revisar.",
                    style = SonrisasTypography.bodyLarge,
                    color = InkPrimary,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(Spacing.xl))
                Text(
                    text = "Si usas una app que cambia tu ubicación, desactívala antes de registrar.",
                    style = SonrisasTypography.bodyMedium,
                    color = InkPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = Spacing.md),
                )

                Spacer(modifier = Modifier.height(Spacing.xxl))
            }
            BackToHomeButton(onClick = onBackToHomeClick)

            Spacer(modifier = Modifier.height(Spacing.xxl))
        }
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
            .background(AccentYellow.copy(alpha = 0.25f))
            .padding(horizontal = Spacing.md, vertical = Spacing.xs),
    ) {
        Text(
            text = text,
            style = SonrisasTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = reviewAccentTextColor,
        )
    }
}

@Composable
private fun BackToHomeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp,
                shape = RadiusPill,
                ambientColor = BrandPurple.copy(alpha = 0.35f),
                spotColor = BrandPurple.copy(alpha = 0.45f),
            )
            .clip(RadiusPill)
            .background(BrandPurple)
            .clickable(onClick = onClick)
            .padding(vertical = Spacing.xl),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Volver al inicio",
            style = SonrisasTypography.titleLarge,
            color = Color.White,
        )
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun LocationUnderReviewClockInPreview() {
    SonrisasAsistenciasTheme {
        LocationUnderReviewScreen(
            recordedTime = "7:51",
            onBackToHomeClick = {},
        )
    }
}

@Preview(showBackground = true, heightDp = 900)
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
