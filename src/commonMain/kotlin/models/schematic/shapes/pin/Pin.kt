package models.schematic.shapes.pin

import models.schematic.Item
import models.schematic.io.Reader
import models.schematic.io.Writer
import models.schematic.shapes.bus.Bus
import models.schematic.shapes.net.Net
import models.schematic.types.*

class Pin(
    val x0: Int,
    val y0: Int,
    val x1: Int,
    val y1: Int,
    override val color: ColorIndex,
    val pinType: PinType,
    val activeEnd: Int,
    override val attributes: Attributes = Attributes()
) : Item, ColorItem, AttributeItem {

    fun withX0(newX0: Int) = Pin(
        newX0,
        y0,
        x1,
        y1,
        color,
        pinType,
        activeEnd,
        attributes
    )

    fun withY0(newY0: Int) = Pin(
        x0,
        newY0,
        x1,
        y1,
        color,
        pinType,
        activeEnd,
        attributes
    )

    fun withX1(newX1: Int) = Pin(
        x0,
        y0,
        newX1,
        y1,
        color,
        pinType,
        activeEnd,
        attributes
    )

    fun withY1(newY1: Int) = Pin(
        x0,
        y0,
        x1,
        newY1,
        color,
        pinType,
        activeEnd,
        attributes
    )

    override fun withItemColor(newColor: ColorIndex) = Pin(
        x0,
        y0,
        x1,
        y1,
        newColor,
        pinType,
        activeEnd,
        attributes
    )

    fun withPinType(newPinType: PinType) = Pin(
        x0,
        y0,
        x1,
        y1,
        color,
        newPinType,
        activeEnd,
        attributes
    )

    fun withActiveEnd(newActiveEnd: Int) = Pin(
        x0,
        y0,
        x1,
        y1,
        color,
        pinType,
        newActiveEnd,
        attributes
    )

    override fun withAttributes(newAttributes: Attributes) = Pin(
        x0,
        y0,
        x1,
        y1,
        color,
        pinType,
        activeEnd,
        newAttributes
    )

    override fun calculateBounds() = Bounds.fromCorners(
        x0,
        y0,
        x1,
        y1
    )

    companion object : Creator {
        const val TOKEN = "P"

        override fun read(params: Array<String>, reader: Reader) = Pin(
            x0 = params[1].toInt(),
            y0 = params[2].toInt(),
            x1 = params[3].toInt(),
            y1 = params[4].toInt(),
            color = ColorIndex(params[5].toInt()),
            pinType = PinType.fromFileValue(params[6].toInt()),
            activeEnd = params[7].toInt()
        )
    }

    override fun paint(drawer: Drawer) {
        drawer.apply {
            beginDraw()

            moveTo(x0, y0)
            lineTo(x1, y1)

            var lineStyle = when(pinType) {
                PinType.BUS -> Bus.lineStyle
                PinType.NET -> Net.lineStyle
            }

            endDraw(ColorIndex.PIN, lineStyle)
        }
    }

    override fun write(writer: Writer) = writer.writeParams(
        TOKEN,
        x0.toString(),
        y0.toString(),
        x1.toString(),
        y1.toString(),
        color.toString(),
        pinType.fileValue.toString(),
        activeEnd.toString()
    )
}