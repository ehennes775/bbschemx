package schematic.shapes.box

import schematic.Item
import schematic.types.*

class Box(
    val lowerX: Int = 0,
    val lowerY: Int = 0,
    val upperX: Int = 100,
    val upperY: Int = 100,
    override val color: Int,
    override val lineStyle: LineStyle = LineStyle(),
    override val fillStyle: FillStyle = FillStyle()
) : Item, ColorItem, LineItem, FillItem {
    override fun withColor(newColor: Int) = Box(
        lowerX,
        lowerY,
        upperX,
        upperY,
        newColor,
        lineStyle,
        fillStyle
    )

    override fun withLineStyle(newLineStyle: LineStyle) = Box(
        lowerX,
        lowerY,
        upperX,
        upperY,
        color,
        newLineStyle,
        fillStyle
    )

    override fun withFillStyle(newFillStyle: FillStyle) = Box(
        lowerX,
        lowerY,
        upperX,
        upperY,
        color,
        lineStyle,
        newFillStyle
    )
}