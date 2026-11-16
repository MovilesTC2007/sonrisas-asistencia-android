package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.tec.sonrisas.asistencia.ui.components.SonrisasBackStyle
import mx.tec.sonrisas.asistencia.ui.components.SonrisasBottomBar
import mx.tec.sonrisas.asistencia.ui.components.SonrisasButtonSize
import mx.tec.sonrisas.asistencia.ui.components.SonrisasFormField
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPersonCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPrimaryButton
import mx.tec.sonrisas.asistencia.ui.components.SonrisasSegmentedControl
import mx.tec.sonrisas.asistencia.ui.components.SonrisasSwitch
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.components.sonrisasCardShadow
import mx.tec.sonrisas.asistencia.ui.navigation.SonrisasTab
import mx.tec.sonrisas.asistencia.ui.navigation.UserRole
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

// Permisos y rol (RF-12 · RF-13 · RF-20).

data class PermissionOption(
    val label: String,
    val enabledByDefault: Boolean,
)

private val RoleOptions = listOf("Empleada", "Coordinadora")

@Composable
fun StaffPermissionsScreen(
    modifier: Modifier = Modifier,
    personName: String = "Lucía Hernández Ruiz",
    personDetail: String = "Maestra · Lactantes",
    initialRole: String = "Coordinadora",
    permissions: List<PermissionOption> = listOf(
        PermissionOption("Registrar entrada de varias personas", enabledByDefault = true),
        PermissionOption("Registrar faltas y retardos", enabledByDefault = true),
        PermissionOption("Corregir registros", enabledByDefault = false),
        PermissionOption("Dar de alta o baja personal", enabledByDefault = false),
        PermissionOption("Aprobar solicitudes", enabledByDefault = false),
        PermissionOption("Ver estadísticas de la estancia", enabledByDefault = true),
    ),
) {
    var role by rememberSaveable { mutableStateOf(initialRole) }
    val enabled = remember(permissions) {
        mutableStateListOf<Boolean>().apply { addAll(permissions.map { it.enabledByDefault }) }
    }
    var selectedTab by remember { mutableStateOf(SonrisasTab.Personal) }

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
                title = "Permisos y rol",
                onBack = {},
                backStyle = SonrisasBackStyle.Plain,
            )

            Spacer(Modifier.height(Spacing.lg))
            SonrisasPersonCard(name = personName, detail = personDetail)

            Spacer(Modifier.height(Spacing.xxl))
            SonrisasFormField(label = "Rol") {
                SonrisasSegmentedControl(
                    options = RoleOptions,
                    selected = role,
                    onSelect = { role = it },
                    label = { it },
                )
            }

            Spacer(Modifier.height(Spacing.xxl))
            Text(
                text = "Qué puede hacer como ${role.lowercase()}",
                style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.SemiBold),
                color = InkPrimary,
                modifier = Modifier.semantics { heading() },
            )
            Spacer(Modifier.height(Spacing.md))
            SonrisasListCard(
                items = permissions.indices.toList(),
                verticalPadding = Spacing.sm,
                modifier = Modifier.sonrisasCardShadow(),
            ) { index ->
                PermissionRow(
                    label = permissions[index].label,
                    checked = enabled[index],
                    onCheckedChange = { enabled[index] = it },
                )
            }

            Spacer(Modifier.height(Spacing.xxl + Spacing.sm))
            SonrisasPrimaryButton(
                text = "Guardar permisos",
                onClick = {},
                size = SonrisasButtonSize.Large,
                elevated = true,
            )
            Spacer(Modifier.height(Spacing.lg))
        }

        SonrisasBottomBar(
            items = UserRole.Responsable.tabs,
            selected = selectedTab,
            onItemClick = { selectedTab = it },
        )
    }
}

@Composable
private fun PermissionRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp)
            .toggleable(value = checked, role = Role.Switch, onValueChange = onCheckedChange)
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = SonrisasTextStyle.Body.copy(
                fontSize = 16.sp,
                lineHeight = 22.sp,
                fontWeight = FontWeight.Medium,
            ),
            color = InkPrimary,
            modifier = Modifier.weight(1f),
        )
        Spacer(Modifier.width(Spacing.lg))
        SonrisasSwitch(checked = checked)
    }
}

@Preview(name = "Permisos y rol", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun StaffPermissionsScreenPreview() {
    SonrisasAsistenciasTheme {
        StaffPermissionsScreen()
    }
}

@Preview(name = "Permisos y rol · Empleada", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun StaffPermissionsScreenEmployeePreview() {
    SonrisasAsistenciasTheme {
        StaffPermissionsScreen(initialRole = "Empleada")
    }
}
