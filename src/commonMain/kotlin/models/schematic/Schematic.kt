package models.schematic

import models.schematic.io.Reader
import models.schematic.io.Writer
import models.schematic.shapes.arc.Arc
import models.schematic.shapes.box.Box
import models.schematic.shapes.bus.Bus
import models.schematic.shapes.circle.Circle
import models.schematic.types.*
import models.schematic.shapes.line.Line
import models.schematic.shapes.net.Net
import models.schematic.shapes.pin.Pin
import models.schematic.shapes.text.Text

class Schematic(
    val version: Version = Version(),
    val items: List<Item> = listOf()
) {
    fun add(newItems: List<Item>) = Schematic(
        version,
        items + newItems
    )

    fun remove(predicate: (Item) -> Boolean) = Schematic(
        version,
        items.filter { predicate(it) }
    )

    companion object {
        fun read(reader: Reader): Schematic {
            var items = mutableListOf<Item>()
            var params = reader.readParams()
            val version = if (params?.get(0)  == "v") {
                val temp = Version.read(params)
                params = reader.readParams()
                temp
            } else Version()
            while (params != null) {
                if (params[0] == BEGIN_ATTRIBUTES) {
                    val tempItem = items.lastOrNull()
                    if (tempItem is AttributeItem) {
                        params = reader.readParams()
                        val attributes = mutableListOf<Attribute>()
                        if (params == null) throw Exception()
                        while (params!![0] != END_ATTRIBUTES) {
                            readItem(params, reader).also {
                                if (it !is Attribute) {
                                    throw Exception()
                                }
                                attributes.add(it)
                            }
                            params = reader.readParams()
                        }
                        items[items.lastIndex] = tempItem.withAttributes(Attributes(attributes))
                    } else {
                        throw Exception()
                    }
                } else {
                    items.add(readItem(params, reader))
                }
                params = reader.readParams()
            }
            return Schematic(version, items)
        }

        private const val BEGIN_ATTRIBUTES = "{"
        private const val END_ATTRIBUTES = "}"

        private val creators = mapOf<String, Creator>(
            Arc.TOKEN to Arc,
            Box.TOKEN to Box,
            Bus.TOKEN to Bus,
            Circle.TOKEN to Circle,
            Line.TOKEN to Line,
            Net.TOKEN to Net,
            Pin.TOKEN to Pin,
            Text.TOKEN to Text
        )

        private fun readItem(params: Array<String>, reader: Reader): Item {
            return creators.getOrElse(
                params.elementAtOrElse(0) {
                    throw Exception("Missing first parameter")
                }) {
                    throw Exception("Unknown item type '${params[0]}'")
                }
                .read(params, reader)
        }
    }

    fun write(writer: Writer) {
        version.write(writer)
        items.forEach {
            it.write(writer)
            if (it is AttributeItem) {
                it.attributes.write(writer)
            }
        }
    }
}