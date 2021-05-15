package schematic.types

import schematic.Item

interface LineItem : Item {
    val lineStyle: LineStyle

    fun withLineStyle(newLineStyle: LineStyle): Item
}