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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

/**
 * Permisos de ubicación — pantalla solo visual (mockup p. 4).
 * Sin lógica: los onClick están vacíos a propósito.
 */
@Composable
fun LocationRequestScreen(modifier: Modifier = Modifier) {
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
                .padding(horizontal = 20.dp),
        ) {
            Spacer(Modifier.height(12.dp))
            LocationHeader()

            // Aire generoso antes del bloque central, como en el mockup.
            Spacer(Modifier.height(88.dp))
            LocationHero()

            Spacer(Modifier.height(24.dp))
            Text(
                text = "Solo usamos tu ubicación al momento de registrar tu entrada " +
                        "o salida, para confirmar que estás en la estancia. " +
                        "No rastreamos tu ubicación.",
                style = SonrisasTextStyle.Body,
                color = InkPrimary,
                modifier = Modifier.padding(horizontal = 4.dp),
            )

            Spacer(Modifier.height(24.dp))
            PrivacyNoteCard(
                text = "Tu privacidad importa. El permiso se usa únicamente en ese momento.",
            )
            Spacer(Modifier.height(24.dp))
        }

        SonrisasPrimaryButton(
            text = "Permitir ubicación",
            onClick = {},
            leadingIcon = Icons.Outlined.LocationOn,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 16.dp),
        )
    }
}

/** Encabezado: chip de regreso + nombre de pantalla (mismo patrón que Activar cuenta). */
@Composable
private fun LocationHeader() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        // Área táctil de 48 dp; el chip visible mide 40 dp.
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
        Spacer(Modifier.size(8.dp))
        Text(
            text = "Permisos de ubicación",
            style = SonrisasTextStyle.Display,
            color = InkPrimary,
            modifier = Modifier.semantics { heading() },
        )
    }
}

/** Bloque central: círculo con pin + titular en morado. */
@Composable
private fun LocationHero() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
                .background(BrandPurpleLight),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = null, // decorativo; el titular ya da el contexto
                tint = BrandPurple,
                modifier = Modifier.size(36.dp),
            )
        }
        Spacer(Modifier.height(24.dp))
        Text(
            text = "Solo al registrar\ntu entrada o salida",
            style = SonrisasTextStyle.Display,
            color = BrandPurpleDeepText,
            textAlign = TextAlign.Center,
        )
    }
}

/** Nota de privacidad: tarjeta blanca con chip de icono, igual que los campos de formulario. */
@Composable
private fun PrivacyNoteCard(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceWhite)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(RoundedCornerShape(9.dp))
                .background(BrandPurpleLight),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.Lock,
                contentDescription = null,
                tint = BrandPurple,
                modifier = Modifier.size(15.dp),
            )
        }
        Spacer(Modifier.size(12.dp))
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