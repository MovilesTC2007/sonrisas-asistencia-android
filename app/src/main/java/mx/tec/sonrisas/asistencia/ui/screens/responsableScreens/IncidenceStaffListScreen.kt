package mx.tec.sonrisas.asistencia.ui.screens.responsableScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.components.AttendanceStatus
import mx.tec.sonrisas.asistencia.ui.components.SonrisasAvatar
import mx.tec.sonrisas.asistencia.ui.components.SonrisasListCard
import mx.tec.sonrisas.asistencia.ui.components.SonrisasStatusChip
import mx.tec.sonrisas.asistencia.ui.components.SonrisasTopBar
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.Spacing

// Paso previo a "Registrar incidencia": el Responsable elige a quién registrar.
// Arriba van quienes aún no se registran hoy y los retardos; abajo el resto, para otra incidencia.

data class IncidenceCandidate(
    val id: Int,
    val name: String,
    val detail: String,
    val assignedTime: String,
    val status: AttendanceStatus,
    val arrivalTime: String? = null,
)

@Composable
fun IncidenceStaffListScreen(
    candidates: List<IncidenceCandidate>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onPersonClick: (Int) -> Unit = {},
) {
    val absent = candidates.filter { it.status == AttendanceStatus.Absent }
    val delayed = candidates.filter {
        it.status == AttendanceStatus.MinorDelay || it.status == AttendanceStatus.MajorDelay
    }
    val others = candidates.filter { it.status == AttendanceStatus.OnTime }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgCream)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.xl),
    ) {
        Spacer(Modifier.height(Spacing.md))
        SonrisasTopBar(title = "Registrar incidencia", onBack = onBack)
        Spacer(Modifier.height(Spacing.sm))
        Text(
            text = "Elige a la persona a la que le vas a registrar la incidencia.",
            style = SonrisasTextStyle.Body,
            color = InkSoft,
            modifier = Modifier.padding(horizontal = Spacing.xs),
        )

        CandidateSection("Sin registro hoy", absent, onPersonClick)
        CandidateSection("Con retardo", delayed, onPersonClick)
        CandidateSection("Otro personal", others, onPersonClick)
        Spacer(Modifier.height(Spacing.xxl))
    }
}

@Composable
private fun CandidateSection(
    title: String,
    people: List<IncidenceCandidate>,
    onPersonClick: (Int) -> Unit,
) {
    if (people.isEmpty()) return
    Spacer(Modifier.height(Spacing.xl))
    Text(
        text = "$title (${people.size})",
        style = SonrisasTextStyle.Title,
        color = InkPrimary,
        modifier = Modifier
            .padding(horizontal = Spacing.xs)
            .semantics { heading() },
    )
    Spacer(Modifier.height(Spacing.md))
    SonrisasListCard(items = people) { person ->
        CandidateRow(person = person, onClick = { onPersonClick(person.id) })
    }
}

@Composable
private fun CandidateRow(
    person: IncidenceCandidate,
    onClick: () -> Unit,
) {
    val note = when {
        person.arrivalTime != null -> "Entrada ${person.assignedTime} · llegó ${person.arrivalTime}"
        person.status == AttendanceStatus.Absent -> "Entrada ${person.assignedTime} · sin registro"
        else -> "Entrada ${person.assignedTime}"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClickLabel = "Registrar incidencia a ${person.name}", onClick = onClick)
            .padding(horizontal = Spacing.lg, vertical = Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SonrisasAvatar(name = person.name, size = 44.dp)
        Spacer(Modifier.width(Spacing.md))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = person.name,
                style = SonrisasTextStyle.Body,
                color = InkPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = person.detail,
                style = SonrisasTextStyle.Caption,
                color = InkSoft,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = note,
                style = SonrisasTextStyle.Caption,
                color = InkSoft,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(Modifier.width(Spacing.md))
        SonrisasStatusChip(
            text = person.status.label,
            tint = person.status.tint,
            ink = person.status.ink,
        )
    }
}

@Preview(name = "Elegir persona", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun IncidenceStaffListScreenPreview() {
    SonrisasAsistenciasTheme {
        IncidenceStaffListScreen(
            candidates = listOf(
                IncidenceCandidate(1, "Sofía Ramírez Cruz", "Maestra · Maternales", "8:00", AttendanceStatus.Absent),
                IncidenceCandidate(
                    2,
                    "Rosa Pérez García",
                    "Auxiliar · Lactantes",
                    "8:00",
                    AttendanceStatus.MajorDelay,
                    "8:22",
                ),
                IncidenceCandidate(
                    3,
                    "Ana Martínez López",
                    "Maestra · Lactantes",
                    "8:00",
                    AttendanceStatus.OnTime,
                    "7:55",
                ),
            ),
            onBack = {},
        )
    }
}
