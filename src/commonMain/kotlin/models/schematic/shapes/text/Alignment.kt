package models.schematic.shapes.text

enum class Alignment(val fileValue: Int, val horizontal: Double, val vertical: Double) {
    LOWER_LEFT(
        fileValue = 0,
        horizontal = 0.0,
        vertical = 0.0
    ) {
        override val mirrorX get() = LOWER_RIGHT
    },
    CENTER_LEFT(
        fileValue = 1,
        horizontal = 0.0,
        vertical = 0.5
    ) {
        override val mirrorX get() = CENTER_RIGHT
    },
    UPPER_LEFT(
        fileValue = 2,
        horizontal = 0.0,
        vertical = 1.0
    ) {
        override val mirrorX get() = UPPER_RIGHT
    },
    LOWER_CENTER(
        fileValue = 3,
        horizontal = 0.5,
        vertical = 0.0
    ) {
        override val mirrorX get() = LOWER_CENTER
    },
    CENTER_CENTER(
        fileValue = 4,
        horizontal = 0.5,
        vertical = 0.5
    ) {
        override val mirrorX get() = CENTER_CENTER
    },
    UPPER_CENTER(
        fileValue = 5,
        horizontal = 0.5,
        vertical = 1.0
    ) {
        override val mirrorX get() = UPPER_CENTER
    },
    LOWER_RIGHT(
        fileValue = 6,
        horizontal = 1.0,
        vertical = 0.0
    ) {
        override val mirrorX get() = LOWER_LEFT
    },
    CENTER_RIGHT(
        fileValue = 7,
        horizontal = 1.0,
        vertical = 0.5
    ) {
        override val mirrorX get() = CENTER_LEFT
    },
    UPPER_RIGHT(
        fileValue = 8,
        horizontal = 1.0,
        vertical = 1.0
    ) {
        override val mirrorX get() = UPPER_LEFT
    };

    abstract val mirrorX: Alignment

    companion object {
        private val VALUES = values()

        fun fromFileValue(fileValue: Int) = VALUES.firstOrNull { it.fileValue == fileValue } ?: throw Exception()
    }
}
