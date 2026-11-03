package mx.tec.sonrisas.asistencia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import mx.tec.sonrisas.asistencia.ui.screens.LoginScreen
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SonrisasAsistenciasTheme {
                LoginScreen()
            }
        }
    }
}