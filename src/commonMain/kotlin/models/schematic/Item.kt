package models.schematic

import models.schematic.io.Writer
import models.schematic.types.Bounds
import models.schematic.types.Drawer

interface Item {
    fun calculateBounds(): Bounds
    fun paint(drawer: Drawer)
    fun write(writer: Writer)
}