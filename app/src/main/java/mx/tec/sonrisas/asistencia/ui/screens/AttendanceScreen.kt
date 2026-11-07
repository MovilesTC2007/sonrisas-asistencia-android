package mx.tec.sonrisas.asistencia.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sonrisas.asistencia.ui.theme.AccentGreen
import mx.tec.sonrisas.asistencia.ui.theme.AccentOrange
import mx.tec.sonrisas.asistencia.ui.theme.AccentRed
import mx.tec.sonrisas.asistencia.ui.theme.AccentYellow
import mx.tec.sonrisas.asistencia.ui.theme.BgCream
import mx.tec.sonrisas.asistencia.ui.theme.BrandPurple
import mx.tec.sonrisas.asistencia.ui.theme.InkPrimary
import mx.tec.sonrisas.asistencia.ui.theme.InkSoft
import mx.tec.sonrisas.asistencia.ui.theme.LineColor
import mx.tec.sonrisas.asistencia.ui.theme.RadiusLg
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasAsistenciasTheme
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTextStyle
import mx.tec.sonrisas.asistencia.ui.theme.SurfaceLilac
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import mx.tec.sonrisas.asistencia.ui.theme.Spacing
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.outlined.WifiOff
import mx.tec.sonrisas.asistencia.ui.theme.SonrisasTypography
import mx.tec.sonrisas.asistencia.ui.theme.SurfacePeach

enum class ShiftStatus { NotClockedIn, OnShift }

enum class RecordStatus { OnTime, MinorDelay, MajorDelay, Absent }

data class AttendanceRecord(
    val title: String,
    val detail: String,
    val status: RecordStatus,
)

@Composable
fun AttendanceScreen(
    userName: String,
    shiftSchedule: String,
    shiftRole: String,
    shiftStatus: ShiftStatus,
    recentRecords: List<AttendanceRecord>,
    onClockActionClick: () -> Unit,
    isOnline: Boolean = true,
    pendingRecordsCount: Int = 0,
    modifier: Modifier = Modifier,
) {
    val visibleRecords = recentRecords.take(5)

    Scaffold(
        modifier = modifier,
        containerColor = BgCream,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.xxl),
        ) {
            Spacer(modifier = Modifier.height(Spacing.xxl))
            Text(
                text = "Hola, $userName",
                style = SonrisasTypography.displayLarge,
                color = InkPrimary,
            )
            Spacer(modifier = Modifier.height(Spacing.xl))

            if (!isOnline) {
                OfflineBanner(pendingRecordsCount = pendingRecordsCount)
                Spacer(modifier = Modifier.height(Spacing.md))
            }

            TodayShiftCard(schedule = shiftSchedule, role = shiftRole)
            Spacer(modifier = Modifier.height(Spacing.xxl))
            ClockActionButton(
                shiftStatus = shiftStatus,
                onClick = onClockActionClick,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            Spacer(modifier = Modifier.height(Spacing.md))
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = AccentGreen,
                    modifier = Modifier.size(20.dp),
                )
                Text(
                    text = "Estás en la estancia",
                    style = SonrisasTypography.titleMedium,
                    color = InkSoft,
                )
            }
            Spacer(modifier = Modifier.height(Spacing.xxl))
            Text(
                text = "Últimos 5 registros",
                style = SonrisasTypography.titleLarge,
                color = InkPrimary,
            )
            Spacer(modifier = Modifier.height(Spacing.sm))
            visibleRecords.forEachIndexed { index, record ->
                RecordRow(record = record)
                if (index<visibleRecords.lastIndex) {
                    HorizontalDivider(color = LineColor)
                }
            }
            Spacer(modifier = Modifier.height(Spacing.xxl))
        }
    }
}

@Composable
private fun OfflineBanner(
    pendingRecordsCount: Int,
    modifier: Modifier = Modifier,
) {
    val message = when (pendingRecordsCount) {
        0 -> "Sin conexión · Tus registros se enviarán al reconectar"
        1 -> "Sin conexión · 1 registro se enviará al reconectar"
        else -> "Sin conexión · $pendingRecordsCount registros se enviarán al reconectar"
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfacePeach)
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.md),
    ) {
        Icon(
            imageVector = Icons.Outlined.WifiOff,
            contentDescription = null,
            tint = AccentOrange,
            modifier = Modifier.size(20.dp),
        )
        Text(
            text = message,
            style = SonrisasTextStyle.Body,
            color = InkPrimary,
            modifier = Modifier.weight(1f),
        )
    }
}
@Composable
private fun TodayShiftCard(
    schedule: String,
    role: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RadiusLg)
            .background(SurfaceLilac)
            .padding(Spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "TU TURNO DE HOY",
                style = SonrisasTextStyle.Label,
                color = BrandPurple,
            )
            Spacer(modifier = Modifier.height(Spacing.xs))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    tint = BrandPurple,
                    modifier = Modifier.size(16.dp),
                )
                Spacer(modifier = Modifier.size(Spacing.sm))
                Text(
                    text = schedule,
                    style = SonrisasTextStyle.Title,
                    color = InkPrimary,
                )
            }
        }
        Text(
            text = role,
            style = SonrisasTextStyle.Caption,
            color = InkSoft,
            modifier = Modifier.weight(0.8f),
        )
    }
}

