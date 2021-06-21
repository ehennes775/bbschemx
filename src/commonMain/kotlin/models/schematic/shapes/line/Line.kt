package models.schematic.shapes.line

import models.schematic.Item
import models.schematic.io.Reader
import models.schematic.io.Writer
import models.schematic.types.*
import types.Drawer
import types.RevealMode

class Line(
    val x0: Int = 0,
    val y0: Int = 0,
    val x1: Int = 0,
    val y1: Int = 0,
    override val color: ColorIndex = ColorIndex.GRAPHIC,
    override val lineStyle: LineStyle = LineStyle()
) : Item, ColorItem, LineItem {

    fun withValues(
        newX0: Int = x0,
        newY0: Int = y0,
        newX1: Int = x1,
        newY1: Int = y1,
        newColor: ColorIndex = color,
        newLineStyle: LineStyle = lineStyle
    ) = Line(newX0, newY0, newX1, newY1, newColor, newLineStyle)

    override fun withItemColor(newColor: ColorIndex) = withValues(
        newColor = newColor
    )

    override fun withLineStyle(newLineStyle: LineStyle) = withValues(
        newLineStyle = newLineStyle
    )

    override val isSignificant: Boolean get() = ((x1 - x0) != 0) && ((y1 - y0) != 0)

    override fun calculateBounds(revealMode: RevealMode) = Bounds.fromCorners(
        x0,
        y0,
        x1,
        y1,
        lineStyle.lineWidth
    )

    override fun inside(bounds: Bounds) = bounds.let { it.inside(x0, y0) && it.inside(x1, y1) }

    companion object : Creator {
        const val TOKEN = "L"

        override fun read(params: Array<String>, reader: Reader) = Line(
            x0 = params[1].toInt(),
            y0 = params[2].toInt(),
            x1 = params[3].toInt(),
            y1 = params[4].toInt(),
            color = ColorIndex(params[5].toInt()),
            lineStyle = LineStyle.fromFileParams(
                lineWidth = params[6],
                capType = params[7],
                dashType = params[8],
                dashLength = params[9],
                dashSpace = params[10]
            )
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
        color.toString(),
        lineStyle.lineWidthFileValue.toString(),
        lineStyle.capTypeFileValue.toString(),
        lineStyle.dashTypeFileValue.toString(),
        lineStyle.dashLengthFileValue.toString(),
        lineStyle.dashSpaceFileValue.toString()
    )
}