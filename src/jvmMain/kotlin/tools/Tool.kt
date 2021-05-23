package tools

import models.schematic.types.Drawer

interface Tool {
    fun buttonPressed(drawingX: Int, drawingY: Int)
    fun buttonReleased(drawingX: Int, drawingY: Int)
    fun draw(drawer: Drawer)
    fun motion(drawingX: Int, drawingY: Int)
}