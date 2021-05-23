package tools

import models.schematic.Item

interface ToolTarget {
    val gridSize: Int
    fun add(item: Item)
    fun repaint(item: Item)
}