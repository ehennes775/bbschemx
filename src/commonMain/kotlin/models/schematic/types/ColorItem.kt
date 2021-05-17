package models.schematic.types

import models.schematic.Item

interface ColorItem : Item {
    val color: Int

    fun withColor(newColor: Int): Item
}