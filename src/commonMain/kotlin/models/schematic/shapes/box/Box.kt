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
    override val color: Int,
    override val lineStyle: LineStyle = LineStyle(),
    override val fillStyle: FillStyle = FillStyle()
) : Item, ColorItem, LineItem, FillItem {

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

    override fun withColor(newColor: Int) = Box(
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

    companion object : Creator {
        const val TOKEN = "B"

        override fun read(params: Array<String>, reader: Reader) = Box(
            lowerX = params[1].toInt(),
            lowerY = params[2].toInt(),
            upperX = params[3].toInt(),
            upperY = params[4].toInt(),
            color = params[5].toInt(),
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