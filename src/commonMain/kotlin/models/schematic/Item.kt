package models.schematic

import models.schematic.io.Writer
import models.schematic.types.Bounds
import types.Drawer

interface Item {
    val isSignificant: Boolean
    fun calculateBounds(): Bounds
    fun paint(drawer: Drawer)
    fun write(writer: Writer)
}