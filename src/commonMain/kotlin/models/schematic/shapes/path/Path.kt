package models.schematic.shapes.path

import models.schematic.Item
import models.schematic.io.Reader
import models.schematic.io.Writer
import models.schematic.types.*

class Path(
    override val color: ColorIndex,
    override val lineStyle: LineStyle,
    override val fillStyle: FillStyle,
    val commands: List<Command> = listOf(),
): Item, ColorItem, LineItem, FillItem {
    override fun withItemColor(newColor: ColorIndex) = Path(
        color = newColor,
        lineStyle = lineStyle,
        fillStyle = fillStyle,
        commands = commands
    )

    override fun withLineStyle(newLineStyle: LineStyle) = Path(
        color = color,
        lineStyle = newLineStyle,
        fillStyle = fillStyle,
        commands = commands
    )

    override fun withFillStyle(newFillStyle: FillStyle) = Path(
        color = color,
        lineStyle = lineStyle,
        fillStyle = newFillStyle,
        commands = commands
    )

    fun withCommands(newCommands: List<Command>) = Path(
        color = color,
        lineStyle = lineStyle,
        fillStyle = fillStyle,
        commands = newCommands
    )

    override val isSignificant: Boolean get() = commands.isNotEmpty()

    override fun calculateBounds() = Bounds.EMPTY

    companion object : Creator {
        const val TOKEN = "H"

        override fun read(params: Array<String>, reader: Reader) = Path(
            color = ColorIndex(params[1].toInt()),
            lineStyle = LineStyle(
                lineWidth = params[2].toInt(),
                capType = CapType.fromFileValue(params[3].toInt()),
                dashType = DashType.fromFileValue(params[4].toInt()),
                dashLength = params[5].toInt(),
                dashSpace = params[6].toInt()
            ),
            fillStyle = FillStyle(
                fillType = FillType.fromFileValue(params[7].toInt()),
                fillWidth = params[8].toInt(),
                fillAngle1 = params[9].toInt(),
                fillPitch1 = params[10].toInt(),
                fillAngle2 = params[11].toInt(),
                fillPitch2 = params[12].toInt(),
            ),
            commands = parseCommands(reader.readLines(params[13].toInt()).joinToString(separator = " "))
        )

        private fun parseCommands(lines: String): List<Command> {
            return listOf()
        }
    }

    override fun paint(drawer: Drawer) {
        drawer.beginDraw()
        commands.forEach { it.paint(drawer) }
        drawer.endDraw(color, lineStyle, fillStyle)
    }

    override fun write(writer: Writer) {
        writer.writeParams(
            TOKEN,
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
            fillStyle.fillPitch2FileValue.toString(),
            commands.size.toString()
        )
        commands.forEach { it.write(writer) }
    }
}