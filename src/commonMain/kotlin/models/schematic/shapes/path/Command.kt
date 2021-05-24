package models.schematic.shapes.path

import models.schematic.io.Writer
import types.Drawer

interface Command {
    fun paint(drawer: Drawer)
    fun write(writer: Writer)
}