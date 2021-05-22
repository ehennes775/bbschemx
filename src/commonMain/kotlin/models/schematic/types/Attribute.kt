package models.schematic.types

import models.schematic.Item

interface Attribute: Item {
    val isAttribute: Boolean
    val attributeNameOrNull: String?
    val attributeValueOrNull: Array<String>?
}