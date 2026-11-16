package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

// seccion de formulario: etiqueta arriba + el control que se le pase (campo, chips, dropdown, fecha)
// la etiqueta vive fuera del control (como en SonrisasDropdownField), asi todos los campos de un formulario se alinean igual
// InkSoft y no InkFaint: sobre crema InkFaint no llega al contraste AA
@Composable
fun SonrisasFormField(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = SonrisasTextStyle.Body,
            color = InkSoft,
        )
        Spacer(Modifier.height(Spacing.sm))
        content()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFBF6ED, widthDp = 360)
@Composable
private fun SonrisasFormFieldPreview() {
    SonrisasAsistenciasTheme {
        var name by remember { mutableStateOf("Patricia Gómez Ríos") }
        SonrisasFormField(label = "Nombre completo", modifier = Modifier.padding(Spacing.xl)) {
            SonrisasInputField(value = name, onValueChange = { name = it })
        }
    }
}