package mx.tec.sonrisas.asistencia.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val SonrisasLightColorScheme = lightColorScheme(
    primary = BrandPurple,
    onPrimary = SurfaceWhite,
    primaryContainer = BrandPurpleLight,
    onPrimaryContainer = BrandPurpleDeepText,
    secondaryContainer = BrandPurpleDeep,
    onSecondaryContainer = SurfaceWhite,
    background = BgCream,
    onBackground = InkPrimary,
    surface = SurfaceWhite,
    onSurface = InkPrimary,
    surfaceVariant = SurfaceLilac,
    onSurfaceVariant = InkSoft,
    outline = LineColor,
    error = AccentRed,
    onError = SurfaceWhite,
)

@Composable
fun SonrisasAsistenciasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = SonrisasLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SonrisasTypography,
        shapes = SonrisasShapes,
        content = content,
    )
}
