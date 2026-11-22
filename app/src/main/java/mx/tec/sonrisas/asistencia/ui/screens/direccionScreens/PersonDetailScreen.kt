package mx.tec.sonrisas.asistencia.ui.screens.direccionScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import mx.tec.sonrisas.asistencia.ui.components.AttendanceStatus
import mx.tec.sonrisas.asistencia.ui.components.SonrisasBackStyle
import mx.tec.sonrisas.asistencia.ui.components.SonrisasInfoCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasPersonCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasStatCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasStatusChip
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

data class PersonRecord(
    val dateLabel: String,
    val detail: String,
    val status: AttendanceStatus,
)

data class PersonDetail(
    val id: Int,
    val name: String,
    val role: String,
    val estancia: String,
    val area: String,
    val todayStatus: AttendanceStatus,
    val todayDetail: String,
    val month: String,
    val onTimeCount: Int,
    val delayCount: Int,
    val absenceCount: Int,
    val records: List<PersonRecord>,
)

@Composable
fun PersonDetailScreen(
    person: PersonDetail,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgCream)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.xl),
    ) {
        Spacer(Modifier.height(Spacing.md))
        SonrisasTopBar(title = "Detalle de persona", onBack = onBack, backStyle = SonrisasBackStyle.Plain)
        Spacer(Modifier.height(Spacing.lg))
        SonrisasPersonCard(
            name = person.name,
            detail = "${person.role} · ${person.estancia} · ${person.area}",
        )
        Spacer(Modifier.height(Spacing.lg))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            SonrisasStatusChip(
                text = person.todayStatus.label,
                tint = person.todayStatus.tint,
                ink = person.todayStatus.ink,
            )
            Text(
                text = "Hoy · ${person.todayDetail}",
                style = SonrisasTextStyle.Body,
                color = InkSoft,
            )
        }
        Spacer(Modifier.height(Spacing.xl))
        SectionLabel(text = person.month)
        Spacer(Modifier.height(Spacing.sm))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
        ) {
            SonrisasStatCard(
                value = person.onTimeCount,
                label = "A tiempo",
                status = AttendanceStatus.OnTime,
                modifier = Modifier.weight(1f),
            )
            SonrisasStatCard(
                value = person.delayCount,
                label = "Retardos",
                status = AttendanceStatus.MinorDelay,
                modifier = Modifier.weight(1f),
            )
            SonrisasStatCard(
                value = person.absenceCount,
                label = "Faltas",
                status = AttendanceStatus.Absent,
                modifier = Modifier.weight(1f),
            )
        }
        Spacer(Modifier.height(Spacing.xl))
        SectionLabel(text = "Últimos registros")
        Spacer(Modifier.height(Spacing.sm))
        SonrisasListCard(items = person.records) { record -> RecordRow(record = record) }
        Spacer(Modifier.height(Spacing.xl))
        SonrisasInfoCard(
            message = "Solo lectura. Los cambios los hace la persona responsable de la estancia.",
            icon = Icons.Outlined.Lock,
        )
        Spacer(Modifier.height(Spacing.xxl))
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        style = SonrisasTextStyle.Body.copy(fontWeight = FontWeight.SemiBold),
        color = InkPrimary,
        modifier = Modifier
            .padding(horizontal = Spacing.xs)
            .semantics { heading() },
    )
}

@Composable
private fun RecordRow(record: PersonRecord) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = record.dateLabel,
                style = SonrisasTextStyle.Body,
                color = InkPrimary,
            )
            Text(
                text = record.detail,
                style = SonrisasTextStyle.Caption,
                color = InkSoft,
            )
        }
        SonrisasStatusChip(
            text = record.status.label,
            tint = record.status.tint,
            ink = record.status.ink,
        )
    }
}

@Preview(name = "Detalle de persona", showBackground = true, widthDp = 360, heightDp = 1000)
@Composable
private fun PersonDetailScreenPreview() {
    SonrisasAsistenciasTheme {
        PersonDetailScreen(
            person = PersonDetail(
                id = 2,
                name = "Rosa Pérez",
                role = "Maestra",
                estancia = "Los Girasoles",
                area = "Lactantes",
                todayStatus = AttendanceStatus.MajorDelay,
                todayDetail = "Entrada 8:22",
                month = "Noviembre",
                onTimeCount = 12,
                delayCount = 2,
                absenceCount = 0,
                records = listOf(
                    PersonRecord("Lun 16 nov", "Entrada 7:55 · Salida 16:02", AttendanceStatus.OnTime),
                    PersonRecord("Vie 13 nov", "Entrada 8:12 · Salida 16:00", AttendanceStatus.MinorDelay),
                    PersonRecord("Mié 11 nov", "Sin registro", AttendanceStatus.Absent),
                ),
            ),
            onBack = {},
        )
    }
}
