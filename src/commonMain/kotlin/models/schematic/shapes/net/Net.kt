package models.schematic.shapes.net

import models.schematic.Item
import models.schematic.io.Reader
import models.schematic.io.Writer
import models.schematic.types.*

class Net(
    val x0: Int = 0,
    val y0: Int = 0,
    val x1: Int = 0,
    val y1: Int = 0,
    override val color: ColorIndex = ColorIndex.NET,
    override val attributes: Attributes = Attributes()
) : Item, ColorItem, AttributeItem {

    fun withPoint0(newX0: Int, newY0: Int) = Net(
        newX0,
        newY0,
        x1,
        y1,
        color,
        attributes
    )

    fun withX0(newX0: Int) = withPoint0(
        newX0,
        y0,
    )

    fun withY0(newY0: Int) = withPoint0(
        x0,
        newY0,
    )

    fun withPoint1(newX1: Int, newY1: Int) = Net(
        x0,
        y0,
        newX1,
        newY1,
        color,
        attributes
    )

    fun withX1(newX1: Int) = withPoint1(
        newX1,
        y1,
    )

    fun withY1(newY1: Int) = withPoint1(
        x1,
        newY1,
    )

    override fun withItemColor(newColor: ColorIndex) = Net(
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

    override fun calculateBounds() = Bounds.fromCorners(
        x0,
        y0,
        x1,
        y1
    )

    companion object : Creator {
        const val TOKEN = "N"

        val lineStyle = LineStyle()

        override fun read(params: Array<String>, reader: Reader) = Net(
            x0 = params[1].toInt(),
            y0 = params[2].toInt(),
            x1 = params[3].toInt(),
            y1 = params[4].toInt(),
            color = ColorIndex(params[5].toInt())
        )
    }


    override fun paint(drawer: Drawer) {
        drawer.apply {
            beginDraw()

            moveTo(x0, y0)
            lineTo(x1, y1)

            endDraw(ColorIndex.NET, lineStyle)
        }
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