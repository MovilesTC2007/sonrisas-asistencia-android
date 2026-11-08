package mx.tec.sonrisas.asistencia.ui.screens

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
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.PersonRemove
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.ResponsableBottomBar
import mx.tec.sonrisas.asistencia.ui.components.ResponsableTab
import mx.tec.sonrisas.asistencia.ui.components.SonrisasAvatar
import mx.tec.sonrisas.asistencia.ui.components.SonrisasOutlinedButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

private data class StaffProfile(
    val name: String,
    val role: String,
    val room: String,
    val activeSince: String,
    val email: String,
    val shift: String,
    val center: String,
)

private data class DetailField(val label: String, val value: String)

private val SampleProfile = StaffProfile(
    name = "Ana Martínez López",
    role = "Maestra",
    room = "Lactantes",
    activeSince = "12 ene 2026",
    email = "ana.martinez@correo.mx",
    shift = "Lunes a viernes · 8:00 – 16:00",
    center = "Arcoíris · Lactantes",
)

//cambio de nombre de la funcion, se confunde para la llamada directa de la pantalla en el mainactivity
@Composable
fun DetailStaffScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onEdit: () -> Unit = {},
    onTransfer: () -> Unit = {},
    onDeactivate: () -> Unit = {},
) {
    var selectedTab by rememberSaveable { mutableStateOf(ResponsableTab.Personal) }
    val profile = SampleProfile

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
            SonrisasTopBar(title = "Detalle de personal", onBack = onBack) //encabezado componentes

            Spacer(Modifier.height(Spacing.lg))
            ProfileCard(profile = profile)

            Spacer(Modifier.height(Spacing.xxl))
            Text(
                text = "Datos de su estancia",
                style = SonrisasTextStyle.Title,
                color = InkPrimary,
                modifier = Modifier
                    .padding(horizontal = Spacing.xs)
                    .semantics { heading() },
            )

            Spacer(Modifier.height(Spacing.sm))
            DetailFieldsCard(
                fields = listOf(
                    DetailField("Correo", profile.email),
                    DetailField("Turno", profile.shift),
                    DetailField("Estancia", profile.center),
                ),
            )

            // Las acciones van justo debajo de los datos, como en el mockup, y no
            // fijas abajo: así se leen como acciones sobre esta persona.
            Spacer(Modifier.height(Spacing.xxl))
            SonrisasPrimaryButton(
                text = "Editar",
                onClick = onEdit,
                leadingIcon = Icons.Outlined.Edit,
            )
            Spacer(Modifier.height(Spacing.md))
            SonrisasOutlinedButton(
                text = "Trasladar",
                onClick = onTransfer,
                leadingIcon = Icons.Outlined.SwapHoriz,
            )
            Spacer(Modifier.height(Spacing.md))
            SonrisasOutlinedButton(
                text = "Dar de baja",
                onClick = onDeactivate,
                leadingIcon = Icons.Outlined.PersonRemove,
                destructive = true, //para mejoramiento de visualizacion 
            )
            Spacer(Modifier.height(Spacing.xxl))
        }

        ResponsableBottomBar(
            selected = selectedTab,
            onTabClick = { selectedTab = it },
        )
    }
}

@Composable
private fun ProfileCard(profile: StaffProfile) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(BrandPurpleLight)
            .semantics(mergeDescendants = true) {}
            .padding(Spacing.lg),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SonrisasAvatar(name = profile.name)
            Spacer(Modifier.width(Spacing.lg))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = profile.name,
                    style = SonrisasTextStyle.Title,
                    color = BrandPurpleDeepText,
                )
                Spacer(Modifier.height(Spacing.xs))
                Text(
                    text = "${profile.role} · ${profile.room}",
                    style = SonrisasTextStyle.Body,
                    color = InkSoft,
                )
            }
        }
        Spacer(Modifier.height(Spacing.lg))
        Text(
            text = "Personal activo desde el ${profile.activeSince}",
            style = SonrisasTextStyle.Caption,
            color = InkSoft,
        )
    }
}

@Composable
private fun DetailFieldsCard(fields: List<DetailField>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceWhite),
    ) {
        fields.forEachIndexed { index, field ->
            DetailFieldRow(field = field)
            if (index < fields.lastIndex) {
                HorizontalDivider(
                    color = LineColor,
                    modifier = Modifier.padding(horizontal = Spacing.lg),
                )
            }
        }
    }
}

@Composable
private fun DetailFieldRow(field: DetailField) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) {}
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
    ) {
        Text(text = field.label, style = SonrisasTextStyle.Label, color = InkFaint)
        Spacer(Modifier.height(2.dp))
        Text(text = field.value, style = SonrisasTextStyle.Body, color = InkPrimary)
    }
}

@Preview(name = "Detalle de personal (p. 14)", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun DetailStaffScreenPreview() {
    SonrisasAsistenciasTheme {
        DetailStaffScreen()
    }
}