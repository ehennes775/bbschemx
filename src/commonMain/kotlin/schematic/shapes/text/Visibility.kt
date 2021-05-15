package schematic.shapes.text

import schematic.types.CapType

enum class Visibility(val fileValue: Int) {
    INVISIBLE(0),
    VISIBLE(1);

    companion object {
        private val VALUES = CapType.values()

        fun fromFileValue(fileValue: Int) = VALUES.firstOrNull { it.fileValue == fileValue } ?: throw Exception()
    }
}