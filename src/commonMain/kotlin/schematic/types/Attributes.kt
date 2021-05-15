package schematic.types

import schematic.Writer

class Attributes(val items: List<Attribute>) {
    fun write(writer: Writer) {
        writer.writeLine("{")
        items.forEach { it.write(writer) }
        writer.writeLine("{")
    }
}