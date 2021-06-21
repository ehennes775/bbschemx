package models.schematic.shapes.path

import models.schematic.Item
import models.schematic.io.Reader
import models.schematic.io.Writer
import models.schematic.types.*
import types.Drawer
import types.RevealMode

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

    override fun calculateBounds(revealMode: RevealMode) = Bounds.EMPTY

    override fun inside(bounds: Bounds) = false

    companion object : Creator {
        const val TOKEN = "H"

        override fun read(params: Array<String>, reader: Reader) = Path(
            color = ColorIndex(params[1].toInt()),
            lineStyle = LineStyle.fromFileParams(
                lineWidth = params[2],
                capType = params[3],
                dashType = params[4],
                dashLength = params[5],
                dashSpace = params[6]
            ),
            fillStyle = FillStyle.fromFileParams(
                fillType = params[7],
                fillWidth = params[8],
                fillAngle1 = params[9],
                fillPitch1 = params[10],
                fillAngle2 = params[11],
                fillPitch2 = params[12],
            ),
            commands = parseCommands(reader.readLines(params[13].toInt()).joinToString(separator = " "))
        )

        private fun parseCommands(lines: String): List<Command> {
            return listOf()
        }
    }

    override fun paint(drawer: Drawer, revealMode: RevealMode, selected: Boolean) {
        drawer.beginDraw()
        commands.forEach { it.paint(drawer) }
        drawer.endDraw(selected, color, lineStyle, fillStyle)
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