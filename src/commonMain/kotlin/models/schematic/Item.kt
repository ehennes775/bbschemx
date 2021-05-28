package models.schematic

import models.schematic.io.Writer
import models.schematic.types.Bounds
import types.Drawer
import types.RevealMode

interface Item {
    val isSignificant: Boolean
    fun calculateBounds(revealMode: RevealMode): Bounds
    fun paint(drawer: Drawer, revealMode: RevealMode)
    fun write(writer: Writer)
}