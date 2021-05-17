package models.schematic.types

import models.schematic.Item

interface LineItem : Item {
    val lineStyle: LineStyle

    fun withLineStyle(newLineStyle: LineStyle): Item
}