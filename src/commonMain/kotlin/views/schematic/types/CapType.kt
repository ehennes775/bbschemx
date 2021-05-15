package views.schematic.types

enum class CapType(val fileValue: Int) {
    NONE(0),
    SQUARE(1),
    ROUND(2);

    companion object {
        private val VALUES = values()

        fun fromFileValue(fileValue: Int) = VALUES.firstOrNull { it.fileValue == fileValue } ?: throw Exception("Invalid CapType '$fileValue'")
    }
}