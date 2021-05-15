package views.schematic.shapes.text

import views.schematic.Item
import views.schematic.io.Reader
import views.schematic.io.Writer
import views.schematic.types.Attribute
import views.schematic.types.ColorItem
import views.schematic.types.Creator

class Text(
    val insertX: Int,
    val insertY: Int,
    override val color: Int,
    val size: Int,
    val visibility: Visibility = Visibility.VISIBLE,
    val presentation: Presentation = Presentation.NAME_VALUE,
    val rotation: Int = 0,
    val alignment: Alignment = Alignment.LOWER_LEFT,
    val lines: Array<String>
) : Item, ColorItem, Attribute {

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

    override fun withColor(newColor: Int) = Text(
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

    companion object : Creator {
        const val TOKEN = "T"

        override fun read(params: Array<String>, reader: Reader) = Text(
            insertX = params[1].toInt(),
            insertY = params[2].toInt(),
            color = params[3].toInt(),
            size = params[4].toInt(),
            visibility = Visibility.fromFileValue(params[6].toInt()),
            presentation = Presentation.fromFileValue(params[6].toInt()),
            rotation = params[7].toInt(),
            alignment = Alignment.fromFileValue(params[8].toInt()),
            lines = reader.readLines(params[9].toInt())
        )
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