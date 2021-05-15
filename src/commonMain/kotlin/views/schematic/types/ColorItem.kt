package views.schematic.types

import views.schematic.Item

interface ColorItem : Item {
    val color: Int

    fun withColor(newColor: Int): Item
}