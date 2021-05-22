package models.schematic.shapes.text

import models.schematic.Item
import models.schematic.io.Reader
import models.schematic.io.Writer
import models.schematic.types.*

class Text(
    val insertX: Int,
    val insertY: Int,
    override val color: ColorIndex,
    val size: Int,
    val visibility: Visibility = Visibility.VISIBLE,
    val presentation: Presentation = Presentation.NAME_VALUE,
    val rotation: Int = 0,
    val alignment: Alignment = Alignment.LOWER_LEFT,
    val lines: Array<String>
) : Item, ColorItem, Attribute {

    val shownLines: Array<String> = when (presentation) {
        Presentation.NAME_VALUE -> lines
        Presentation.VALUE -> lines.mapIndexed { index, line ->
            if (index == 0) {
                regex.find(lines[0])?.groups?.get(3)?.value ?: line
            } else {
                line
            }
        }.toTypedArray()
        Presentation.NAME -> arrayOf(
            regex.find(lines.first())?.groups?.get(2)?.value ?: "Error"
        )
    }

    fun withInsertX(newInsertX: Int) = Text(
        newInsertX,
        insertY,
        color,
        size,
        visibility,
        presentation,
        rotation,
        alignment,
        lines
    )

    fun withInsertY(newInsertY: Int) = Text(
        insertX,
        newInsertY,
        color,
        size,
        visibility,
        presentation,
        rotation,
        alignment,
        lines
    )

    override fun withItemColor(newColor: ColorIndex) = Text(
        insertX,
        insertY,
        newColor,
        size,
        visibility,
        presentation,
        rotation,
        alignment,
        lines
    )

    fun withSize(newSize: Int) = Text(
        insertX,
        insertY,
        color,
        newSize,
        visibility,
        presentation,
        rotation,
        alignment,
        lines
    )

    fun withVisibility(newVisibility: Visibility) = Text(
        insertX,
        insertY,
        color,
        size,
        newVisibility,
        presentation,
        rotation,
        alignment,
        lines
    )

    fun withPresentation(newPresentation: Presentation) = Text(
        insertX,
        insertY,
        color,
        size,
        visibility,
        newPresentation,
        rotation,
        alignment,
        lines
    )

    fun withRotation(newRotation: Int) = Text(
        insertX,
        insertY,
        color,
        size,
        visibility,
        presentation,
        newRotation,
        alignment,
        lines
    )

    fun withAlignment(newAlignment: Alignment) = Text(
        insertX,
        insertY,
        color,
        size,
        visibility,
        presentation,
        rotation,
        newAlignment,
        lines
    )

    fun withLines(newLines: Array<String>) = Text(
        insertX,
        insertY,
        color,
        size,
        visibility,
        presentation,
        rotation,
        alignment,
        newLines
    )

    override fun calculateBounds() = Bounds.EMPTY

    companion object : Creator {
        const val TOKEN = "T"

        private val regex = Regex("""(?<both>(?<name>.+?)=(?<value>.*))|(?<text>.+)""")

        override fun read(params: Array<String>, reader: Reader) = Text(
            insertX = params[1].toInt(),
            insertY = params[2].toInt(),
            color = ColorIndex(params[3].toInt()),
            size = params[4].toInt(),
            visibility = Visibility.fromFileValue(params[5].toInt()),
            presentation = Presentation.fromFileValue(params[6].toInt()),
            rotation = params[7].toInt(),
            alignment = Alignment.fromFileValue(params[8].toInt()),
            lines = reader.readLines(params[9].toInt())
        )
    }

    override fun paint(drawer: Drawer) {
        if (visibility == Visibility.VISIBLE) {
            drawer.drawText(this)
        }
    }

    override fun write(writer: Writer) {
        writer.writeParams(
            TOKEN,
            insertX.toString(),
            insertY.toString(),
            color.toString(),
            size.toString(),
            visibility.fileValue.toString(),
            presentation.fileValue.toString(),
            rotation.toString(),
            alignment.fileValue.toString(),
            lines.size.toString()
        )
        lines.forEach { writer.writeLine(it) }
    }
}