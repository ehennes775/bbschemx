package views.schematic.types

class LineStyle(
    val lineWidth: Int = 10,
    val dashType: DashType = DashType.SOLID,
    val dashLength: Int = 100,
    val dashSpace: Int = 100,
    val capType: CapType = CapType.NONE
) {
    val lineWidthFileValue get() = lineWidth

    val dashTypeFileValue get() = dashType.fileValue

    val dashLengthFileValue get() = if (dashType.usesLength) dashLength else -1

    val dashSpaceFileValue get() = if (dashType.usesSpace) dashSpace else -1

    val capTypeFileValue get() = capType.fileValue

    fun withLineWidth(newLineWidth: Int) = LineStyle(
        newLineWidth,
        dashType,
        dashLength,
        dashSpace,
        capType
    )

    fun withDashType(newDashType: DashType) = LineStyle(
        lineWidth,
        newDashType,
        dashLength,
        dashSpace,
        capType
    )

    fun withDashLength(newDashLength: Int) = LineStyle(
        lineWidth,
        dashType,
        newDashLength,
        dashSpace,
        capType
    )

    fun withDashSpace(newDashSpace: Int) = LineStyle(
        lineWidth,
        dashType,
        dashLength,
        newDashSpace,
        capType
    )

    fun withCapType(newCapType: CapType) = LineStyle(
        lineWidth,
        dashType,
        dashLength,
        dashSpace,
        newCapType
    )
}