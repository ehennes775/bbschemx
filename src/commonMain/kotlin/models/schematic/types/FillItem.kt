package models.schematic.types

import models.schematic.Item

interface FillItem : Item {
    val fillStyle: FillStyle

    fun withFillStyle(newFillStyle: FillStyle): Item
}