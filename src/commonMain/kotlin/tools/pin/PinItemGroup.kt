package tools.pin

import types.Drawer
import tools.ToolTarget

interface PinItemGroup {
    val x0: Int
    val y0: Int
    fun withFirstPoint(newX: Int, newY: Int): PinItemGroup
    fun withSecondPoint(newX: Int, newY: Int): PinItemGroup
    fun draw(drawer: Drawer)
    fun repaint(target: ToolTarget)
}