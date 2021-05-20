package models.schematic.shapes.arc

import models.schematic.Item
import models.schematic.io.Reader
import models.schematic.io.Writer
import models.schematic.types.*

class Arc(
    val centerX: Int = 0,
    val centerY: Int = 0,
    val radius: Int = 100,
    val startAngle: Int = 0,
    val sweepAngle: Int = 180,
    override val color: Int,
    override val lineStyle: LineStyle = LineStyle()
) : Item, ColorItem, LineItem {

    fun withCenterX(newCenterX: Int) = Arc(
        newCenterX,
        centerY,
        radius,
        startAngle,
        sweepAngle,
        color,
        lineStyle
    )

    fun withCenterY(newCenterY: Int) = Arc(
        centerX,
        newCenterY,
        radius,
        startAngle,
        sweepAngle,
        color,
        lineStyle
    )

    fun withRadius(newRadius: Int) = Arc(
        centerX,
        centerY,
        newRadius,
        startAngle,
        sweepAngle,
        color,
        lineStyle
    )

    fun withStartAngle(newStartAngle: Int) = Arc(
        centerX,
        centerY,
        radius,
        newStartAngle,
        sweepAngle,
        color,
        lineStyle
    )

    fun withSweepAngle(newSweepAngle: Int) = Arc(
        centerX,
        centerY,
        radius,
        startAngle,
        newSweepAngle,
        color,
        lineStyle
    )

    override fun withItemColor(newColor: Int) = Arc(
        centerX,
        centerY,
        radius,
        startAngle,
        sweepAngle,
        newColor,
        lineStyle
    )

    override fun withLineStyle(newLineStyle: LineStyle) = Arc(
        centerX,
        centerY,
        radius,
        startAngle,
        sweepAngle,
        color,
        newLineStyle
    )

    override fun calculateBounds() = Bounds.EMPTY

    companion object : Creator {
        const val TOKEN = "A"

        override fun read(params: Array<String>, reader: Reader) = Arc(
            centerX = params[1].toInt(),
            centerY = params[2].toInt(),
            radius = params[3].toInt(),
            startAngle = params[4].toInt(),
            sweepAngle = params[5].toInt(),
            color = params[6].toInt(),
            lineStyle = LineStyle(
                lineWidth = params[7].toInt(),
                capType = CapType.fromFileValue(params[8].toInt()),
                dashType = DashType.fromFileValue(params[9].toInt()),
                dashLength = params[10].toInt(),
                dashSpace = params[11].toInt()
            )
        )
    }

    override fun write(writer: Writer) = writer.writeParams(
        TOKEN,
        centerX.toString(),
        centerY.toString(),
        radius.toString(),
        startAngle.toString(),
        sweepAngle.toString(),
        color.toString(),
        lineStyle.lineWidthFileValue.toString(),
        lineStyle.capTypeFileValue.toString(),
        lineStyle.dashTypeFileValue.toString(),
        lineStyle.dashLengthFileValue.toString(),
        lineStyle.dashSpaceFileValue.toString()
    )
}
