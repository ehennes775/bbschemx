package schematic.shapes.net

import schematic.Item
import schematic.Writer
import schematic.types.AttributeItem
import schematic.types.Attributes
import schematic.types.ColorItem

class Net(
    val x0: Int,
    val y0: Int,
    val x1: Int,
    val y1: Int,
    override val color: Int,
    override val attributes: Attributes
) : Item, ColorItem, AttributeItem {

    fun withX0(newX0: Int) = Net(
        newX0,
        y0,
        x1,
        y1,
        color,
        attributes
    )

    fun withY0(newY0: Int) = Net(
        x0,
        newY0,
        x1,
        y1,
        color,
        attributes
    )

    fun withX1(newX1: Int) = Net(
        x0,
        y0,
        newX1,
        y1,
        color,
        attributes
    )

    fun withY1(newY1: Int) = Net(
        x0,
        y0,
        x1,
        newY1,
        color,
        attributes
    )

    override fun withColor(newColor: Int) = Net(
       x0,
       y0,
       x1,
       y1,
       newColor,
       attributes
    )

    override fun withAttributes(newAttributes: Attributes) = Net(
        x0,
        y0,
        x1,
        y1,
        color,
        newAttributes
    )

    companion object {
        const val TOKEN = "N"
    }

    override fun write(writer: Writer) = writer.writeParams(
        TOKEN,
        x0.toString(),
        y0.toString(),
        x1.toString(),
        y1.toString(),
        color.toString()
    )
}