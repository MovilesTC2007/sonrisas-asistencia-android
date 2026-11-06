package mx.tec.sonrisas.asistencia.ui.ui.theme

import mx.tec.sonrisas.asistencia.R
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
 val PoppinsFamily = FontFamily(
     Font(R.font.poppins_semibold, FontWeight.SemiBold),
     Font(R.font.poppins_bold, FontWeight.Bold),
 )
val PlusJakartaSansFamily = FontFamily(
     Font(R.font.plusjakartasans_regular, FontWeight.Normal),
     Font(R.font.plusjakartasans_medium, FontWeight.Medium),
     Font(R.font.plusjakartasans_semibold, FontWeight.SemiBold),
)

private fun display(size: Int, weight: FontWeight = FontWeight.Bold) = TextStyle(
    fontFamily = PoppinsFamily,
    fontWeight = weight,
    fontSize = size.sp,
    lineHeight = (size * 1.22).sp,
    letterSpacing = (-0.01 * size).sp,
)

private fun title(size: Int) = TextStyle(
    fontFamily = PoppinsFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = size.sp,
    lineHeight = (size * 1.2).sp,
)

private fun body(size: Double) = TextStyle(
    fontFamily = PlusJakartaSansFamily,
    fontWeight = FontWeight.Medium,
    fontSize = size.sp,
    lineHeight = (size * 1.35).sp,
)

private fun label(size: Double) = TextStyle(
    fontFamily = PlusJakartaSansFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = size.sp,
    lineHeight = (size * 1.3).sp,
    letterSpacing = (0.02 * size).sp,
)

object SonrisasTextStyle {
    val Display = display(23)
    val Title = title(17)
    val Button = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.5.sp,
        lineHeight = 18.sp,
    )
    val Body = body(14.5)
    val Label = label(11.5)
    val Caption = body(12.0)
}

val SonrisasTypography = Typography(
    displayLarge = display(32),
    displayMedium = display(26),
    displaySmall = SonrisasTextStyle.Display,
    headlineLarge = display(24, FontWeight.SemiBold),
    headlineMedium = display(22, FontWeight.SemiBold),
    headlineSmall = display(20, FontWeight.SemiBold),
    titleLarge = SonrisasTextStyle.Title,
    titleMedium = title(15),
    titleSmall = title(13),
    bodyLarge = body(15.0),
    bodyMedium = SonrisasTextStyle.Body,
    bodySmall = SonrisasTextStyle.Caption,
    labelLarge = SonrisasTextStyle.Button,
    labelMedium = label(11.5),
    labelSmall = label(10.5),
)
