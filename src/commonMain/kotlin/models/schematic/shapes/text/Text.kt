package models.schematic.shapes.text

import models.schematic.Item
import models.schematic.io.Reader
import models.schematic.io.Writer
import models.schematic.types.*
import types.Drawer
import types.RevealMode

class Text(
    val insertX: Int,
    val insertY: Int,
    override val color: ColorIndex,
    val size: Int,
    val visibility: Visibility = Visibility.VISIBLE,
    val presentation: Presentation = Presentation.NAME_VALUE,
    val rotation: Int = 0,
    val alignment: Alignment = Alignment.LOWER_LEFT,
    val lines: Lines
) : Item, ColorItem, Attribute {

    override val isAttribute: Boolean
        get() = lines.isAttribute

    override val attributeNameOrNull: String?
        get() = lines.attributeNameOrNull

    override val attributeValueOrNull: Lines?
        get() = lines.attributeValueOrNull

    override fun withName(newName: String): Item {
        TODO("Not yet implemented")
    }

    override fun withValue(newValue: Array<String>): Item {
        TODO("Not yet implemented")
    }

    val shownLines: Lines = when (presentation) {
        Presentation.NAME_VALUE -> lines
        Presentation.VALUE -> lines.attributeValueOrNull ?: lines
        Presentation.NAME -> lines.attributeNameOrNull?.let { Lines(arrayOf(it)) } ?: lines
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

    fun withLines(newLines: Lines) = Text(
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

    override val isSignificant: Boolean get() = true

    override fun calculateBounds(revealMode: RevealMode) = if (revealMode.textIsVisible(visibility)) {
        Bounds.fromCorners(insertX, insertY, insertX, insertY)
    } else {
        Bounds.EMPTY
    }

    override fun inside(bounds: Bounds) = false

    companion object : Creator {
        const val TOKEN = "T"

        override fun read(params: Array<String>, reader: Reader) = Text(
            insertX = params[1].toInt(),
            insertY = params[2].toInt(),
            color = ColorIndex(params[3].toInt()),
            size = params[4].toInt(),
            visibility = Visibility.fromFileValue(params[5].toInt()),
            presentation = Presentation.fromFileValue(params[6].toInt()),
            rotation = params[7].toInt(),
            alignment = Alignment.fromFileValue(params[8].toInt()),
            lines = Lines(reader.readLines(params[9].toInt()))
        )
    }

    override fun paint(drawer: Drawer, revealMode: RevealMode, selected: Boolean) {
        if (revealMode.textIsVisible(visibility)) {
            drawer.drawText(selected, revealMode.alpha(visibility), this)
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
            lines.lines.size.toString()
        )
        lines.lines.forEach { writer.writeLine(it) }
    }
}