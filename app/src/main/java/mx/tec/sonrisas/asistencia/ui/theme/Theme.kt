package mx.tec.sonrisas.asistencia.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Light palette only for now — the mockups are fixed-light, matching how the
// Figma/live-editor screens were designed (docs/identidad-de-marca.md §2).
// Add a dark scheme here once that direction is designed; darkTheme is read
// below so the call site won't need to change when it lands.
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
    val colorScheme = SonrisasLightColorScheme // swap in a dark scheme once one exists

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SonrisasTypography,
        shapes = SonrisasShapes,
        content = content,
    )
}