@Composable
private fun ClockActionButton(
    shiftStatus: ShiftStatus,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isClockIn = shiftStatus==ShiftStatus.NotClockedIn
    val icon = if (isClockIn) Icons.AutoMirrored.Filled.Login else Icons.AutoMirrored.Filled.Logout
    val text = if (isClockIn) "Registrar entrada" else "Registrar salida"

    Column(
        modifier = modifier
            .size(150.dp)
            .shadow(
                elevation = 24.dp,
                shape = CircleShape,
                ambientColor = BrandPurple.copy(alpha = 0.35f),
                spotColor = BrandPurple.copy(alpha = 0.45f),
            )
            .clip(CircleShape)
            .background(BrandPurple)
            .clickable(onClick = onClick)
            .padding(Spacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(28.dp),
        )
        Spacer(modifier = Modifier.height(Spacing.sm))
        Text(
            text = text,
            style = SonrisasTextStyle.Button.copy(fontWeight = FontWeight.Bold),
            color = Color.White,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun RecordRow(
    record: AttendanceRecord,
    modifier: Modifier = Modifier,
) {
    val statusColor = when (record.status) {
        RecordStatus.OnTime -> AccentGreen
        RecordStatus.MinorDelay -> AccentYellow
        RecordStatus.MajorDelay -> AccentOrange
        RecordStatus.Absent -> AccentRed
    }
    val statusIcon = when (record.status) {
        RecordStatus.OnTime -> Icons.Filled.Check
        RecordStatus.Absent -> Icons.Outlined.Cancel
        else -> Icons.Outlined.ErrorOutline
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = record.title,
                style = SonrisasTypography.bodyLarge,
                color = InkPrimary,
            )
            Text(
                text = record.detail,
                style = SonrisasTypography.bodyMedium,
                color = InkSoft,
            )
        }
        Icon(
            imageVector = statusIcon,
            contentDescription = null,
            tint = statusColor,
            modifier = Modifier.size(20.dp),
        )
    }
}


private val sampleClockInRecords = listOf(
    AttendanceRecord("Mié 7 oct", "7:58 – 16:02", RecordStatus.OnTime),
    AttendanceRecord("Mar 6 oct", "8:08 – 16:01", RecordStatus.MinorDelay),
    AttendanceRecord("Lun 5 oct", "7:55 – 16:00", RecordStatus.OnTime),
    AttendanceRecord("Vie 2 oct", "Sin registro", RecordStatus.Absent),
    AttendanceRecord("Jue 1 oct", "7:57 – 16:00", RecordStatus.OnTime),
)

private val sampleClockOutRecords = listOf(
    AttendanceRecord("Hoy · 8 oct", "Entrada 7:55 · En turno", RecordStatus.OnTime),
    AttendanceRecord("Mié 7 oct", "7:58 – 16:02", RecordStatus.OnTime),
    AttendanceRecord("Mar 6 oct", "8:08 – 16:01", RecordStatus.MinorDelay),
    AttendanceRecord("Lun 5 oct", "7:56 – 16:00", RecordStatus.OnTime),
    AttendanceRecord("Vie 2 oct", "Sin registro", RecordStatus.Absent),
)

@Preview(showBackground = true, heightDp = 780)
@Composable
private fun ClockInPreview() {
    SonrisasAsistenciasTheme {
        AttendanceScreen(
            userName = "Fernando",
            shiftSchedule = "8:00 – 16:00",
            shiftRole = "Maestra · Lactantes",
            shiftStatus = ShiftStatus.NotClockedIn,
            recentRecords = sampleClockInRecords,
            onClockActionClick = {},
        )
    }
}

@Preview(showBackground = true, heightDp = 780)
@Composable
private fun ClockOutPreview() {
    SonrisasAsistenciasTheme {
        AttendanceScreen(
            userName = "Fernando",
            shiftSchedule = "8:00 – 16:00",
            shiftRole = "Maestra · Lactantes",
            shiftStatus = ShiftStatus.OnShift,
            recentRecords = sampleClockOutRecords,
            onClockActionClick = {},
        )
    }
}

@Preview(showBackground = true, heightDp = 780)
@Composable
private fun OfflinePreview() {
    SonrisasAsistenciasTheme {
        AttendanceScreen(
            userName = "Fernando",
            shiftSchedule = "8:00 – 16:00",
            shiftRole = "Maestra · Lactantes",
            shiftStatus = ShiftStatus.NotClockedIn,
            recentRecords = sampleClockInRecords,
            onClockActionClick = {},
            isOnline = false,
            pendingRecordsCount = 3,
        )
    }
}