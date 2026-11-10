package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import mx.tec.sonrisas.asistencia.ui.components.SonrisasHeroBadge
import mx.tec.sonrisas.asistencia.ui.components.SonrisasIconChip
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

//se añade acciones
@Composable
fun LocationRequestScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onAllowLocation: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgCream)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.xl),
        ) {
            Spacer(Modifier.height(Spacing.md))
            SonrisasTopBar(title = "Permisos de ubicación", onBack = onBack)

            Spacer(Modifier.height(Spacing.hero))
            LocationHero()

            Spacer(Modifier.height(Spacing.xxl))
            Text(
                text = "Solo usamos tu ubicación al momento de registrar tu entrada " +
                        "o salida, para confirmar que estás en la estancia. " +
                        "No rastreamos tu ubicación.",
                style = SonrisasTextStyle.Body,
                color = InkSoft,
                modifier = Modifier.padding(horizontal = Spacing.xs),
            )

            Spacer(Modifier.height(Spacing.xxl))
            PrivacyNoteCard(
                text = "Tu privacidad importa. El permiso se usa únicamente en ese momento.",
            )
            Spacer(Modifier.height(Spacing.xxl))
        }

        SonrisasPrimaryButton(
            text = "Permitir ubicación",
            onClick = onAllowLocation,
            leadingIcon = Icons.Outlined.LocationOn,
            modifier = Modifier.padding(start = Spacing.xl, end = Spacing.xl, top = Spacing.sm, bottom = Spacing.lg),
        )
    }
}

@Composable
private fun LocationHero() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SonrisasHeroBadge(icon = Icons.Outlined.LocationOn) //se llama al circulo
        Spacer(Modifier.height(Spacing.xxl))
        Text(
            text = "Solo al registrar\ntu entrada o salida",
            style = SonrisasTextStyle.Display,
            color = BrandPurpleDeepText,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun PrivacyNoteCard(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceWhite)
            .padding(horizontal = Spacing.lg, vertical = Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        SonrisasIconChip(icon = Icons.Outlined.Lock)
        Spacer(Modifier.width(Spacing.md))
        Text(
            text = text,
            style = SonrisasTextStyle.Caption,
            color = InkSoft,
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun LocationRequestScreenPreview() {
    SonrisasAsistenciasTheme {
        LocationRequestScreen()
    }
}
