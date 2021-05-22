package models.schematic.shapes.path

import models.schematic.io.Writer
import models.schematic.types.Drawer

interface Command {
    fun paint(drawer: Drawer)
    fun write(writer: Writer)
}