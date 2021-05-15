package schematic.types

import schematic.Item

interface AttributeItem {
    val attributes: Attributes

    fun withAttributes(newAttributes: Attributes): Item
}