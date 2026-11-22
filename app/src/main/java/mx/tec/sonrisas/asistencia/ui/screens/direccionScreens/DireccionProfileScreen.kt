package mx.tec.sonrisas.asistencia.ui.screens.direccionScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasAvatar
import mx.tec.sonrisas.asistencia.ui.components.SonrisasBackStyle
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasOutlinedButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeep
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

data class DireccionProfile(
    val fullName: String,
    val email: String,
    val scope: String,
    val trustedDevices: Int,
)

private data class ProfileInfo(val label: String, val value: String)

private data class DireccionProfileAction(
    val icon: ImageVector,
    val label: String,
    val trailingText: String? = null,
    val onClick: () -> Unit,
)

@Composable
fun DireccionProfileScreen(
    profile: DireccionProfile,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onChangePasswordClick: () -> Unit = {},
    onTrustedDevicesClick: () -> Unit = {},
    onPrivacyNoticeClick: () -> Unit = {},
    onTransferClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
) {
    val info = listOf(
        ProfileInfo("Correo", profile.email),
        ProfileInfo("Alcance", profile.scope),
        ProfileInfo("Permisos", "Solo lectura"),
    )
    val actions = listOf(
        DireccionProfileAction(Icons.Outlined.Lock, "Cambiar contraseña", onClick = onChangePasswordClick),
        DireccionProfileAction(
            icon = Icons.Outlined.PhoneAndroid,
            label = "Teléfonos recordados",
            trailingText = "${profile.trustedDevices}",
            onClick = onTrustedDevicesClick,
        ),
        DireccionProfileAction(Icons.Outlined.Description, "Aviso de privacidad", onClick = onPrivacyNoticeClick),
        DireccionProfileAction(Icons.Outlined.SwapHoriz, "Transferir cuenta", onClick = onTransferClick),
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
        SonrisasTopBar(title = "Perfil", onBack = onBack, backStyle = SonrisasBackStyle.Plain)
        Spacer(Modifier.height(Spacing.lg))
        ProfileHeader(fullName = profile.fullName)
        Spacer(Modifier.height(Spacing.lg))
        SonrisasListCard(items = info) { item -> InfoRow(item = item) }
        Spacer(Modifier.height(Spacing.lg))
        SonrisasListCard(items = actions) { action -> ActionRow(action = action) }
        Spacer(Modifier.height(Spacing.xl))
        SonrisasOutlinedButton(
            text = "Cerrar sesión",
            onClick = onLogoutClick,
            leadingIcon = Icons.AutoMirrored.Outlined.Logout,
            destructive = true,
        )
        Spacer(Modifier.height(Spacing.xxl))
    }
}

@Composable
private fun ProfileHeader(fullName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(BrandPurpleDeep)
            .padding(Spacing.xl),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.lg),
    ) {
        SonrisasAvatar(
            name = fullName,
            size = 68.dp,
            containerColor = BrandPurple,
            contentColor = SurfaceWhite,
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = fullName,
                style = SonrisasTextStyle.Title,
                color = SurfaceWhite,
            )
            Spacer(Modifier.height(Spacing.xs))
            Text(
                text = "Dirección · Sonrisas de Cristal",
                style = SonrisasTextStyle.Body,
                color = BrandPurpleLight,
            )
        }
    }
}

@Composable
private fun InfoRow(item: ProfileInfo) {
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

@Composable
private fun ActionRow(action: DireccionProfileAction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = action.onClick)
            .heightIn(min = 56.dp)
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.md),
    ) {
        Icon(
            imageVector = action.icon,
            contentDescription = null,
            tint = BrandPurple,
            modifier = Modifier.size(22.dp),
        )
        Text(
            text = action.label,
            style = SonrisasTextStyle.Body,
            color = InkPrimary,
            modifier = Modifier.weight(1f),
        )
        if (action.trailingText != null) {
            Text(
                text = action.trailingText,
                style = SonrisasTextStyle.Body,
                color = InkSoft,
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = null,
            tint = InkFaint,
            modifier = Modifier.size(24.dp),
        )
    }
}

@Preview(name = "Perfil de Dirección", showBackground = true, widthDp = 360, heightDp = 900)
@Composable
private fun DireccionProfilePreview() {
    SonrisasAsistenciasTheme {
        DireccionProfileScreen(
            profile = DireccionProfile(
                fullName = "Patricia Gómez Ríos",
                email = "direccion@sonrisasdecristal.org",
                scope = "Todas las estancias (62)",
                trustedDevices = 2,
            ),
            onBack = {},
        )
    }
}
