package schematic.shapes.pin

import schematic.Item
import schematic.Writer
import schematic.types.AttributeItem
import schematic.types.Attributes
import schematic.types.ColorItem

class Pin(
    val x0: Int,
    val y0: Int,
    val x1: Int,
    val y1: Int,
    override val color: Int,
    val pinType: PinType,
    val activeEnd: Int,
    override val attributes: Attributes
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

    override fun withColor(newColor: Int) = Pin(
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

    companion object {
        const val TOKEN = "P"
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