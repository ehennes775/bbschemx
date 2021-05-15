package views.schematic.types

import views.schematic.Item

interface FillItem : Item {
    val fillStyle: FillStyle

    fun withFillStyle(newFillStyle: FillStyle): Item
}