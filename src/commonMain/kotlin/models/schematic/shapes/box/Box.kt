package models.schematic.shapes.box

import models.schematic.Item
import models.schematic.io.Reader
import models.schematic.io.Writer
import models.schematic.types.*
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

    fun withPoint0(newLowerX: Int, newLowerY: Int) = Box(
        newLowerX,
        newLowerY,
        upperX,
        upperY,
        color,
        lineStyle,
        fillStyle
    )

    fun withLowerX(newLowerX: Int) = Box(
        newLowerX,
        lowerY,
        upperX,
        upperY,
        color,
        lineStyle,
        fillStyle
    )

    fun withLowerY(newLowerY: Int) = Box(
        lowerX,
        newLowerY,
        upperX,
        upperY,
        color,
        lineStyle,
        fillStyle
    )

    fun withPoint1(newUpperX: Int, newUpperY: Int) = Box(
        lowerX,
        lowerY,
        newUpperX,
        newUpperY,
        color,
        lineStyle,
        fillStyle
    )

    fun withUpperX(newUpperX: Int) = Box(
        lowerX,
        lowerY,
        newUpperX,
        upperY,
        color,
        lineStyle,
        fillStyle
    )

    fun withUpperY(newUpperY: Int) = Box(
        lowerX,
        lowerY,
        upperX,
        newUpperY,
        color,
        lineStyle,
        fillStyle
    )

    override fun withItemColor(newColor: ColorIndex) = Box(
        lowerX,
        lowerY,
        upperX,
        upperY,
        newColor,
        lineStyle,
        fillStyle
    )

    override fun withLineStyle(newLineStyle: LineStyle) = Box(
        lowerX,
        lowerY,
        upperX,
        upperY,
        color,
        newLineStyle,
        fillStyle
    )

    override fun withFillStyle(newFillStyle: FillStyle) = Box(
        lowerX,
        lowerY,
        upperX,
        upperY,
        color,
        lineStyle,
        newFillStyle
    )

    override fun calculateBounds() = Bounds.fromCorners(
        lowerX,
        lowerY,
        upperX,
        upperY,
        lineStyle.lineWidth
    )

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
                lineStyle = LineStyle(
                    lineWidth = params[6].toInt(),
                    capType = CapType.fromFileValue(params[7].toInt()),
                    dashType = DashType.fromFileValue(params[8].toInt()),
                    dashLength = params[9].toInt(),
                    dashSpace = params[10].toInt()
                ),
                fillStyle = FillStyle(
                    fillType = FillType.fromFileValue(params[11].toInt()),
                    fillWidth = params[12].toInt(),
                    fillAngle1 = params[13].toInt(),
                    fillPitch1 = params[14].toInt(),
                    fillAngle2 = params[15].toInt(),
                    fillPitch2 = params[16].toInt(),
                )
            )
        }
    }

    override fun paint(drawer: Drawer) {
        drawer.apply {
            beginDraw()

            moveTo(lowerX, lowerY)
            lineTo(upperX, lowerY)
            lineTo(upperX, upperY)
            lineTo(lowerX, upperY)
            close()

            endDraw(color , lineStyle, fillStyle)
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