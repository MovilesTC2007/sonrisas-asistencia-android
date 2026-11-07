package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.RadiusMd
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

@Composable
fun SonrisasAddButton(
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(
        onClick = onClick,
        shape = RadiusMd,
        containerColor = BrandPurpleLight,
        contentColor = BrandPurpleDeepText,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp,
        ),
        modifier = modifier.size(56.dp),
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = contentDescription,
            modifier = Modifier.size(28.dp),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFBF6ED)
@Composable
private fun SonrisasAddButtonPreview() {
    SonrisasAsistenciasTheme {
        Box(Modifier.padding(Spacing.xl)) {
            SonrisasAddButton(contentDescription = "Agregar personal", onClick = {})
        }
    }
}