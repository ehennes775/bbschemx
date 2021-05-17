package models.schematic.types

enum class FillType(
    val fileValue: Int,
    val usesFillWidth: Boolean,
    val usesFirstSet: Boolean,
    val usesSecondSet: Boolean
) {
    HOLLOW(
        fileValue = 0,
        usesFillWidth = false,
        usesFirstSet = false,
        usesSecondSet = false
    ),
    FILL(
        fileValue = 1,
        usesFillWidth = false,
        usesFirstSet = false,
        usesSecondSet = false
    ),
    MESH(
        fileValue = 2,
        usesFillWidth = true,
        usesFirstSet = true,
        usesSecondSet = true
    ),
    HATCH(
        fileValue = 3,
        usesFillWidth = true,
        usesFirstSet = true,
        usesSecondSet = false
    ),
    VOID(
        fileValue = 4,
        usesFillWidth = false,
        usesFirstSet = false,
        usesSecondSet = false
    );

    companion object {
        private val VALUES = values()

        fun fromFileValue(fileValue: Int) = VALUES.firstOrNull { it.fileValue == fileValue } ?: throw Exception()
    }
}