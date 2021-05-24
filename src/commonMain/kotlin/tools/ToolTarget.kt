package tools

import models.schematic.Item
import models.schematic.types.Point

interface ToolTarget {
    val gridSize: Int
    fun addItem(item: Item)
    fun repaint()
    fun repaint(item: Item)
}