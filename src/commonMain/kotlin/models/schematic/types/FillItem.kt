package models.schematic.types

import models.schematic.Item

interface FillItem : Item {
    val fillStyle: FillStyle

    fun withFillStyle(newFillStyle: FillStyle): Item

    fun applyFillStyle(apply: (FillStyle) -> FillStyle) = withFillStyle(apply(fillStyle))
}