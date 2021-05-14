package schematic.types

import schematic.Item

interface ColorItem {
    val color: Int

    fun withColor(newColor: Int): Item
}