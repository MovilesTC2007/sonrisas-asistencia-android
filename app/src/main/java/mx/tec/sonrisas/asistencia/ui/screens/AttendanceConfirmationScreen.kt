package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.rounded.Check
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
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasBottomBar
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTab
import mx.tec.sonrisas.asistencia.ui.theme.AccentGreen
import mx.tec.sonrisas.asistencia.ui.theme.AccentYellow
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

enum class AttendanceStatus { OnTime, MinorDelay }

private val TimeDisplayStyle = SonrisasTextStyle.Display.copy(
    fontSize = 48.sp,
    lineHeight = 56.sp,
    letterSpacing = (-0.48).sp,
)

@Composable
fun AttendanceConfirmationScreen(
    modifier: Modifier = Modifier,
    status: AttendanceStatus = AttendanceStatus.OnTime,
    time: String = "7:55",
    date: String = "Jueves 8 de octubre de 2026",
    estancia: String = "Estancia Arcoíris · Lactantes",
    shift: String = "Turno de hoy: 8:00 – 16:00",
) {
    var selectedTab by rememberSaveable { mutableStateOf(SonrisasTab.Inicio) }

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
            ConfirmationHeader()

            Spacer(Modifier.height(64.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SuccessBadge()

                Spacer(Modifier.height(Spacing.xxl))
                Text(
                    text = "¡Entrada registrada!",
                    style = SonrisasTextStyle.Display,
                    color = BrandPurpleDeepText,
                    textAlign = TextAlign.Center,
                )

                Spacer(Modifier.height(Spacing.md))
                Text(
                    text = time,
                    style = TimeDisplayStyle,
                    color = BrandPurpleDeepText,
                    textAlign = TextAlign.Center,
                )

                Spacer(Modifier.height(Spacing.lg))
                StatusIndicator(status = status)

                Spacer(Modifier.height(Spacing.xl))
                Text(
                    text = date,
                    style = SonrisasTextStyle.Body,
                    color = InkSoft,
                    textAlign = TextAlign.Center,
                )

                Spacer(Modifier.height(Spacing.lg))
                ShiftInfoCard(estancia = estancia, shift = shift)
            }
            Spacer(Modifier.height(Spacing.xxl))
        }

        SonrisasPrimaryButton(
            text = "Volver al inicio",
            onClick = {},
            modifier = Modifier.padding(
                start = Spacing.xl,
                end = Spacing.xl,
                top = Spacing.sm,
                bottom = Spacing.lg,
            ),
        )

        SonrisasBottomBar(
            selected = selectedTab,
            onTabClick = { selectedTab = it },
        )
    }
}

@Composable
private fun ConfirmationHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = "Registro guardado",
            style = SonrisasTextStyle.Display,
            color = InkPrimary,
            modifier = Modifier.semantics { heading() },
        )
    }
}

@Composable
private fun SuccessBadge() {
    Box(
        modifier = Modifier
            .size(88.dp)
            .clip(CircleShape)
            .background(BrandPurpleLight),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Rounded.Check,
            contentDescription = null,
            tint = BrandPurple,
            modifier = Modifier.size(44.dp),
        )
    }
}

@Composable
private fun StatusIndicator(status: AttendanceStatus) {
    val (icon, tint, label) = when (status) {
        AttendanceStatus.OnTime -> Triple(Icons.Outlined.CheckCircle, AccentGreen, "A tiempo")
        AttendanceStatus.MinorDelay -> Triple(Icons.Outlined.Schedule, AccentYellow, "Retardo menor")
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(16.dp),
        )
        Text(
            text = label,
            style = SonrisasTextStyle.Caption.copy(fontWeight = FontWeight.SemiBold),
            color = InkPrimary,
        )
    }
}

@Composable
private fun ShiftInfoCard(estancia: String, shift: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceWhite)
            .padding(Spacing.lg),
    ) {
        Text(
            text = estancia,
            style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.SemiBold),
            color = InkPrimary,
        )
        Spacer(Modifier.height(Spacing.sm))
        Text(
            text = shift,
            style = SonrisasTextStyle.Body,
            color = InkSoft,
        )
    }
}

@Preview(name = "A tiempo (p. 7)", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun AttendanceConfirmationOnTimePreview() {
    SonrisasAsistenciasTheme {
        AttendanceConfirmationScreen()
    }
}

@Preview(name = "Retardo menor (p. 8)", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun AttendanceConfirmationMinorDelayPreview() {
    SonrisasAsistenciasTheme {
        AttendanceConfirmationScreen(
            status = AttendanceStatus.MinorDelay,
            time = "8:08",
        )
    }
}