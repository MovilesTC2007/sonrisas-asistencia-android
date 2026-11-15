package mx.tec.sonrisas.asistencia.ui.screens

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

private data class ProfileAction(
    val icon: ImageVector,
    val label: String,
    val trailingText: String? = null,
    val onClick: (() -> Unit)? = null,
)

@Composable
fun UserProfileScreen(
    fullName: String,
    roleSummary: String,
    details: List<AccountDetail>,
    locationUsageText: String,
    modifier: Modifier = Modifier,
    onChangePasswordClick: () -> Unit = {},
    onPrivacyNoticeClick: () -> Unit = {},
) {
    val actions = listOf(
        ProfileAction(
            icon = Icons.Outlined.Lock,
            label = "Cambiar contraseña",
            onClick = onChangePasswordClick,
        ),
        ProfileAction(
            icon = Icons.Outlined.LocationOn,
            label = "Ubicación",
            trailingText = locationUsageText,
        ),
        ProfileAction(
            icon = Icons.Outlined.Description,
            label = "Aviso de privacidad",
            onClick = onPrivacyNoticeClick,
        ),
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
        ProfileHeaderCard(fullName = fullName, roleSummary = roleSummary)
        Spacer(Modifier.height(Spacing.lg))
        SonrisasListCard(items = details) { detail ->
            ProfileDetailRow(detail = detail)
        }
        Spacer(Modifier.height(Spacing.lg))
        SonrisasListCard(items = actions) { action ->
            ProfileActionRow(action = action)
        }
        Spacer(Modifier.height(Spacing.xxl))
    }
}

@Composable
private fun ProfileHeaderCard(
    fullName: String,
    roleSummary: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
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
            Text(
                text = fullName,
                style = SonrisasTextStyle.Title,
                color = Color.White,
            )
            Spacer(Modifier.height(Spacing.xs))
            Text(
                text = roleSummary,
                style = SonrisasTextStyle.Body,
                color = BrandPurpleLight,
            )
        }
    }
}

@Composable
private fun ProfileDetailRow(
    detail: AccountDetail,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
    ) {
        Text(
            text = detail.label,
            style = SonrisasTextStyle.Caption,
            color = InkFaint,
        )
        Text(
            text = detail.value,
            style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.SemiBold),
            color = InkPrimary,
        )
    }
}

@Composable
private fun ProfileActionRow(
    action: ProfileAction,
    modifier: Modifier = Modifier,
) {
    val onClick = action.onClick
    val rowModifier = if (onClick != null) modifier.clickable(onClick = onClick) else modifier

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
            Text(
                text = action.trailingText,
                style = SonrisasTextStyle.Caption,
                color = InkFaint,
            )
        } else if (onClick != null) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                contentDescription = null,
                tint = InkFaint,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

private val sampleProfileDetails = listOf(
    AccountDetail("Correo", "fernando@gmail.com"),
    AccountDetail("Área", "Lactantes"),
    AccountDetail("En la estancia desde", "12 de enero de 2026"),
)

@Preview(showBackground = true)
@Composable
private fun UserProfilePreview() {
    SonrisasAsistenciasTheme {
        UserProfileScreen(
            fullName = "Fernando Anckerman Ramírez",
            roleSummary = "Maestro · Estancia Arcoíris",
            details = sampleProfileDetails,
            locationUsageText = "Solo al registrar",
        )
    }
}
