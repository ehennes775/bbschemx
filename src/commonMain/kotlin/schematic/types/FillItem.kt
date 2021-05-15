package schematic.types

import schematic.Item

interface FillItem : Item {
    val fillStyle: FillStyle

    fun withFillStyle(newFillStyle: FillStyle): Item
}