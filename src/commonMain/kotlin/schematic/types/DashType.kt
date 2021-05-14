package schematic.types

enum class DashType(val fileValue: Int, val usesLength: Boolean, val usesSpace: Boolean) {
    SOLID(
        fileValue = 0,
        usesLength = false,
        usesSpace = false
    ),
    DOTTED(
        fileValue = 1,
        usesLength = false,
        usesSpace = true
    ),
    DASHED(
        fileValue = 2,
        usesLength = true,
        usesSpace = true
    ),
    CENTER(
        fileValue = 3,
        usesLength = true,
        usesSpace = true
    ),
    PHANTOM(
        fileValue = 4,
        usesLength = true,
        usesSpace = true
    );

    companion object {
        private val VALUES = CapType.values()

        fun fromFileValue(fileValue: Int) = VALUES.firstOrNull { it.fileValue == fileValue } ?: throw Exception()
    }
}