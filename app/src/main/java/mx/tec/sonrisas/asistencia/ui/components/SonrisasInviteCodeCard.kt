package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeep
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceLilac
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

// tarjeta "isla" oscura (BrandPurpleDeep) con el codigo de invitacion en grande, centrado
// mismo tratamiento que la isla de Detalle de estancia: texto blanco y texto secundario en SurfaceLilac
private val InviteNameStyle = SonrisasTextStyle.Title.copy(fontSize = 20.sp, lineHeight = 26.sp)

// el codigo va en Poppins Bold con tracking amplio para que cada caracter se distinga (0 / O, 1 / I)
private val InviteCodeStyle = SonrisasTextStyle.Display.copy(
    fontSize = 32.sp,
    lineHeight = 40.sp,
    letterSpacing = 4.sp,
)

@Composable
fun SonrisasInviteCodeCard(
    name: String,
    detail: String,
    code: String,
    validity: String,
    modifier: Modifier = Modifier,
    eyebrow: String = "Para",
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(BrandPurpleDeep)
            .padding(horizontal = Spacing.xl, vertical = Spacing.xxl)
            .semantics(mergeDescendants = true) {},
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = eyebrow,
            style = SonrisasTextStyle.Body,
            color = SurfaceLilac,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(Spacing.xs))
        Text(
            text = name,
            style = InviteNameStyle,
            color = SurfaceWhite,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(Spacing.xs))
        Text(
            text = detail,
            style = SonrisasTextStyle.Body,
            color = SurfaceLilac,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(Spacing.xxl))
        Text(
            text = code,
            style = InviteCodeStyle,
            color = SurfaceWhite,
            textAlign = TextAlign.Center,
            // TalkBack lee el codigo caracter por caracter ("S C 4 K, 7 R 2 T") y no como una palabra
            modifier = Modifier.semantics { contentDescription = spelledOut(code) },
        )
        Spacer(Modifier.height(Spacing.lg))
        Text(
            text = validity,
            style = SonrisasTextStyle.Body,
            color = SurfaceLilac,
            textAlign = TextAlign.Center,
        )
    }
}
private fun spelledOut(code: String): String =
    code.trim()
        .split(Regex("\\s+"))
        .joinToString(separator = ", ") { group -> group.toList().joinToString(separator = " ") }

@Preview(showBackground = true, backgroundColor = 0xFFFBF6ED, widthDp = 360)
@Composable
private fun SonrisasInviteCodeCardPreview() {
    SonrisasAsistenciasTheme {
        SonrisasInviteCodeCard(
            name = "Patricia Gómez Ríos",
            detail = "Asistente · Maternales",
            code = "SC4K 7R2T",
            validity = "Un solo uso · vence el 16 de noviembre",
            modifier = Modifier.padding(Spacing.xl),
        )
    }
}
