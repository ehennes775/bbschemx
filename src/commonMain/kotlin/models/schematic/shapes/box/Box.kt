package models.schematic.shapes.box

import models.schematic.Item
import models.schematic.io.Reader
import models.schematic.io.Writer
import models.schematic.types.*
import types.Drawer
import types.RevealMode
import kotlin.math.abs

class Box(
    val lowerX: Int = 0,
    val lowerY: Int = 0,
    val upperX: Int = 100,
    val upperY: Int = 100,
    override val color: ColorIndex = ColorIndex.GRAPHIC,
    override val lineStyle: LineStyle = LineStyle(),
    override val fillStyle: FillStyle = FillStyle()
) : Item, ColorItem, LineItem, FillItem {

    fun withValues(
        newLowerX: Int = lowerX,
        newLowerY: Int = lowerY,
        newUpperX: Int = upperX,
        newUpperY: Int = upperY,
        newColor: ColorIndex = color,
        newLineStyle: LineStyle = lineStyle,
        newFillStyle: FillStyle = fillStyle
    ) = Box(newLowerX, newLowerY, newUpperX, newUpperY, newColor, newLineStyle, newFillStyle)

    override fun withItemColor(newColor: ColorIndex) = withValues(
        newColor = newColor,
    )

    override fun withLineStyle(newLineStyle: LineStyle) = withValues(
        newLineStyle = newLineStyle,
    )

    override fun withFillStyle(newFillStyle: FillStyle) = withValues(
        newFillStyle = newFillStyle
    )

    override val isSignificant: Boolean get() = ((upperX - lowerX) != 0) && ((upperY - lowerY) != 0)

    override fun calculateBounds(revealMode: RevealMode) = Bounds.fromCorners(
        lowerX,
        lowerY,
        upperX,
        upperY,
        lineStyle.lineWidth
    )

    override fun inside(bounds: Bounds) = bounds.let {
        it.inside(lowerX, lowerY) && it.inside(upperX, upperY)
    }

    companion object : Creator {
        const val TOKEN = "B"

        override fun read(params: Array<String>, reader: Reader): Box {
            val cornerX = params[1].toInt()
            val cornerY = params[2].toInt()
            val width = params[3].toInt()
            val height = params[4].toInt()

            return Box(
                lowerX = cornerX,
                lowerY = cornerY,
                upperX = cornerX + width,
                upperY = cornerY + height,
                color = ColorIndex(params[5].toInt()),
                lineStyle = LineStyle.fromFileParams(
                    lineWidth = params[6],
                    capType = params[7],
                    dashType = params[8],
                    dashLength = params[9],
                    dashSpace = params[10]
                ),
                fillStyle = FillStyle.fromFileParams(
                    fillType = params[11],
                    fillWidth = params[12],
                    fillAngle1 = params[13],
                    fillPitch1 = params[14],
                    fillAngle2 = params[15],
                    fillPitch2 = params[16],
                )
            )
        }
    }

    override fun paint(drawer: Drawer, revealMode: RevealMode, selected: Boolean) {
        drawer.apply {
            beginDraw()

            moveTo(lowerX, lowerY)
            lineTo(upperX, lowerY)
            lineTo(upperX, upperY)
            lineTo(lowerX, upperY)
            close()

            endDraw(selected, color, lineStyle, fillStyle)
        }
    }

    override fun write(writer: Writer) = writer.writeParams(
        TOKEN,
        minOf(lowerX, upperX).toString(),
        minOf(lowerY, upperY).toString(),
        abs(upperX - lowerX).toString(),
        abs(upperY - lowerY).toString(),
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