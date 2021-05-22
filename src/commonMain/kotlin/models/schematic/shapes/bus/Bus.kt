package models.schematic.shapes.bus

import models.schematic.Item
import models.schematic.io.Reader
import models.schematic.io.Writer
import models.schematic.types.*

class Bus(
    val x0: Int,
    val y0: Int,
    val x1: Int,
    val y1: Int,
    override val color: ColorIndex,
    val ripperDirection: Int,
    override val attributes: Attributes = Attributes()
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

    override fun withItemColor(newColor: ColorIndex) = Bus(
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

    override fun calculateBounds() = Bounds.fromCorners(
        x0,
        y0,
        x1,
        y1
    )

    companion object : Creator {
        const val TOKEN = "U"

        val lineStyle = LineStyle(
            lineWidth = 30
        )

        override fun read(params: Array<String>, reader: Reader) = Bus(
            x0 = params[1].toInt(),
            y0 = params[2].toInt(),
            x1 = params[3].toInt(),
            y1 = params[4].toInt(),
            color = ColorIndex(params[5].toInt()),
            ripperDirection = params[6].toInt()
        )
    }

    override fun paint(drawer: Drawer) {
        drawer.apply {
            beginDraw()

            drawer.moveTo(x0, y0)
            drawer.lineTo(x1, y1)

            endDraw(ColorIndex.BUS, lineStyle)
        }
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