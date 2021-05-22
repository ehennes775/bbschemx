package models.schematic.shapes.circle

import models.schematic.Item
import models.schematic.io.Reader
import models.schematic.io.Writer
import models.schematic.types.*

class Circle(
    val centerX: Int = 0,
    val centerY: Int = 0,
    val radius: Int = 100,
    override val color: ColorIndex,
    override val lineStyle: LineStyle = LineStyle(),
    override val fillStyle: FillStyle = FillStyle()
) : Item, ColorItem, LineItem, FillItem {
    fun withCenterX(newCenterX: Int) = Circle(
        newCenterX,
        centerY,
        radius,
        color,
        lineStyle,
        fillStyle
    )

    fun withCenterY(newCenterY: Int) = Circle(
        centerX,
        newCenterY,
        radius,
        color,
        lineStyle,
        fillStyle
    )

    fun withRadius(newRadius: Int) = Circle(
        centerX,
        centerY,
        newRadius,
        color,
        lineStyle,
        fillStyle
    )

    override fun withItemColor(newColor: ColorIndex) = Circle(
        centerX,
        centerY,
        radius,
        newColor,
        lineStyle,
        fillStyle
    )

    override fun withLineStyle(newLineStyle: LineStyle) = Circle(
        centerX,
        centerY,
        radius,
        color,
        newLineStyle,
        fillStyle
    )

    override fun withFillStyle(newFillStyle: FillStyle) = Circle(
        centerX,
        centerY,
        radius,
        color,
        lineStyle,
        newFillStyle
    )

    override fun calculateBounds() = Bounds.fromCorners(
        centerX - radius,
        centerY - radius,
        centerX + radius,
        centerY + radius,
        lineStyle.lineWidth
    )

    companion object : Creator {
        const val TOKEN = "V"

        override fun read(params: Array<String>, reader: Reader) = Circle(
            centerX = params[1].toInt(),
            centerY = params[2].toInt(),
            radius = params[3].toInt(),
            color = ColorIndex(params[4].toInt()),
            lineStyle = LineStyle(
                lineWidth = params[5].toInt(),
                capType = CapType.fromFileValue(params[6].toInt()),
                dashType = DashType.fromFileValue(params[7].toInt()),
                dashLength = params[8].toInt(),
                dashSpace = params[9].toInt(),
            ),
            fillStyle = FillStyle(
                fillType = FillType.fromFileValue(params[10].toInt()),
                fillWidth = params[11].toInt(),
                fillAngle1 = params[12].toInt(),
                fillPitch1 = params[13].toInt(),
                fillAngle2 = params[14].toInt(),
                fillPitch2 = params[15].toInt(),
            )
        )
    }

    override fun paint(drawer: Drawer) {
    }

    override fun write(writer: Writer) = writer.writeParams(
        TOKEN,
        centerX.toString(),
        centerY.toString(),
        radius.toString(),
        color.toString(),
        lineStyle.lineWidthFileValue.toString(),
        lineStyle.capTypeFileValue.toString(),
        lineStyle.dashTypeFileValue.toString(),
        lineStyle.dashLengthFileValue.toString(),
        lineStyle.dashSpaceFileValue.toString(),
        fillStyle.fillTypeFileValue.toString(),
        fillStyle.fillWidthFileValue.toString(),
        fillStyle.fillAngle1FileValue.toString(),
        fillStyle.fillPitch1FileValue.toString(),
        fillStyle.fillAngle2FileValue.toString(),
        fillStyle.fillPitch2FileValue.toString()
    )
}