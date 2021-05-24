package tools

import models.schematic.types.Drawer
import models.schematic.types.Point

interface Tool {
    fun buttonPressed(widgetPoint: Point, drawingPoint: Point)
    fun buttonReleased(widgetPoint: Point, drawingPoint: Point)
    fun draw(drawer: Drawer)
    fun motion(widgetPoint: Point, drawingPoint: Point)
}