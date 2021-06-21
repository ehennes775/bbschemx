package models.schematic.types

class LineStyle(
    val lineWidth: Int = DEFAULT_WIDTH,
    val dashType: DashType = DashType.SOLID,
    val dashLength: Int = DEFAULT_LENGTH,
    val dashSpace: Int = DEFAULT_SPACE,
    val capType: CapType = CapType.NONE
) {
    fun withValues(
        newLineWidth: Int = lineWidth,
        newDashType: DashType = dashType,
        newDashLength: Int = dashLength,
        newDashSpace: Int = dashSpace,
        newCapType: CapType = capType
    ) = LineStyle(newLineWidth, newDashType, newDashLength, newDashSpace, newCapType)

    val lineWidthFileValue get() = lineWidth

    val dashTypeFileValue get() = dashType.fileValue

    val dashLengthFileValue get() = if (dashType.usesLength) dashLength else UNUSED_VALUE

    val dashSpaceFileValue get() = if (dashType.usesSpace) dashSpace else UNUSED_VALUE

    val capTypeFileValue get() = capType.fileValue

    companion object {

        private const val DEFAULT_WIDTH = 10

        private const val DEFAULT_SPACE = 100

        private const val DEFAULT_LENGTH = 100

        private const val UNUSED_VALUE = -1

        fun fromFileParams(
            lineWidth: String,
            dashType: String,
            dashLength: String,
            dashSpace: String,
            capType: String
        ) = LineStyle(
            fromFileParam(lineWidth, DEFAULT_WIDTH),
            DashType.fromFileValue(dashType.toInt()),
            fromFileParam(dashLength, DEFAULT_LENGTH),
            fromFileParam(dashSpace, DEFAULT_SPACE),
            CapType.fromFileValue(capType.toInt())
        )

        private fun fromFileParam(fileParam: String, defaultValue: Int) = fileParam.toIntOrNull().let {
            if ((it != null) && it > 0) it else defaultValue
        }
    }
}