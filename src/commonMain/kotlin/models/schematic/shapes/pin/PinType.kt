package models.schematic.shapes.pin

enum class PinType(val fileValue: Int) {
    NET(0),
    BUS(1);

    companion object {
        private val VALUES = values()

        fun fromFileValue(fileValue: Int) = VALUES.firstOrNull { it.fileValue == fileValue } ?: throw Exception()
    }
}