package schematic.shapes.bus

import schematic.Item
import schematic.Writer
import schematic.types.AttributeItem
import schematic.types.Attributes
import schematic.types.ColorItem

class Bus(
    val x0: Int,
    val y0: Int,
    val x1: Int,
    val y1: Int,
    override val color: Int,
    val ripperDirection: Int,
    override val attributes: Attributes
) : Item, ColorItem, AttributeItem {

    fun withX0(newX0: Int) = Bus(
        newX0,
        y0,
        x1,
        y1,
        color,
        ripperDirection,
        attributes
    )

    fun withY0(newY0: Int) = Bus(
        x0,
        newY0,
        x1,
        y1,
        color,
        ripperDirection,
        attributes
    )

    fun withX1(newX1: Int) = Bus(
        x0,
        y0,
        newX1,
        y1,
        color,
        ripperDirection,
        attributes
    )

    fun withY1(newY1: Int) = Bus(
        x0,
        y0,
        x1,
        newY1,
        color,
        ripperDirection,
        attributes
    )

    override fun withColor(newColor: Int) = Bus(
        x0,
        y0,
        x1,
        y1,
        newColor,
        ripperDirection,
        attributes
    )

    fun withRipperDirection(newRipperDirection: Int) = Bus(
        x0,
        y0,
        x1,
        y1,
        color,
        newRipperDirection,
        attributes
    )

    override fun withAttributes(newAttributes: Attributes) = Bus(
        x0,
        y0,
        x1,
        y1,
        color,
        ripperDirection,
        newAttributes
    )

    companion object {
        const val TOKEN = "U"
    }

    override fun write(writer: Writer) = writer.writeParams(
        TOKEN,
        x0.toString(),
        y0.toString(),
        x1.toString(),
        y1.toString(),
        color.toString(),
        ripperDirection.toString()
    )
}