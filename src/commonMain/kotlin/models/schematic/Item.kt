package models.schematic

import models.schematic.io.Writer

interface Item {
    fun write(writer: Writer)
}