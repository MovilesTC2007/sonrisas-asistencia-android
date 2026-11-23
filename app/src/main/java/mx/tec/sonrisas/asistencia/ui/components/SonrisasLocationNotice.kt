package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GpsNotFixed
import androidx.compose.material.icons.outlined.WrongLocation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

// Aviso de ubicación para el registro de asistencia. Reutiliza SonrisasNoticeBanner,
// así que se ve igual que el de "Sin conexión" (fondo durazno, icono naranja).
// Las pantallas reciben un LocationIssue? y solo lo muestran cuando no es null.
enum class LocationIssue(val icon: ImageVector, val message: String) {
    Imprecise(
        icon = Icons.Outlined.GpsNotFixed,
        message = "Ubicación poco precisa · El registro se marcará para revisión",
    ),
    Suspicious(
        icon = Icons.Outlined.WrongLocation,
        message = "Ubicación sospechosa · No coincide con la estancia. Se marcará para revisión",
    ),
}

@Composable
fun SonrisasLocationNotice(
    issue: LocationIssue,
    modifier: Modifier = Modifier,
) {
    SonrisasNoticeBanner(
        icon = issue.icon,
        message = issue.message,
        modifier = modifier,
    )
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun SonrisasLocationNoticePreview() {
    SonrisasAsistenciasTheme {
        Column(
            modifier = Modifier.padding(Spacing.xl),
            verticalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            SonrisasLocationNotice(issue = LocationIssue.Imprecise)
            SonrisasLocationNotice(issue = LocationIssue.Suspicious)
        }
    }
}
