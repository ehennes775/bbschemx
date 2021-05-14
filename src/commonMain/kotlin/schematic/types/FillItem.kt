package schematic.types

import schematic.Item

interface FillItem {
    val fillStyle: FillStyle

    fun withFillStyle(newFillStyle: FillStyle): Item
}