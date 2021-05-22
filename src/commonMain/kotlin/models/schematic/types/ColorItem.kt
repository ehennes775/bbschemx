package models.schematic.types

import models.schematic.Item

interface ColorItem : Item {
    val color: ColorIndex

    fun withItemColor(newColor: ColorIndex): Item
}