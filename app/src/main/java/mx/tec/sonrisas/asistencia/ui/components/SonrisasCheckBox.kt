package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

@Composable
fun SonrisasCheckbox(
    state: ToggleableState,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
) {
    TriStateCheckbox(
        state = state,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = CheckboxDefaults.colors(
            checkedColor = BrandPurple,
            uncheckedColor = InkFaint,
            checkmarkColor = SurfaceWhite,
        ),
    )
}

@Composable
fun SonrisasCheckbox(
    checked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    enabled: Boolean = true,
) {
    SonrisasCheckbox(
        state = ToggleableState(checked),
        modifier = modifier,
        onClick = onCheckedChange?.let { { it(!checked) } },
        enabled = enabled,
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SonrisasCheckboxPreview() {
    SonrisasAsistenciasTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.md)) {
            SonrisasCheckbox(state = ToggleableState.On)
            SonrisasCheckbox(state = ToggleableState.Indeterminate)
            SonrisasCheckbox(state = ToggleableState.Off)
        }
    }
}