package models.schematic.types

import models.schematic.io.Writer

class Attributes(val items: List<Attribute> = listOf()) {

    fun paint(drawer: Drawer) {
        items.forEach { it.paint(drawer) }
    }

    fun write(writer: Writer) {
        if (items.isNotEmpty()) {
            writer.writeLine("{")
            items.forEach { it.write(writer) }
            writer.writeLine("{")
        }
    }
}