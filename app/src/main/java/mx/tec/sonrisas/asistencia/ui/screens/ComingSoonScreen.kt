package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import mx.tec.sonrisas.asistencia.ui.components.SonrisasHeroBadge
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

//pantalla por lo menos provisional
//faltan algunas pantallas por agregar en el navbar entonces le delegamos esta en sonrisas app mientras tanto
//IA SLOP EN SU MAYORIA ESTO SE VA A BORRAR
@Composable
fun ComingSoonScreen(
    title: String,
    icon: ImageVector,
    message: String,
    modifier: Modifier = Modifier,
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
        SonrisasTopBar(title = title)
        Spacer(Modifier.height(Spacing.hero))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SonrisasHeroBadge(icon = icon)
            Spacer(Modifier.height(Spacing.xxl))
            Text(
                text = "Próximamente",
                style = SonrisasTextStyle.Display,
                color = BrandPurpleDeepText,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(Spacing.md))
            Text(
                text = message,
                style = SonrisasTextStyle.Body,
                color = InkSoft,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(name = "Próximamente · Perfil", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun ComingSoonScreenPreview() {
    SonrisasAsistenciasTheme {
        ComingSoonScreen(
            title = "Perfil",
            icon = Icons.Outlined.Person,
            message = "Aquí verás tus datos personales y podrás cerrar sesión.",
        )
    }
}
