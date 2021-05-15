package views.schematic.shapes.line

import views.schematic.Item
import views.schematic.io.Reader
import views.schematic.io.Writer
import views.schematic.types.*

class Line(
    val x0: Int,
    val y0: Int,
    val x1: Int,
    val y1: Int,
    override val color: Int,
    override val lineStyle: LineStyle
) : Item, ColorItem, LineItem {

    fun withX0(newX0: Int) = Line(
        newX0,
        y0,
        x1,
        y1,
        color,
        lineStyle
    )

    fun withY0(newY0: Int) = Line(
        x0,
        newY0,
        x1,
        y1,
        color,
        lineStyle
    )

    fun withX1(newX1: Int) = Line(
        x0,
        y0,
        newX1,
        y1,
        color,
        lineStyle
    )

    fun withY1(newY1: Int) = Line(
        x0,
        y0,
        x1,
        newY1,
        color,
        lineStyle
    )

    override fun withColor(newColor: Int) = Line(
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

    companion object : Creator {
        const val TOKEN = "L"

        override fun read(params: Array<String>, reader: Reader) = Line(
            x0 = params[1].toInt(),
            y0 = params[2].toInt(),
            x1 = params[3].toInt(),
            y1 = params[4].toInt(),
            color = params[5].toInt(),
            lineStyle = LineStyle(
                lineWidth = params[6].toInt(),
                capType = CapType.fromFileValue(params[7].toInt()),
                dashType = DashType.fromFileValue(params[8].toInt()),
                dashLength = params[9].toInt(),
                dashSpace = params[10].toInt()
            )
        )
    }

    override fun write(writer: Writer) = writer.writeParams(
        TOKEN,
        x0.toString(),
        y0.toString(),
        x1.toString(),
        y1.toString(),
        color.toString(),
        lineStyle.lineWidthFileValue.toString(),
        lineStyle.dashTypeFileValue.toString(),
        lineStyle.dashLengthFileValue.toString(),
        lineStyle.dashSpaceFileValue.toString(),
        lineStyle.capTypeFileValue.toString(),
    )
}