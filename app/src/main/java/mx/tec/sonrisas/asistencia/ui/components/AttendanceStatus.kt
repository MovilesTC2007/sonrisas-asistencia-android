package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import mx.tec.sonrisas.asistencia.ui.theme.AccentGreen
import mx.tec.sonrisas.asistencia.ui.theme.AccentOrange
import mx.tec.sonrisas.asistencia.ui.theme.AccentRed
import mx.tec.sonrisas.asistencia.ui.theme.AccentYellow
import mx.tec.sonrisas.asistencia.ui.theme.StatusGreenInk
import mx.tec.sonrisas.asistencia.ui.theme.StatusGreenTint
import mx.tec.sonrisas.asistencia.ui.theme.StatusOrangeInk
import mx.tec.sonrisas.asistencia.ui.theme.StatusOrangeTint
import mx.tec.sonrisas.asistencia.ui.theme.StatusRedInk
import mx.tec.sonrisas.asistencia.ui.theme.StatusRedTint
import mx.tec.sonrisas.asistencia.ui.theme.StatusYellowInk
import mx.tec.sonrisas.asistencia.ui.theme.StatusYellowTint

//esto servira para la continuacion del mapeo entre estado, texo icono y colores
//esto porque el recordstatus utilizaba iconos distintos que basicamente eran para el mismo estado
//caso comun debido al propio mapeo de pantallas
enum class AttendanceStatus(
    val label: String,
    val icon: ImageVector,
    val accent: Color,
    val tint: Color,
    val ink: Color,
) {
    OnTime("A tiempo", Icons.Outlined.CheckCircle, AccentGreen, StatusGreenTint, StatusGreenInk),
    MinorDelay("Retardo menor", Icons.Outlined.Schedule, AccentYellow, StatusYellowTint, StatusYellowInk),
    MajorDelay("Retardo mayor", Icons.Outlined.ErrorOutline, AccentOrange, StatusOrangeTint, StatusOrangeInk),
    Absent("Falta", Icons.Outlined.Cancel, AccentRed, StatusRedTint, StatusRedInk),
}
