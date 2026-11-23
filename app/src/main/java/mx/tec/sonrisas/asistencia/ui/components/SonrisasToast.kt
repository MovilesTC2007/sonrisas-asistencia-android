package mx.tec.sonrisas.asistencia.ui.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberSonrisasToast(
    message: String = "Cambios guardados correctamente",
): () -> Unit {
    val context = LocalContext.current
    return remember(context, message) {
        { Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }
    }
}
