package tools

import models.schematic.Item
import types.Point

interface ToolTarget {
    val gridSize: Int

    fun addItem(item: Item)
    fun addItems(items: List<Item>)

    fun repaint()
    fun repaint(item: Item)

    fun selectBox(point0: Point, point1: Point)
    fun zoomBox(p0: Point, p1: Point)
}