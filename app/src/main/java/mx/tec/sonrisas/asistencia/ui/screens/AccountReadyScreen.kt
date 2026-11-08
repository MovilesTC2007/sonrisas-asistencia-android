package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.tec.sonrisas.asistencia.R
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTypography
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

data class AccountDetail(
    val label: String,
    val value: String,
)

@Composable
fun AccountReadyScreen(
    greetingName: String,
    details: List<AccountDetail>,
    modifier: Modifier = Modifier,
    onContinueClick: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        containerColor = BgCream,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = Spacing.xxl),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(48.dp))
                Image(
                    painter = painterResource(id = R.drawable.sonrisaslogo),
                    contentDescription = "Logo Sonrisas",
                    modifier = Modifier.size(250.dp),
                )

                Text(
                    text = "TODO LISTO",
                    style = SonrisasTypography.titleMedium.copy(letterSpacing = 2.sp),
                    color = BrandPurple,
                )
                Spacer(modifier = Modifier.height(Spacing.sm))
                Text(
                    text = "Tu cuenta está\nlista, $greetingName",
                    style = SonrisasTypography.displayMedium,
                    color = InkPrimary,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(Spacing.md))
                Text(
                    text = "Revisa que tus datos estén bien.",
                    style = SonrisasTypography.titleMedium,
                    color = InkSoft,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(Spacing.xl))
                AccountDetailsCard(details = details)
                Spacer(modifier = Modifier.height(Spacing.xl))
            }

            ContinueButton(onClick = onContinueClick)
            Spacer(modifier = Modifier.height(Spacing.lg))

            Text(
                text = "¿Algo no está bien? Avísale a tu responsable.",
                style = SonrisasTypography.bodyMedium,
                color = InkFaint,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.xl),
            )
            Spacer(modifier = Modifier.height(Spacing.xxl))
        }
    }
}

@Composable
private fun AccountDetailsCard(
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
                    style = SonrisasTypography.titleMedium,
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
private fun ContinueButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp,
                shape = RadiusPill,
                ambientColor = BrandPurple.copy(alpha = 0.35f),
                spotColor = BrandPurple.copy(alpha = 0.45f),
            )
            .clip(RadiusPill)
            .background(BrandPurple)
            .clickable(onClick = onClick)
            .padding(vertical = Spacing.xl),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Ir al inicio",
            style = SonrisasTypography.titleLarge,
            color = Color.White,
        )
    }
}

private val sampleAccountDetails = listOf(
    AccountDetail("Nombre", "Patricia Gómez Ríos"),
    AccountDetail("Correo", "paty.gomez@gmail.com"),
    AccountDetail("Estancia", "Arcoíris · Maternales"),
    AccountDetail("Rol", "Empleada"),
)


@Preview(showBackground = true, heightDp = 900)
@Composable
private fun AccountReadyWithContinuePreview() {
    SonrisasAsistenciasTheme {
        AccountReadyScreen(
            greetingName = "Patricia",
            details = sampleAccountDetails,
            onContinueClick = {},
        )
    }
}