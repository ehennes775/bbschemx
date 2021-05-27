package models.schematic.types

import models.schematic.io.Writer
import types.Drawer
import types.RevealMode

class Attributes(val items: List<Attribute> = listOf()) {

    fun paint(drawer: Drawer, revealModel: RevealMode) {
        items.forEach { it.paint(drawer, revealModel) }
    }

    fun write(writer: Writer) {
        if (items.isNotEmpty()) {
            writer.writeLine("{")
            items.forEach { it.write(writer) }
            writer.writeLine("{")
        }
    }
}