package mx.tec.sonrisas.asistencia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mx.tec.sonrisas.asistencia.ui.screens.CreateAccountScreen
import mx.tec.sonrisas.asistencia.ui.screens.LocationRequestScreen
import mx.tec.sonrisas.asistencia.ui.screens.LoginScreen
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SonrisasAsistenciasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LocationRequestScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LocationRequestScreenPreview() {
    SonrisasAsistenciasTheme {
       LocationRequestScreen()
    }
}
