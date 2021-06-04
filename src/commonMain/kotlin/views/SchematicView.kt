package views

import models.schematic.Item
import models.schematic.SchematicModel
import types.Point

interface SchematicView {
    val gridSize: Int

    var schematicModel: SchematicModel

    fun addItem(item: Item)
    fun addItems(items: List<Item>)

    fun repaint()
    fun repaint(item: Item)

    fun selectBox(point0: Point, point1: Point)
    fun zoomBox(p0: Point, p1: Point)
}