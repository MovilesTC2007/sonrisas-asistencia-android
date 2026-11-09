package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.AccentGreen
import mx.tec.sonrisas.asistencia.ui.theme.AccentOrange
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing


//esto es la fila de cobertura, es la que se encontraba copiada entre ManagerHome y en el panel para direccion
//la idea es solo tenerla directamente en una fila para que pueda ir dentro de la SonrisasListCard
@Composable
fun SonrisasCoverageRow(
    label: String,
    presentCount: Int,
    requiredCount: Int,
    modifier: Modifier = Modifier,
    unit: String? = null,
) {
    val isComplete = presentCount >= requiredCount
    val count = if (unit != null) "$presentCount/$requiredCount $unit" else "$presentCount/$requiredCount"

    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = SonrisasTextStyle.Body,
            color = InkPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
        Spacer(Modifier.width(Spacing.sm))
        Text(
            text = count,
            style = SonrisasTextStyle.Body,
            color = InkSoft,
        )
        Spacer(Modifier.width(Spacing.sm))
        Icon(
            imageVector = if (isComplete) Icons.Outlined.CheckCircle else Icons.Outlined.ErrorOutline,
            contentDescription = if (isComplete) "Cobertura completa" else "Cobertura incompleta",
            tint = if (isComplete) AccentGreen else AccentOrange,
            modifier = Modifier.size(20.dp),
        )
    }
}
