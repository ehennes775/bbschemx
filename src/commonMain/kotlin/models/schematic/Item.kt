package models.schematic

import models.schematic.io.Writer
import models.schematic.types.Bounds

interface Item {
    fun calculateBounds(): Bounds
    fun write(writer: Writer)
}