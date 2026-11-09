package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTypography
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.R


data class AccountDetail(
    val label: String,
    val value: String,
)

@Composable
fun AccountReadyScreen(
    greetingName: String,
    details: List<AccountDetail>,
    onContinueClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgCream)
            .systemBarsPadding()
            .padding(horizontal = Spacing.xxl),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(Spacing.xxl))
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
            SonrisasListCard(items = details) { detail ->
                AccountDetailRow(detail = detail)
            }
            Spacer(modifier = Modifier.height(Spacing.xl))
        }
        SonrisasPrimaryButton(
            text = "Ir al inicio",
            onClick = onContinueClick,
        )
        Spacer(modifier = Modifier.height(Spacing.lg))
        Text(
            text = "¿Algo no está bien? Avísale a tu responsable.",
            style = SonrisasTextStyle.Body,
            color = InkFaint,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.xl),
        )
        Spacer(modifier = Modifier.height(Spacing.xxl))
    }
}

@Composable
private fun AccountDetailRow(
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

private val sampleAccountDetails = listOf(
    AccountDetail("Nombre", "Patricia Gómez Ríos"),
    AccountDetail("Correo", "paty.gomez@gmail.com"),
    AccountDetail("Estancia", "Arcoíris · Maternales"),
    AccountDetail("Rol", "Empleada"),
)

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun AccountReadyScreenPreview() {
    SonrisasAsistenciasTheme {
        AccountReadyScreen(
            greetingName = "Patricia",
            details = sampleAccountDetails,
            onContinueClick = {},
        )
    }
}
