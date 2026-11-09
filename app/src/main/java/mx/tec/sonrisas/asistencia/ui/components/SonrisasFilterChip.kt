package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

@Composable
fun SonrisasFilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    role: Role = Role.RadioButton,
) {
    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .height(40.dp)
            .clip(RadiusPill)
            .background(if (selected) BrandPurpleLight else SurfaceWhite)
            .border(
                border = BorderStroke(1.5.dp, if (selected) BrandPurple else LineColor),
                shape = RadiusPill,
            )
            .selectable(selected = selected, role = role, onClick = onClick)
            .padding(horizontal = Spacing.lg),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = SonrisasTextStyle.Body,
            color = if (selected) BrandPurpleDeepText else InkSoft,
            maxLines = 1,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFBF6ED)
@Composable
private fun SonrisasFilterChipPreview() {
    SonrisasAsistenciasTheme {
        var pdf by remember { mutableStateOf(true) }
        Row(
            modifier = Modifier.padding(Spacing.xl),
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
        ) {
            SonrisasFilterChip(text = "PDF", selected = pdf, onClick = { pdf = true })
            SonrisasFilterChip(text = "Excel", selected = !pdf, onClick = { pdf = false })
        }
    }
}