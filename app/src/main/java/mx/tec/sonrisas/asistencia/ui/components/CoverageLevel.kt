package mx.tec.sonrisas.asistencia.ui.components

// Regla para cobertura de estancia
enum class CoverageLevel { Complete, Partial, Critical }

private const val CRITICAL_THRESHOLD = 0.7f

fun coverageLevel(present: Int, required: Int): CoverageLevel = when {
    present >= required -> CoverageLevel.Complete
    present.toFloat() / required < CRITICAL_THRESHOLD -> CoverageLevel.Critical
    else -> CoverageLevel.Partial
}
