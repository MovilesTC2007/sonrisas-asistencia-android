package mx.tec.sonrisas.asistencia.ui.screens.responsableScreens

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
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasAvatar
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasOutlinedButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeep
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

// Perfil del Responsable (pestaña Perfil).

data class ResponsableProfile(
    val fullName: String,
    val email: String,
    val estancia: String,
    val staffCount: Int,
)

private data class ProfileInfoRow(val label: String, val value: String)

private data class ResponsableProfileAction(
    val icon: ImageVector,
    val label: String,
    val trailingText: String? = null,
    val onClick: (() -> Unit)? = null,
)

@Composable
fun ResponsableProfileScreen(
    profile: ResponsableProfile,
    modifier: Modifier = Modifier,
    onChangePasswordClick: () -> Unit = {},
    onPrivacyNoticeClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
) {
    val info = listOf(
        ProfileInfoRow("Correo", profile.email),
        ProfileInfoRow("Estancia a cargo", profile.estancia),
        ProfileInfoRow("Personal a cargo", "${profile.staffCount} personas"),
    )
    val actions = listOf(
        ResponsableProfileAction(Icons.Outlined.Lock, "Cambiar contraseña", onClick = onChangePasswordClick),
        ResponsableProfileAction(Icons.Outlined.LocationOn, "Ubicación", trailingText = "Solo al registrar"),
        ResponsableProfileAction(Icons.Outlined.Description, "Aviso de privacidad", onClick = onPrivacyNoticeClick),
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
        SonrisasTopBar(title = "Perfil")
        Spacer(Modifier.height(Spacing.lg))
        ProfileHeader(fullName = profile.fullName, estancia = profile.estancia)
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
private fun ProfileHeader(
    fullName: String,
    estancia: String,
) {
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
            contentColor = Color.White,
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(text = fullName, style = SonrisasTextStyle.Title, color = Color.White)
            Spacer(Modifier.height(Spacing.xs))
            Text(
                text = "Responsable · $estancia",
                style = SonrisasTextStyle.Body,
                color = BrandPurpleLight,
            )
        }
    }
}

@Composable
private fun InfoRow(item: ProfileInfoRow) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
    ) {
        Text(text = item.label, style = SonrisasTextStyle.Caption, color = InkFaint)
        Text(
            text = item.value,
            style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.SemiBold),
            color = InkPrimary,
        )
    }
}

@Composable
private fun ActionRow(action: ResponsableProfileAction) {
    val onClick = action.onClick
    val rowModifier = if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier

    Row(
        modifier = rowModifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.md),
    ) {
        Icon(
            imageVector = action.icon,
            contentDescription = null,
            tint = BrandPurple,
            modifier = Modifier.size(20.dp),
        )
        Text(
            text = action.label,
            style = SonrisasTextStyle.Body,
            color = InkPrimary,
            modifier = Modifier.weight(1f),
        )
        if (action.trailingText != null) {
            Text(text = action.trailingText, style = SonrisasTextStyle.Caption, color = InkFaint)
        }
        if (onClick != null) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                contentDescription = null,
                tint = InkFaint,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

@Preview(name = "Perfil del Responsable", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun ResponsableProfileScreenPreview() {
    SonrisasAsistenciasTheme {
        ResponsableProfileScreen(
            profile = ResponsableProfile(
                fullName = "Elena Ríos Salazar",
                email = "elena@gmail.com",
                estancia = "Arcoíris",
                staffCount = 18,
            ),
        )
    }
}
