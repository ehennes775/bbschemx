package views.schematic.shapes.bus

import views.schematic.Item
import views.schematic.io.Reader
import views.schematic.io.Writer
import views.schematic.types.*

class Bus(
    val x0: Int,
    val y0: Int,
    val x1: Int,
    val y1: Int,
    override val color: Int,
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

    companion object : Creator {
        const val TOKEN = "U"

        override fun read(params: Array<String>, reader: Reader) = Bus(
            x0 = params[1].toInt(),
            y0 = params[2].toInt(),
            x1 = params[3].toInt(),
            y1 = params[4].toInt(),
            color = params[5].toInt(),
            ripperDirection = params[6].toInt()
        )
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