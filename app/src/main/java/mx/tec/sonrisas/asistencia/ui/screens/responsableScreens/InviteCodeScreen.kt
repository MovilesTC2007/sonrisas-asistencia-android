package mx.tec.sonrisas.asistencia.ui.screens.responsableScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasBackStyle
import mx.tec.sonrisas.asistencia.ui.components.SonrisasButtonSize
import mx.tec.sonrisas.asistencia.ui.components.SonrisasInviteCodeCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasStatusChip
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTintedButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.StatusGreenInk
import mx.tec.sonrisas.asistencia.ui.theme.StatusGreenTint
import mx.tec.sonrisas.asistencia.ui.theme.StatusYellowInk
import mx.tec.sonrisas.asistencia.ui.theme.StatusYellowTint

// Código de invitación (Generar código de invitación, RF-23 · CU02 paso 1).

private enum class InviteStatus(val label: String, val tint: Color, val ink: Color) {
    Pending("Pendiente", StatusYellowTint, StatusYellowInk),
    Used("Usado", StatusGreenTint, StatusGreenInk),
}

private data class InviteEntry(val name: String, val detail: String, val status: InviteStatus)

private val SampleInvites = listOf(
    InviteEntry("Patricia Gómez Ríos", "Creado hoy", InviteStatus.Pending),
    InviteEntry("Julia Morales Soto", "Usado el 3 oct", InviteStatus.Used),
)

@Composable
fun InviteCodeScreen(
    modifier: Modifier = Modifier,
    recipientName: String = "Patricia Gómez Ríos",
    recipientDetail: String = "Asistente · Maternales",
    code: String = "SC4K 7R2T",
    validity: String = "Un solo uso · vence el 16 de noviembre",
    onBack: () -> Unit = {},
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
            SonrisasTopBar(
                title = "Código de invitación",
                onBack = onBack,
                backStyle = SonrisasBackStyle.Plain,
            )

            Spacer(Modifier.height(Spacing.lg))
            SonrisasInviteCodeCard(
                name = recipientName,
                detail = recipientDetail,
                code = code,
                validity = validity,
            )

            Spacer(Modifier.height(Spacing.xl))
            Text(
                text = "Compártele este código. Solo con él puede crear su cuenta en la app.",
                style = SonrisasTextStyle.Body.copy(lineHeight = 26.sp),
                color = InkSoft,
            )

            Spacer(Modifier.height(Spacing.xl))
            SonrisasPrimaryButton(
                text = "Copiar código",
                onClick = {},
                leadingIcon = Icons.Outlined.ContentCopy,
                size = SonrisasButtonSize.Large,
                elevated = true,
            )
            Spacer(Modifier.height(Spacing.lg))
            SonrisasTintedButton(
                text = "Compartir",
                onClick = {},
                leadingIcon = Icons.Outlined.Share,
                size = SonrisasButtonSize.Large,
            )

            Spacer(Modifier.height(Spacing.xxl + Spacing.sm))
            Text(
                text = "Códigos pendientes",
                style = SonrisasTextStyle.Title,
                color = InkPrimary,
                modifier = Modifier
                    .padding(horizontal = Spacing.xs)
                    .semantics { heading() },
            )

            Spacer(Modifier.height(Spacing.md))
            SonrisasListCard(items = SampleInvites) { invite ->
                InviteRow(invite = invite)
            }
            Spacer(Modifier.height(Spacing.xl))
        }
    }
}

@Composable
private fun InviteRow(invite: InviteEntry) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.lg, vertical = Spacing.xl)
            .semantics(mergeDescendants = true) {},
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = invite.name,
                style = SonrisasTextStyle.Body.copy(fontSize = 16.sp, lineHeight = 22.sp),
                color = InkPrimary,
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = invite.detail,
                style = SonrisasTextStyle.Caption,
                color = InkSoft,
            )
        }
        Spacer(Modifier.width(Spacing.md))
        SonrisasStatusChip(
            text = invite.status.label,
            tint = invite.status.tint,
            ink = invite.status.ink,
        )
    }
}

@Preview(name = "Código de invitación", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun InviteCodeScreenPreview() {
    SonrisasAsistenciasTheme {
        InviteCodeScreen()
    }
}
