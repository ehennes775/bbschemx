package schematic.shapes.text

import schematic.types.CapType

enum class Alignment(val fileValue: Int, val horizontal: Double, val vertical: Double) {
    LOWER_LEFT(
        fileValue = 0,
        horizontal = 0.0,
        vertical = 0.0
    ),
    CENTER_LEFT(
        fileValue = 1,
        horizontal = 0.0,
        vertical = 0.5
    ),
    UPPER_LEFT(
        fileValue = 2,
        horizontal = 0.0,
        vertical = 1.0
    ),
    LOWER_CENTER(
        fileValue = 3,
        horizontal = 0.5,
        vertical = 0.0
    ),
    CENTER_CENTER(
        fileValue = 4,
        horizontal = 0.5,
        vertical = 0.5
    ),
    UPPER_CENTER(
        fileValue = 5,
        horizontal = 0.5,
        vertical = 1.0
    ),
    LOWER_RIGHT(
        fileValue = 6,
        horizontal = 1.0,
        vertical = 0.0
    ),
    CENTER_RIGHT(
        fileValue = 7,
        horizontal = 1.0,
        vertical = 0.5
    ),
    UPPER_RIGHT(
        fileValue = 8,
        horizontal = 1.0,
        vertical = 1.0
    );

    companion object {
        private val VALUES = CapType.values()

        fun fromFileValue(fileValue: Int) = VALUES.firstOrNull { it.fileValue == fileValue } ?: throw Exception()
    }
}
