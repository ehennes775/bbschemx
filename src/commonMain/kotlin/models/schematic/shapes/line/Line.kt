package models.schematic.shapes.line

import models.schematic.Item
import models.schematic.io.Reader
import models.schematic.io.Writer
import models.schematic.types.*

class Line(
    val x0: Int = 0,
    val y0: Int = 0,
    val x1: Int = 0,
    val y1: Int = 0,
    override val color: ColorIndex = ColorIndex.GRAPHIC,
    override val lineStyle: LineStyle = LineStyle()
) : Item, ColorItem, LineItem {

    fun withPoint0(newX0: Int, newY0: Int) = Line(
        newX0,
        newY0,
        x1,
        y1,
        color,
        lineStyle
    )

    fun withX0(newX0: Int) = withPoint0(
        newX0,
        y0,
    )

    fun withY0(newY0: Int) = withPoint0(
        x0,
        newY0,
    )

    fun withPoint1(newX1: Int, newY1: Int) = Line(
        x0,
        y0,
        newX1,
        newY1,
        color,
        lineStyle
    )

    fun withX1(newX1: Int) = withPoint1(
        newX1,
        y1,
    )

    fun withY1(newY1: Int) = withPoint1(
        x1,
        newY1,
    )

    override fun withItemColor(newColor: ColorIndex) = Line(
        x0,
        y0,
        x1,
        y1,
        newColor,
        lineStyle
    )

    override fun withLineStyle(newLineStyle: LineStyle) = Line(
        x0,
        y0,
        x1,
        y1,
        color,
        newLineStyle
    )

    override val isSignificant: Boolean get() = ((x1 - x0) != 0) && ((y1 - y0) != 0)

    override fun calculateBounds() = Bounds.fromCorners(
        x0,
        y0,
        x1,
        y1,
        lineStyle.lineWidth
    )

    companion object : Creator {
        const val TOKEN = "L"

        override fun read(params: Array<String>, reader: Reader) = Line(
            x0 = params[1].toInt(),
            y0 = params[2].toInt(),
            x1 = params[3].toInt(),
            y1 = params[4].toInt(),
            color = ColorIndex(params[5].toInt()),
            lineStyle = LineStyle(
                lineWidth = params[6].toInt(),
                capType = CapType.fromFileValue(params[7].toInt()),
                dashType = DashType.fromFileValue(params[8].toInt()),
                dashLength = params[9].toInt(),
                dashSpace = params[10].toInt()
            )
        )
    }

    override fun paint(drawer: Drawer) {
        drawer.apply {
            beginDraw()

            moveTo(x0, y0)
            lineTo(x1, y1)

            endDraw(color, lineStyle)
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