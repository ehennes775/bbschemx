package schematic.shapes.circle

import schematic.Item
import schematic.types.*

class Circle(
    val centerX: Int = 0,
    val centerY: Int = 0,
    val radius: Int = 100,
    override val color: Int,
    override val lineStyle: LineStyle = LineStyle(),
    override val fillStyle: FillStyle = FillStyle()
) : Item, ColorItem, LineItem, FillItem {
    fun withCenterX(newCenterX: Int) = Circle(
        newCenterX,
        centerY,
        radius,
        color,
        lineStyle,
        fillStyle
    )

    fun withCenterY(newCenterY: Int) = Circle(
        centerX,
        newCenterY,
        radius,
        color,
        lineStyle,
        fillStyle
    )

    fun withRadius(newRadius: Int) = Circle(
        centerX,
        centerY,
        newRadius,
        color,
        lineStyle,
        fillStyle
    )

    override fun withColor(newColor: Int) = Circle(
        centerX,
        centerY,
        radius,
        newColor,
        lineStyle,
        fillStyle
    )

    override fun withLineStyle(newLineStyle: LineStyle) = Circle(
        centerX,
        centerY,
        radius,
        color,
        newLineStyle,
        fillStyle
    )

    override fun withFillStyle(newFillStyle: FillStyle) = Circle(
        centerX,
        centerY,
        radius,
        color,
        lineStyle,
        newFillStyle
    )
}