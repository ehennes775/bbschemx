package schematic.shapes.net

import schematic.Item
import schematic.Writer
import schematic.types.ColorItem

class Net(
    val x0: Int,
    val y0: Int,
    val x1: Int,
    val y1: Int,
    override val color: Int
) : Item, ColorItem {

    fun withX0(newX0: Int) = Net(
        newX0,
        y0,
        x1,
        y1,
        color
    )

    fun withY0(newY0: Int) = Net(
        x0,
        newY0,
        x1,
        y1,
        color
    )

    fun withX1(newX1: Int) = Net(
        x0,
        y0,
        newX1,
        y1,
        color
    )

    fun withY1(newY1: Int) = Net(
        x0,
        y0,
        x1,
        newY1,
        color
    )

    override fun withColor(newColor: Int) = Net(
       x0,
       y0,
       x1,
       y1,
       newColor
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