package schematic.types

import schematic.Item

interface LineItem {
    val lineStyle: LineStyle

    fun withLineStyle(newLineStyle: LineStyle): Item
}