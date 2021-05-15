package views.schematic.types

import views.schematic.Item

interface LineItem : Item {
    val lineStyle: LineStyle

    fun withLineStyle(newLineStyle: LineStyle): Item
}