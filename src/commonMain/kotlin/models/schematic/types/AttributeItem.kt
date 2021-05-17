package models.schematic.types

import models.schematic.Item

interface AttributeItem {
    val attributes: Attributes

    fun withAttributes(newAttributes: Attributes): Item
}