package mx.tec.sonrisas.asistencia.ui.navigation

// Horas en texto "H:mm" (formato de 24 horas) para las pantallas con selector de hora.

internal const val MINUTES_PER_HOUR = 60

// "8:22" -> 502 minutos desde la medianoche.
internal fun timeToMinutes(time: String): Int {
    val parts = time.split(":")
    val hour = parts.getOrNull(0)?.toIntOrNull() ?: 0
    val minute = parts.getOrNull(1)?.toIntOrNull() ?: 0
    return hour * MINUTES_PER_HOUR + minute
}

// (8, 5) -> "8:05"
internal fun formatTime(hour: Int, minute: Int): String = "$hour:${minute.toString().padStart(2, '0')}"
