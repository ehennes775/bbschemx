package models.schematic.types

import models.schematic.Item
import models.schematic.shapes.text.Lines

interface Attribute: Item {

    val isAttribute: Boolean
    val attributeNameOrNull: String?
    val attributeValueOrNull: Lines?

    fun withName(newName: String): Item
    fun withValue(newValue: Array<String>): Item
}