package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeep
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg

// sombra suave y difusa de las tarjetas blancas (guia de elevacion): teñida de morado oscuro, nunca gris duro
// va ANTES del clip/background de la tarjeta, por eso se pasa como modifier desde fuera:
// SonrisasListCard(items, modifier = Modifier.sonrisasCardShadow()) { ... }
// el boton principal tiene su propia sombra morada (SonrisasPrimaryButton elevated), esta es solo para tarjetas
fun Modifier.sonrisasCardShadow(shape: Shape = RadiusLg): Modifier = shadow(
    elevation = 8.dp,
    shape = shape,
    ambientColor = BrandPurpleDeep.copy(alpha = 0.08f),
    spotColor = BrandPurpleDeep.copy(alpha = 0.12f),
)
