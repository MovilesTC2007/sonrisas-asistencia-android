package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusMd
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

// campo de fecha: misma tarjeta que SonrisasDropdownField pero con icono de calendario
// el icono usa el mismo margen que el chevron del dropdown para que queden en la misma columna
// no es editable; quien lo use decide que hace el toque (abrir un selector de fecha, etc.)
// label no se dibuja (vive en SonrisasFormField), solo para que TalkBack diga "Elegir <label>"
// showIcon = false para cuando el campo va en media columna junto a otro (Traslado: fecha + area): sin icono el texto de fecha cabe sin cortarse
@Composable
fun SonrisasDateField(
    value: String,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    showIcon: Boolean = true,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(SonrisasFieldHeight)
            .clip(RadiusMd)
            .background(SurfaceWhite)
            .border(1.dp, LineColor, RadiusMd)
            .clickable(
                role = Role.Button,
                onClickLabel = "Elegir $label",
                onClick = onClick,
            )
            .padding(start = Spacing.lg, end = if (showIcon) Spacing.md else Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = value,
            style = SonrisasTextStyle.Body,
            color = InkPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
        if (showIcon) {
            Icon(
                imageVector = Icons.Outlined.CalendarToday,
                contentDescription = null,
                tint = InkSoft,
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFBF6ED, widthDp = 360)
@Composable
private fun SonrisasDateFieldPreview() {
    SonrisasAsistenciasTheme {
        Column(Modifier.padding(Spacing.xl)) {
            SonrisasDateField(value = "9 de noviembre de 2026", label = "Fecha de alta", onClick = {})
            Box(Modifier.padding(top = Spacing.md)) {
                SonrisasDateField(value = "16 nov 2026", label = "A partir del", onClick = {}, showIcon = false)
            }
        }
    }
}
