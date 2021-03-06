package models.schematic.shapes.complex

import models.schematic.Item
import models.schematic.io.Reader
import models.schematic.io.Writer
import models.schematic.types.*
import types.Drawer
import types.RevealMode

class Complex(
    val insertX: Int,
    val insertY: Int,
    val selectable: Boolean,
    val rotation: Int,
    val mirror: Int,
    val basename: String,
    override val attributes: Attributes = Attributes()
): Item, AttributeItem {


    override fun withAttributes(newAttributes: Attributes) = Complex(
        insertX = insertX,
        insertY = insertY,
        selectable = selectable,
        rotation = rotation,
        mirror = mirror,
        basename = basename,
        attributes = newAttributes
    )

    override val isSignificant: Boolean get() = true

    override fun calculateBounds(revealMode: RevealMode) = Bounds.EMPTY

    override fun inside(bounds: Bounds) = false

    companion object : Creator {
        const val TOKEN = "C"

        override fun read(params: Array<String>, reader: Reader) = Complex(
            params[1].toInt(),
            params[2].toInt(),
            params[3].toBoolean(),
            params[4].toInt(),
            params[5].toInt(),
            params[6]
        )
    }

    override fun paint(drawer: Drawer, revealMode: RevealMode, selected: Boolean) {
        // drawer.draw(this)
    }

    override fun write(writer: Writer) {
        writer.writeParams(
            TOKEN,
            insertX.toString(),
            insertY.toString(),
            selectable.toString(),
            rotation.toString(),
            mirror.toString(),
            basename
        )
    }
}