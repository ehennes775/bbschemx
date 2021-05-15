package views.schematic.types

import views.schematic.io.Writer

class Attributes(val items: List<Attribute> = listOf()) {
    fun write(writer: Writer) {
        if (items.isNotEmpty()) {
            writer.writeLine("{")
            items.forEach { it.write(writer) }
            writer.writeLine("{")
        }
    }
}