package schematic.shapes.box

import schematic.Item
import schematic.Writer
import schematic.types.*
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

    companion object {
        const val TOKEN = "B"
    }

    override fun write(writer: Writer) = writer.writeParams(
        TOKEN,
        minOf(lowerX, upperX).toString(),
        minOf(lowerY, upperY).toString(),
        abs(upperX - lowerX).toString(),
        abs(upperY - lowerY).toString(),
        color.toString(),
        lineStyle.lineWidthFileValue.toString(),
        lineStyle.dashTypeFileValue.toString(),
        lineStyle.dashLengthFileValue.toString(),
        lineStyle.dashSpaceFileValue.toString(),
        lineStyle.capTypeFileValue.toString(),
        fillStyle.fillTypeFileValue.toString(),
        fillStyle.fillWidthFileValue.toString(),
        fillStyle.fillAngle1FileValue.toString(),
        fillStyle.fillPitch1FileValue.toString(),
        fillStyle.fillAngle2FileValue.toString(),
        fillStyle.fillPitch2FileValue.toString()
    )
}