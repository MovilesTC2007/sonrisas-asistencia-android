package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeep
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTypography
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

@Composable
fun UserProfileScreen(
    fullName: String,
    roleSummary: String,
    details: List<AccountDetail>,
    locationUsageText: String,
    onChangePasswordClick: () -> Unit,
    onPrivacyNoticeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        containerColor = BgCream,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.xxl),
        ) {
            Spacer(modifier = Modifier.height(Spacing.xxl))
            Row(verticalAlignment = Alignment.CenterVertically) {
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
                Spacer(Modifier.size(Spacing.sm))
                Text(
                    text = "Perfil",
                    style = SonrisasTypography.displayMedium,
                    color = InkPrimary,
                )
            }

            Spacer(modifier = Modifier.height(Spacing.xl))
            ProfileHeaderCard(fullName = fullName, roleSummary = roleSummary)

            Spacer(modifier = Modifier.height(Spacing.lg))
            ProfileDetailsCard(details = details)

            Spacer(modifier = Modifier.height(Spacing.lg))
            ProfileActionsCard(
                locationUsageText = locationUsageText,
                onChangePasswordClick = onChangePasswordClick,
                onPrivacyNoticeClick = onPrivacyNoticeClick,
            )

            Spacer(modifier = Modifier.height(Spacing.xxl))
        }
    }
}

@Composable
private fun ProfileHeaderCard(
    fullName: String,
    roleSummary: String,
    modifier: Modifier = Modifier,
) {
    val initials = fullName
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercase() }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(BrandPurpleDeep)
            .padding(Spacing.xl),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.lg),
    ) {
        Box(
            modifier = Modifier
                .size(68.dp)
                .clip(CircleShape)
                .background(BrandPurple),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = initials,
                style = SonrisasTypography.titleLarge,
                color = Color.White,
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = fullName,
                style = SonrisasTypography.headlineSmall,
                color = Color.White,
            )
            Spacer(modifier = Modifier.height(Spacing.xs))
            Text(
                text = roleSummary,
                style = SonrisasTypography.bodyLarge,
                color = BrandPurpleLight,
            )
        }
    }
}

@Composable
private fun ProfileDetailsCard(
    details: List<AccountDetail>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceWhite)
            .padding(horizontal = Spacing.xl, vertical = Spacing.md),
    ) {
        details.forEachIndexed { index, detail ->
            Column(modifier = Modifier.padding(vertical = Spacing.md)) {
                Text(
                    text = detail.label,
                    style = SonrisasTypography.bodyMedium,
                    color = InkFaint,
                )
                Text(
                    text = detail.value,
                    style = SonrisasTypography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = InkPrimary,
                )
            }
            if (index<details.lastIndex) {
                HorizontalDivider(color = LineColor)
            }
        }
    }
}

@Composable
private fun ProfileActionsCard(
    locationUsageText: String,
    onChangePasswordClick: () -> Unit,
    onPrivacyNoticeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceWhite)
            .padding(horizontal = Spacing.xl, vertical = Spacing.xs),
    ) {
        ProfileActionRow(
            icon = Icons.Outlined.Lock,
            label = "Cambiar contraseña",
            onClick = onChangePasswordClick,
        )
        HorizontalDivider(color = LineColor)
        ProfileActionRow(
            icon = Icons.Outlined.LocationOn,
            label = "Ubicación",
            trailingText = locationUsageText,
        )
        HorizontalDivider(color = LineColor)
        ProfileActionRow(
            icon = Icons.Outlined.Description,
            label = "Aviso de privacidad",
            onClick = onPrivacyNoticeClick,
        )
    }
}

@Composable
private fun ProfileActionRow(
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier,
    trailingText: String? = null,
    onClick: (() -> Unit)? = null,
) {
    val rowModifier = if (onClick!=null) modifier.clickable(onClick = onClick) else modifier

    Row(
        modifier = rowModifier
            .fillMaxWidth()
            .padding(vertical = Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.md),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = BrandPurple,
            modifier = Modifier.size(20.dp),
        )
        Text(
            text = label,
            style = SonrisasTypography.bodyLarge,
            color = InkPrimary,
            modifier = Modifier.weight(1f),
        )
        if (trailingText!=null) {
            Text(
                text = trailingText,
                style = SonrisasTypography.bodyMedium,
                color = InkFaint,
            )
        } else if (onClick!=null) {
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

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun UserProfilePreview() {
    SonrisasAsistenciasTheme {
        UserProfileScreen(
            fullName = "Fernando Anckerman Ramírez",
            roleSummary = "Maestro · Estancia Arcoíris",
            details = sampleProfileDetails,
            locationUsageText = "Solo al registrar",
            onChangePasswordClick = {},
            onPrivacyNoticeClick = {},
        )
    }
}
