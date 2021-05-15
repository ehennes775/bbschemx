package views.schematic.types

import views.schematic.Item

interface AttributeItem {
    val attributes: Attributes

    fun withAttributes(newAttributes: Attributes): Item
}