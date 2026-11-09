package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasCheckbox
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.RadiusMd
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

data class PrivacySection(
    val title: String,
    val body: String,
)

@Composable
fun PrivacyPolicyScreen(
    sections: List<PrivacySection>,
    accepted: Boolean,
    onAcceptedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {},
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgCream)
            .statusBarsPadding()
            .padding(horizontal = Spacing.xl),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(Modifier.height(Spacing.md))
            SonrisasTopBar(title = "Aviso de privacidad", onBack = onBackClick)
            Spacer(Modifier.height(Spacing.lg))
            PrivacyPolicyCard(
                sections = sections,
                scrollState = scrollState,
            )
            Spacer(Modifier.height(Spacing.md))
            Text(
                text = "Desliza para leer todo el aviso.",
                style = SonrisasTextStyle.Body,
                color = InkFaint,
                modifier = Modifier.alpha(if (scrollState.canScrollForward) 1f else 0f),
            )
            Spacer(Modifier.height(Spacing.md))
            AcceptanceRow(
                accepted = accepted,
                onAcceptedChange = onAcceptedChange,
            )
            Spacer(Modifier.height(Spacing.xl))
        }
        SonrisasPrimaryButton(
            text = "Aceptar y continuar",
            onClick = onContinueClick,
            enabled = accepted,
        )
        Spacer(Modifier.height(Spacing.md))
        Text(
            text = "Necesitas aceptarlo para crear tu cuenta.",
            style = SonrisasTextStyle.Body,
            color = InkFaint,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (accepted) 0f else 1f),
        )
        Spacer(Modifier.height(Spacing.xxl))
    }
}

@Composable
private fun AcceptanceRow(
    accepted: Boolean,
    onAcceptedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusMd)
            .toggleable(
                value = accepted,
                role = Role.Checkbox,
                onValueChange = onAcceptedChange,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        SonrisasCheckbox(checked = accepted)
        Text(
            text = "Leí y acepto el aviso de privacidad",
            style = SonrisasTextStyle.Body,
            color = InkPrimary,
        )
    }
}

@Composable
private fun PrivacyPolicyCard(
    sections: List<PrivacySection>,
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
            .clip(RadiusLg)
            .background(SurfaceWhite),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(Spacing.xl),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg),
        ) {
            sections.forEach { section ->
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                    Text(
                        text = section.title,
                        style = SonrisasTextStyle.Title,
                        color = InkPrimary,
                    )
                    Text(
                        text = section.body,
                        style = SonrisasTextStyle.Body,
                        color = InkSoft,
                    )
                }
            }
        }
        if (scrollState.canScrollForward) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, SurfaceWhite),
                        ),
                    ),
            )
        }
    }
}

private val samplePrivacySections = listOf(
    PrivacySection(
        title = "Quién usa tus datos",
        body = "Fundación Estancias Infantiles Sonrisas de Cristal A.C. es responsable de tus datos personales.",
    ),
    PrivacySection(
        title = "Qué datos usamos",
        body = "Tu nombre, correo, estancia, horario y registros de asistencia. Tu ubicación solo al momento de registrar entrada o salida.",
    ),
    PrivacySection(
        title = "Para qué",
        body = "Para llevar el control de asistencia, coordinar turnos y generar reportes de cobertura por estancia.",
    ),
    PrivacySection(
        title = "Con quién los compartimos",
        body = "Solo con el personal directivo de la fundación que requiere la información para organizar las estancias.",
    ),
    PrivacySection(
        title = "Tus derechos",
        body = "Puedes solicitar el acceso, la corrección o la eliminación de tus datos en cualquier momento.",
    ),
)

@Preview(showBackground = true)
@Composable
private fun PrivacyPolicyPendingPreview() {
    SonrisasAsistenciasTheme {
        var accepted by remember { mutableStateOf(false) }
        PrivacyPolicyScreen(
            sections = samplePrivacySections,
            accepted = accepted,
            onAcceptedChange = { accepted = it },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PrivacyPolicyAcceptedPreview() {
    SonrisasAsistenciasTheme {
        var accepted by remember { mutableStateOf(true) }
        PrivacyPolicyScreen(
            sections = samplePrivacySections,
            accepted = accepted,
            onAcceptedChange = { accepted = it },
        )
    }
}