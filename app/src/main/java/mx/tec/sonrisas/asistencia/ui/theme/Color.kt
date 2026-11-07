package mx.tec.sonrisas.asistencia.ui.theme

import androidx.compose.ui.graphics.Color

val BrandPurple = Color(0xFF6C4FD1)
val BrandPurpleDeep = Color(0xFF241B3D)
val BrandPurpleLight = Color(0xFFEDE7FB)
val BrandPurpleDeepText = Color(0xFF5A3FC4)

// Surfaces
val BgCream = Color(0xFFFBF6ED)
val SurfaceWhite = Color(0xFFFFFFFF)
val SurfacePeach = Color(0xFFFFE7CE)
val SurfaceLilac = Color(0xFFE7E0FB)
val LineColor = Color(0xFFECE6DA)

// Ink
val InkPrimary = Color(0xFF241F33)
val InkSoft = Color(0xFF6E6680)
val InkFaint = Color(0xFF8B839C)

// Status accents — pulled from the five stars in the institutional logo.
// These carry meaning (on-time / delayed / absent), not decoration — never
// repurpose them as generic UI color.
val AccentGreen = Color(0xFF2FBE86)
val AccentYellow = Color(0xFFFFC83D)
val AccentOrange = Color(0xFFFF9F43)
val AccentRed = Color(0xFFFF6B6B)
val AccentBlue = Color(0xFF3DA9FC)

// Status tints & inks — derived from the accents above for status summary
// surfaces (e.g. the cards in Mis asistencias). Tint = accent heavily
// lightened, used as background; Ink = accent darkened so text on its tint
// meets AA contrast. Same rule as the accents: only where color means status.
val StatusGreenTint = Color(0xFFE9F7F0)
val StatusGreenInk = Color(0xFF1D7A54)
val StatusYellowTint = Color(0xFFFFF6DC)
val StatusYellowInk = Color(0xFF8A6410)
val StatusRedTint = Color(0xFFFFECEC)
val StatusRedInk = Color(0xFFB0383A)