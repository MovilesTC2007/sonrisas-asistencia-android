package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurpleLight
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.RadiusMd
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

//Cards anteriores tenian padding y valores diferentes (no respetaban la theme)
@Composable
fun SonrisasStatCard(
    value: Int,
    label: String,
    containerColor: Color,
    valueColor: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RadiusMd)
            .background(containerColor)
            .padding(Spacing.md)
            .semantics(mergeDescendants = true) {},
    ) {
        Text(
            text = value.toString(),
            style = SonrisasTextStyle.Stat,
            color = valueColor,
        )
        Spacer(Modifier.height(Spacing.xs))
        Text(
            text = label,
            style = SonrisasTextStyle.Caption,
            color = InkSoft,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

//cambio para tint (Utilizado para cambio de colores repetidos entre misma pantalla)
@Composable
fun SonrisasStatCard(
    value: Int,
    label: String,
    status: AttendanceStatus,
    modifier: Modifier = Modifier,
) {
    SonrisasStatCard(
        value = value,
        label = label,
        containerColor = status.tint,
        valueColor = status.ink,
        modifier = modifier,
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFBF6ED, widthDp = 360)
@Composable
private fun SonrisasStatCardPreview() {
    SonrisasAsistenciasTheme {
        Column(
            modifier = Modifier.padding(Spacing.xl),
            verticalArrangement = Arrangement.spacedBy(Spacing.sm),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            ) {
                SonrisasStatCard(17, "Presentes", AttendanceStatus.OnTime, Modifier.weight(1f))
                SonrisasStatCard(2, "Retardos", AttendanceStatus.MinorDelay, Modifier.weight(1f))
                SonrisasStatCard(1, "Faltas", AttendanceStatus.Absent, Modifier.weight(1f))
            }
            SonrisasStatCard(
                value = 84,
                label = "Personal total",
                containerColor = BrandPurpleLight,
                valueColor = BrandPurple,
            )
        }
    }
}
