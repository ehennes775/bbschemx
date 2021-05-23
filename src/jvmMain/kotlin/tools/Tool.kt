package tools

import models.schematic.types.Drawer
import models.schematic.types.Point

interface Tool {
    fun buttonPressed(drawingPoint: Point)
    fun buttonReleased(drawingPoint: Point)
    fun draw(drawer: Drawer)
    fun motion(drawingPoint: Point)
}