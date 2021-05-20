package models.schematic.types

import models.schematic.Item

interface LineItem : Item {
    val lineStyle: LineStyle

    fun withLineStyle(newLineStyle: LineStyle): Item

    fun applyLineStyle(apply: (LineStyle) -> LineStyle) = withLineStyle(apply(lineStyle))
}