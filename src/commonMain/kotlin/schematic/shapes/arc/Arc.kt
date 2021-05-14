package schematic.shapes.arc

import schematic.Item
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
}