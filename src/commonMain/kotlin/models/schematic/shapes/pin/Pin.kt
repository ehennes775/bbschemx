package models.schematic.shapes.pin

import models.schematic.Item
import models.schematic.io.Reader
import models.schematic.io.Writer
import models.schematic.shapes.bus.Bus
import models.schematic.shapes.net.Net
import models.schematic.types.*
import types.Drawer
import types.RevealMode

class Pin(
    val x0: Int = 0,
    val y0: Int = 0,
    val x1: Int = 0,
    val y1: Int = 0,
    override val color: ColorIndex = ColorIndex.PIN,
    val pinType: PinType = PinType.NET,
    val activeEnd: Int = 0,
    override val attributes: Attributes = Attributes()
) : Item, ColorItem, AttributeItem {

    fun withValues(
        newX0: Int = x0,
        newY0: Int = y0,
        newX1: Int = x1,
        newY1: Int = y1,
        newColor: ColorIndex = color,
        newPinType: PinType = pinType,
        newActiveEnd: Int = activeEnd,
        newAttributes: Attributes = attributes
    ) = Pin(newX0, newY0, newX1, newY1, newColor, newPinType, newActiveEnd, newAttributes)

    override fun withItemColor(newColor: ColorIndex) = withValues(
        newColor = newColor
    )

    override fun withAttributes(newAttributes: Attributes) = withValues(
        newAttributes = newAttributes
    )

    override val isSignificant: Boolean get() = ((x1 - x0) != 0) && ((y1 - y0) != 0)

    override fun calculateBounds(revealMode: RevealMode) = Bounds.fromCorners(
        x0,
        y0,
        x1,
        y1
    )

    override fun inside(bounds: Bounds) = bounds.let { it.inside(x0, y0) && it.inside(x1, y1) }

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

    override fun paint(drawer: Drawer, revealMode: RevealMode, selected: Boolean) {
        drawer.apply {
            beginDraw()

            moveTo(x0, y0)
            lineTo(x1, y1)

            var lineStyle = when(pinType) {
                PinType.BUS -> Bus.lineStyle
                PinType.NET -> Net.lineStyle
            }

            endDraw(selected, color, lineStyle)
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