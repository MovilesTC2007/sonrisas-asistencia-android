package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
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
import mx.tec.sonrisas.asistencia.ui.components.AttendanceStatus
import mx.tec.sonrisas.asistencia.ui.components.SonrisasHeroBadge
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

//cambio para uso de componentes

//los botones se cambiaron para permitir acciones y navegacion
@Composable
fun AttendanceConfirmationScreen(
    modifier: Modifier = Modifier,
    status: AttendanceStatus = AttendanceStatus.OnTime,
    time: String = "7:55",
    date: String = "Jueves 8 de octubre de 2026",
    estancia: String = "Estancia Arcoíris · Lactantes",
    shift: String = "Turno de hoy: 8:00 – 16:00",
    onBackToHome: () -> Unit = {},
) {
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
            SonrisasTopBar(title = "Registro guardado")

            Spacer(Modifier.height(Spacing.hero))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SonrisasHeroBadge(icon = Icons.Rounded.Check)

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
                    style = SonrisasTextStyle.Hero,
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
            onClick = onBackToHome,
            modifier = Modifier.padding(
                start = Spacing.xl,
                end = Spacing.xl,
                top = Spacing.sm,
                bottom = Spacing.lg,
            ),
        )
        //se quita la barra de aqui
    }
}

@Composable
private fun StatusIndicator(status: AttendanceStatus) {
    //por ser un enum compartido no hay necesidad de usar when locales
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        Icon(
            imageVector = status.icon,
            contentDescription = null,
            tint = status.accent,
            modifier = Modifier.size(16.dp),
        )
        Text(
            text = status.label,
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

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun AttendanceConfirmationOnTimePreview() {
    SonrisasAsistenciasTheme {
        AttendanceConfirmationScreen()
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun AttendanceConfirmationMinorDelayPreview() {
    SonrisasAsistenciasTheme {
        AttendanceConfirmationScreen(
            status = AttendanceStatus.MinorDelay,
            time = "8:08",
        )
    }
}
