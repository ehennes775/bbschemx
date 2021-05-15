package schematic.shapes.pin

import schematic.types.CapType

enum class PinType(val fileValue: Int) {
    NET(0),
    BUS(1);

    companion object {
        private val VALUES = CapType.values()

        fun fromFileValue(fileValue: Int) = VALUES.firstOrNull { it.fileValue == fileValue } ?: throw Exception()
    }
}