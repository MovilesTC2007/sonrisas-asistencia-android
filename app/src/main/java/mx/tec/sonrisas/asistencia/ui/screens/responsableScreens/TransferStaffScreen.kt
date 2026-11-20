package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import mx.tec.sonrisas.asistencia.ui.components.SonrisasBackStyle
import mx.tec.sonrisas.asistencia.ui.components.SonrisasButtonSize
import mx.tec.sonrisas.asistencia.ui.components.SonrisasDateField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasDropdownField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasFormField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasInfoCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPersonCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasRouteCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.components.sonrisasCardShadow
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

// Traslado entre estancias (RF-27 · CU03, flujo alterno).

data class WorkedPeriod(
    val center: String,
    val range: String,
    val days: Int,
)

private val AreaOptions = listOf("Lactantes", "Maternales", "Apoyo")

@Composable
fun TransferStaffScreen(
    modifier: Modifier = Modifier,
    personName: String = "Ana Martínez López",
    personDetail: String = "Maestra · Lactantes",
    originCenter: String = "Arcoíris",
    destinationCenter: String = "Rayito de Sol",
    startDate: String = "16 nov 2026",
    initialArea: String = "Lactantes",
    workedPeriods: List<WorkedPeriod> = listOf(
        WorkedPeriod(center = "Arcoíris", range = "12 ene – 15 nov 2026", days = 214),
    ),
) {
    var area by rememberSaveable { mutableStateOf(initialArea) }

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
                title = "Trasladar",
                onBack = {},
                backStyle = SonrisasBackStyle.Plain,
            )

            Spacer(Modifier.height(Spacing.lg))
            SonrisasPersonCard(name = personName, detail = personDetail)

            Spacer(Modifier.height(Spacing.xl))
            SonrisasRouteCard(
                from = originCenter,
                to = destinationCenter,
                modifier = Modifier.sonrisasCardShadow(),
            )

            Spacer(Modifier.height(Spacing.xl))
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.md)) {
                SonrisasFormField(label = "A partir del", modifier = Modifier.weight(1f)) {
                    SonrisasDateField(
                        value = startDate,
                        label = "A partir del",
                        onClick = {},
                        showIcon = false,
                    )
                }
                SonrisasFormField(label = "Área nueva", modifier = Modifier.weight(1f)) {
                    SonrisasDropdownField(
                        value = area,
                        options = AreaOptions,
                        onSelect = { area = it },
                        label = "Área nueva",
                    )
                }
            }

            Spacer(Modifier.height(Spacing.xxl))
            Text(
                text = "Días trabajados",
                style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.SemiBold),
                color = InkPrimary,
                modifier = Modifier.semantics { heading() },
            )
            Spacer(Modifier.height(Spacing.md))
            SonrisasListCard(
                items = workedPeriods,
                modifier = Modifier.sonrisasCardShadow(),
            ) { period ->
                WorkedPeriodRow(period)
            }

            Spacer(Modifier.height(Spacing.xl))
            SonrisasInfoCard(
                message = "La responsable de $destinationCenter recibirá el aviso y verá su historial.",
            )

            Spacer(Modifier.height(Spacing.xxl + Spacing.sm))
            SonrisasPrimaryButton(
                text = "Confirmar traslado",
                onClick = {},
                size = SonrisasButtonSize.Large,
                elevated = true,
            )
            Spacer(Modifier.height(Spacing.lg))
        }
    }
}

@Composable
private fun WorkedPeriodRow(period: WorkedPeriod) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.lg, vertical = Spacing.xl)
            .semantics(mergeDescendants = true) {},
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = period.center,
                style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.Medium),
                color = InkPrimary,
            )
            Text(
                text = period.range,
                style = SonrisasTextStyle.Caption,
                color = InkSoft,
            )
        }
        Spacer(Modifier.width(Spacing.md))
        Text(
            text = "${period.days} días",
            style = SonrisasTextStyle.Body,
            color = InkSoft,
        )
    }
}

@Preview(name = "Trasladar", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun TransferStaffScreenPreview() {
    SonrisasAsistenciasTheme {
        TransferStaffScreen()
    }
}

@Preview(name = "Trasladar · varios periodos", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun TransferStaffScreenSeveralPeriodsPreview() {
    SonrisasAsistenciasTheme {
        TransferStaffScreen(
            workedPeriods = listOf(
                WorkedPeriod(center = "Arcoíris", range = "12 ene – 15 nov 2026", days = 214),
                WorkedPeriod(center = "Los Girasoles", range = "3 mar 2025 – 10 ene 2026", days = 313),
            ),
        )
    }
}
