package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleDeepText
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceWhite

@Composable
fun SonrisasAvatar(
    name: String,
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
    containerColor: Color = SurfaceWhite,
    contentColor: Color = BrandPurpleDeepText,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(containerColor)
            .clearAndSetSemantics {},
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = initialsOf(name),
            style = SonrisasTextStyle.Title,
            color = contentColor,
            maxLines = 1,
        )
    }
}

private fun initialsOf(name: String): String =
    name.trim()
        .split(Regex("\\s+"))
        .filter { it.isNotEmpty() }
        .take(2)
        .joinToString(separator = "") { it.first().uppercase() }

@Preview(showBackground = true, backgroundColor = 0xFFEDE7FB)
@Composable
private fun SonrisasAvatarPreview() {
    SonrisasAsistenciasTheme {
        Box(Modifier.padding(Spacing.lg)) {
            SonrisasAvatar(name = "Ana Martínez López")
        }
    }
}