package mx.tec.sonrisas.asistencia.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.R
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.InkFaint
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.RadiusPill
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTypography
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceLilac
import mx.tec.sonrisas.asistencia.ui.theme.SurfacePeach

@Composable
fun WelcomeScreen(
    onStartClick: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    Scaffold(
        modifier = modifier,
        containerColor = BgCream,
    ){ innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.xxl),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Spacer(modifier = Modifier.height(48.dp))
            Image(
                painter = painterResource(id = R.drawable.sonrisaslogo),
                contentDescription = "Logo Sonrisas",
                modifier = Modifier.size(300.dp),
            )
            Text(
                text = "SONRISAS DE CRISTAL",
                style = SonrisasTypography.displaySmall,
                color = BrandPurple,
            )

            Spacer(modifier = Modifier.height(Spacing.lg))
            Text(
                text = "Cada turno,\ncon una sonrisa.",
                style = SonrisasTypography.displayLarge,
                color = InkPrimary,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(Spacing.xxl))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(Spacing.md),
            ){
                InfoCard(
                    text = "Registra tu turno en segundos",
                    backgroundColor = SurfacePeach,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                )

                InfoCard(
                    text = "Tu equipo, siempre a la vista",
                    backgroundColor = SurfaceLilac,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                )
            }

            Spacer(modifier = Modifier.height(Spacing.xxl))
            StartButton(onClick = onStartClick)

            Spacer(modifier = Modifier.height(Spacing.xl))
            Text(
                text = "¿Ya tienes una cuenta? Iniciar sesión",
                style = SonrisasTypography.bodyLarge,
                color = InkFaint,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clip(RadiusPill)
                    .clickable(onClick = onLoginClick)
                    .padding(horizontal = Spacing.md, vertical = Spacing.sm),
            )
        }
    }
}

@Composable
private fun InfoCard(
    text: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RadiusLg)
            .background(backgroundColor)
            .padding(Spacing.lg),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = text,
            style = SonrisasTypography.titleLarge,
            color = InkPrimary,
        )
    }
}

@Composable
private fun StartButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp,
                shape = RadiusPill,
                ambientColor = BrandPurple.copy(alpha = 0.35f),
                spotColor = BrandPurple.copy(alpha = 0.45f),
            )
            .clip(RadiusPill)
            .background(BrandPurple)
            .clickable(onClick = onClick)
            .padding(vertical = Spacing.xl),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Comenzar",
            style = SonrisasTypography.titleLarge,
            color = Color.White,
        )
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun WelcomePreview() {
    SonrisasAsistenciasTheme {
        WelcomeScreen(
            onStartClick = {},
            onLoginClick = {},
        )
    }
}