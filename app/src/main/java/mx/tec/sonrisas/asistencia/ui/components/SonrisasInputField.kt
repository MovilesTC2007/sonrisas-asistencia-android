package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusMd
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

// alto comun de los campos de formulario (mismo que SonrisasSearchField y SonrisasDropdownField)
internal val SonrisasFieldHeight = 52.dp

// campo de texto de una linea en tarjeta blanca, para formularios con la etiqueta arriba (ver SonrisasFormField)
// no confundir con SonrisasTextField, que lleva el chip de icono y la etiqueta adentro (login / crear cuenta)
// la decoracion va en decorationBox para que todo el rectangulo sea area tactil y no solo la linea de texto
@Composable
fun SonrisasInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = SonrisasTextStyle.Body.copy(color = InkPrimary),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(BrandPurple),
        modifier = modifier
            .fillMaxWidth()
            .height(SonrisasFieldHeight)
            .clip(RadiusMd)
            .background(SurfaceWhite)
            .border(width = 1.dp, color = LineColor, shape = RadiusMd),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Spacing.lg),
                contentAlignment = Alignment.CenterStart,
            ) {
                if (value.isEmpty() && placeholder != null) {
                    Text(text = placeholder, style = SonrisasTextStyle.Body, color = InkFaint)
                }
                innerTextField()
            }
        },
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFBF6ED, widthDp = 360)
@Composable
private fun SonrisasInputFieldPreview() {
    SonrisasAsistenciasTheme {
        var filled by remember { mutableStateOf("Patricia Gómez Ríos") }
        var empty by remember { mutableStateOf("") }
        Column(Modifier.padding(Spacing.xl)) {
            SonrisasInputField(value = filled, onValueChange = { filled = it })
            Spacer(Modifier.height(Spacing.md))
            SonrisasInputField(value = empty, onValueChange = { empty = it }, placeholder = "Escribe el nombre completo")
        }
    }
}