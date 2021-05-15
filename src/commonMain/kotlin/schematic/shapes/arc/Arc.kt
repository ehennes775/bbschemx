package schematic.shapes.arc

import schematic.Item
import schematic.Writer
import schematic.types.ColorItem
import schematic.types.LineItem
import schematic.types.LineStyle

class Arc(
    val centerX: Int = 0,
    val centerY: Int = 0,
    val radius: Int = 100,
    val startAngle: Int = 0,
    val sweepAngle: Int = 180,
    override val color: Int,
    override val lineStyle: LineStyle = LineStyle()
) : Item, ColorItem, LineItem {

    fun withCenterX(newCenterX: Int) = Arc(
        newCenterX,
        centerY,
        radius,
        startAngle,
        sweepAngle,
        color,
        lineStyle
    )

    fun withCenterY(newCenterY: Int) = Arc(
        centerX,
        newCenterY,
        radius,
        startAngle,
        sweepAngle,
        color,
        lineStyle
    )

    fun withRadius(newRadius: Int) = Arc(
        centerX,
        centerY,
        newRadius,
        startAngle,
        sweepAngle,
        color,
        lineStyle
    )

    fun withStartAngle(newStartAngle: Int) = Arc(
        centerX,
        centerY,
        radius,
        newStartAngle,
        sweepAngle,
        color,
        lineStyle
    )

    fun withSweepAngle(newSweepAngle: Int) = Arc(
        centerX,
        centerY,
        radius,
        startAngle,
        newSweepAngle,
        color,
        lineStyle
    )

    override fun withColor(newColor: Int) = Arc(
        centerX,
        centerY,
        radius,
        startAngle,
        sweepAngle,
        newColor,
        lineStyle
    )

    override fun withLineStyle(newLineStyle: LineStyle) = Arc(
        centerX,
        centerY,
        radius,
        startAngle,
        sweepAngle,
        color,
        newLineStyle
    )

    companion object {
        const val TOKEN = "A"
    }

    override fun write(writer: Writer) = writer.writeParams(
        TOKEN,
        centerX.toString(),
        centerY.toString(),
        radius.toString(),
        startAngle.toString(),
        sweepAngle.toString(),
        color.toString(),
        lineStyle.lineWidthFileValue.toString(),
        lineStyle.dashTypeFileValue.toString(),
        lineStyle.dashLengthFileValue.toString(),
        lineStyle.dashSpaceFileValue.toString(),
        lineStyle.capTypeFileValue.toString()
    )
}
