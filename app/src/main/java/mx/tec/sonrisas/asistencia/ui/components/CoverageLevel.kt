package mx.tec.sonrisas.asistencia.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import mx.tec.sonrisas.asistencia.ui.theme.AccentGreen
import mx.tec.sonrisas.asistencia.ui.theme.AccentOrange
import mx.tec.sonrisas.asistencia.ui.theme.AccentRed

// Regla final de cobertura
enum class CoverageLevel { Complete, Partial, Critical }

private const val CRITICAL_THRESHOLD = 0.7f

fun coverageLevel(present: Int, required: Int): CoverageLevel = when {
    present >= required -> CoverageLevel.Complete
    present.toFloat() / required < CRITICAL_THRESHOLD -> CoverageLevel.Critical
    else -> CoverageLevel.Partial
}

val CoverageLevel.icon: ImageVector
    get() = if (this == CoverageLevel.Complete) Icons.Outlined.CheckCircle else Icons.Outlined.ErrorOutline

val CoverageLevel.tint: Color
    get() = when (this) {
        CoverageLevel.Complete -> AccentGreen
        CoverageLevel.Partial -> AccentOrange
        CoverageLevel.Critical -> AccentRed
    }

val CoverageLevel.description: String
    get() = when (this) {
        CoverageLevel.Complete -> "cobertura completa"
        CoverageLevel.Partial -> "cobertura incompleta"
        CoverageLevel.Critical -> "cobertura crítica"
    }
