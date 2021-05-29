package models.schematic.shapes.net

import models.schematic.Item
import models.schematic.io.Reader
import models.schematic.io.Writer
import models.schematic.types.*
import types.Drawer
import types.RevealMode

class Net(
    val x0: Int = 0,
    val y0: Int = 0,
    val x1: Int = 0,
    val y1: Int = 0,
    override val color: ColorIndex = ColorIndex.NET,
    override val attributes: Attributes = Attributes()
) : Item, ColorItem, AttributeItem {

    fun withValues(
        x0: Int = this.x0,
        y0: Int = this.y0,
        x1: Int = this.x1,
        y1: Int = this.y1,
        color: ColorIndex = this.color,
        attributes: Attributes = this.attributes
    ) = Net(x0, y0, x1, y1, color, attributes)

    override fun withItemColor(newColor: ColorIndex) = withValues(color = newColor)

    override fun withAttributes(newAttributes: Attributes) = withValues(attributes = newAttributes)

    override val isSignificant: Boolean get() = ((x1 - x0) != 0) && ((y1 - y0) != 0)

    override fun calculateBounds(revealMode: RevealMode) = Bounds.fromCorners(
        x0,
        y0,
        x1,
        y1
    )

    override fun inside(bounds: Bounds) = bounds.let { it.inside(x0, y0) && it.inside(x1, y1) }

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


    override fun paint(drawer: Drawer, revealMode: RevealMode, selected: Boolean) {
        drawer.apply {
            beginDraw()
            moveTo(x0, y0)
            lineTo(x1, y1)
            endDraw(selected, color, lineStyle)
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