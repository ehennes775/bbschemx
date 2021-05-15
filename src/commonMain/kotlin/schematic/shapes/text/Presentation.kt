package schematic.shapes.text

import schematic.types.CapType

enum class Presentation(val fileValue: Int) {
    NAME_VALUE(0),
    VALUE(1),
    NAME(2);

    companion object {
        private val VALUES = CapType.values()

        fun fromFileValue(fileValue: Int) = VALUES.firstOrNull { it.fileValue == fileValue } ?: throw Exception()
    }
}