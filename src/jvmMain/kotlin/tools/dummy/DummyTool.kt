package tools.dummy

import models.schematic.types.Drawer
import models.schematic.types.Point
import tools.Tool

class DummyTool : Tool {
    override fun buttonPressed(drawingPoint: Point) {}

    override fun buttonReleased(drawingPoint: Point) {}

    override fun draw(drawer: Drawer) {
    }

    override fun motion(drawingPoint: Point) {}
}
