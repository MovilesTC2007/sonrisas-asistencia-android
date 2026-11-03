package mx.tec.sonrisas.asistencia.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// "Suave" preset from docs/identidad-de-marca.md — radio-de-forma scale.
val RadiusSm = RoundedCornerShape(10.dp)
val RadiusMd = RoundedCornerShape(16.dp)
val RadiusLg = RoundedCornerShape(22.dp)
val RadiusPill = RoundedCornerShape(percent = 50)

val SonrisasShapes = Shapes(
    extraSmall = RadiusSm,
    small = RadiusMd,
    medium = RadiusLg,
    large = RadiusLg,
    extraLarge = RadiusPill,
)
