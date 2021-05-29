package models.schematic.types

import models.schematic.Item
import models.schematic.io.Writer
import types.Drawer
import types.RevealMode

class Attributes(val items: List<Attribute> = listOf()) {

    fun paint(drawer: Drawer, revealModel: RevealMode, selected: (Item) -> Boolean) {
        items.forEach { it.paint(drawer, revealModel, selected(it)) }
    }

    fun write(writer: Writer) {
        if (items.isNotEmpty()) {
            writer.writeLine("{")
            items.forEach { it.write(writer) }
            writer.writeLine("{")
        }
    }
}