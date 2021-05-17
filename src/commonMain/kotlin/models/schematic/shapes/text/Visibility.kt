package models.schematic.shapes.text

enum class Visibility(val fileValue: Int) {
    INVISIBLE(0),
    VISIBLE(1);

    companion object {
        private val VALUES = values()

        fun fromFileValue(fileValue: Int) = VALUES.firstOrNull { it.fileValue == fileValue } ?: throw Exception()
    }
}