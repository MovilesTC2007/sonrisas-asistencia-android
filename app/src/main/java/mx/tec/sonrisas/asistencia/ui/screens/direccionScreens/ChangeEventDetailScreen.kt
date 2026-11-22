package mx.tec.sonrisas.asistencia.ui.screens.direccionScreens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasBackStyle
import mx.tec.sonrisas.asistencia.ui.components.SonrisasInfoCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.RadiusSm
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

private data class DetailItem(val label: String, val value: String)

@Composable
fun ChangeEventDetailScreen(
    event: ChangeEvent,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val change = listOfNotNull(
        event.oldValue?.let { DetailItem("Antes", it) },
        event.newValue?.let { DetailItem("Después", it) },
    )
    val info = listOfNotNull(
        DetailItem("Cuándo", event.time),
        DetailItem("Quién", event.actor),
        event.estancia?.let { DetailItem("Estancia", it) },
        event.device?.let { DetailItem("Dispositivo", it) },
        DetailItem("Motivo", event.reason ?: "Sin motivo registrado"),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgCream)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.xl),
    ) {
        Spacer(Modifier.height(Spacing.md))
        SonrisasTopBar(title = "Detalle del cambio", onBack = onBack, backStyle = SonrisasBackStyle.Plain)
        Spacer(Modifier.height(Spacing.lg))
        EventHeader(event = event)
        if (change.isNotEmpty()) {
            Spacer(Modifier.height(Spacing.xl))
            SectionLabel(text = "Qué cambió")
            Spacer(Modifier.height(Spacing.sm))
            SonrisasListCard(items = change) { item -> DetailRow(item = item) }
        }
        Spacer(Modifier.height(Spacing.xl))
        SectionLabel(text = "Detalles")
        Spacer(Modifier.height(Spacing.sm))
        SonrisasListCard(items = info) { item -> DetailRow(item = item) }
        Spacer(Modifier.height(Spacing.xl))
        SonrisasInfoCard(
            message = "Este registro es de solo lectura. Nadie puede modificarlo.",
            icon = Icons.Outlined.Lock,
        )
        Spacer(Modifier.height(Spacing.xxl))
    }
}

@Composable
private fun EventHeader(event: ChangeEvent) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceWhite)
            .padding(Spacing.lg),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RadiusSm)
                .background(BrandPurpleLight),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = event.kind.icon,
                contentDescription = null,
                tint = BrandPurple,
                modifier = Modifier.size(22.dp),
            )
        }
        Spacer(Modifier.width(Spacing.lg))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = event.description,
                style = SonrisasTextStyle.Title,
                color = InkPrimary,
            )
            Spacer(Modifier.height(Spacing.xs))
            Text(
                text = event.kind.label,
                style = SonrisasTextStyle.Caption,
                color = InkSoft,
            )
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.SemiBold),
        color = InkPrimary,
        modifier = Modifier
            .padding(horizontal = Spacing.xs)
            .semantics { heading() },
    )
}

@Composable
private fun DetailRow(item: DetailItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
    ) {
        Text(
            text = item.label,
            style = SonrisasTextStyle.Caption,
            color = InkFaint,
        )
        Text(
            text = item.value,
            style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.SemiBold),
            color = InkPrimary,
        )
    }
}

@Preview(name = "Detalle del cambio", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun ChangeEventDetailPreview() {
    SonrisasAsistenciasTheme {
        ChangeEventDetailScreen(
            event = ChangeEvent(
                id = 1,
                kind = ChangeKind.Correction,
                description = "Elena Ruiz corrigió la salida de Carmen López",
                estancia = "Arcoíris",
                time = "hoy 9:14",
                daysAgo = 0,
                actor = "Elena Ruiz",
                oldValue = "Salida: sin registro",
                newValue = "Salida: 15:00",
                reason = "Olvidó registrar la salida",
            ),
            onBack = {},
        )
    }
}

@Preview(name = "Detalle · acceso", showBackground = true, widthDp = 360, heightDp = 700)
@Composable
private fun ChangeEventAccessPreview() {
    SonrisasAsistenciasTheme {
        ChangeEventDetailScreen(
            event = ChangeEvent(
                id = 8,
                kind = ChangeKind.Access,
                description = "Patricia Gómez inició sesión",
                estancia = null,
                time = "hoy 7:30",
                daysAgo = 0,
                actor = "Patricia Gómez",
                device = "Pixel 7 · Monterrey, N.L.",
            ),
            onBack = {},
        )
    }
}
