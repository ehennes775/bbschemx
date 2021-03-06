package models.schematic.shapes.circle

import models.schematic.Item
import models.schematic.io.Reader
import models.schematic.io.Writer
import models.schematic.types.*
import types.Drawer
import types.RevealMode

class Circle(
    val centerX: Int = 0,
    val centerY: Int = 0,
    val radius: Int = 100,
    override val color: ColorIndex = ColorIndex.GRAPHIC,
    override val lineStyle: LineStyle = LineStyle(),
    override val fillStyle: FillStyle = FillStyle()
) : Item, ColorItem, LineItem, FillItem {

    fun withValues(
        newCenterX: Int = centerX,
        newCenterY: Int = centerY,
        newRadius: Int = radius,
        newColor: ColorIndex = color,
        newLineStyle: LineStyle = lineStyle,
        newFillStyle: FillStyle = fillStyle
    ) = Circle(newCenterX, newCenterY, newRadius, newColor, newLineStyle, newFillStyle)

    override fun withItemColor(newColor: ColorIndex) = withValues(
        newColor = newColor
    )

    override fun withLineStyle(newLineStyle: LineStyle) = withValues(
        newLineStyle = newLineStyle
    )

    override fun withFillStyle(newFillStyle: FillStyle) = withValues(
        newFillStyle = newFillStyle
    )

    override val isSignificant: Boolean get() = (radius != 0)

    override fun calculateBounds(revealMode: RevealMode) = Bounds.fromCorners(
        centerX - radius,
        centerY - radius,
        centerX + radius,
        centerY + radius,
        lineStyle.lineWidth
    )

    override fun inside(bounds: Bounds) = false

    companion object : Creator {
        const val TOKEN = "V"

        override fun read(params: Array<String>, reader: Reader) = Circle(
            centerX = params[1].toInt(),
            centerY = params[2].toInt(),
            radius = params[3].toInt(),
            color = ColorIndex(params[4].toInt()),
            lineStyle = LineStyle.fromFileParams(
                lineWidth = params[5],
                capType = params[6],
                dashType = params[7],
                dashLength = params[8],
                dashSpace = params[9],
            ),
            fillStyle = FillStyle.fromFileParams(
                fillType = params[10],
                fillWidth = params[11],
                fillAngle1 = params[12],
                fillPitch1 = params[13],
                fillAngle2 = params[14],
                fillPitch2 = params[15],
            )
        )
    }

    override fun paint(drawer: Drawer, revealMode: RevealMode, selected: Boolean) {
        drawer.apply {
            beginDraw()
            drawCircle(centerX, centerY, radius)
            endDraw(selected, color, lineStyle, fillStyle)
        }
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