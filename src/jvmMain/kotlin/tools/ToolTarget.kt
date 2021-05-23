package tools

import models.schematic.Item

interface ToolTarget {
    fun add(item: Item)
    fun repaint(item: Item)
}