package schematic.types

import schematic.Item

interface ColorItem : Item {
    val color: Int

    fun withColor(newColor: Int): Item
}