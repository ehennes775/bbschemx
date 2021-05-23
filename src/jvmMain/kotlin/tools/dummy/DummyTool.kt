package tools.dummy

import models.schematic.types.Drawer
import tools.Tool

class DummyTool : Tool {
    override fun buttonPressed(drawingX: Int, drawingY: Int) {}

    override fun buttonReleased(drawingX: Int, drawingY: Int) {}

    override fun draw(drawer: Drawer) {
    }

    override fun motion(drawingX: Int, drawingY: Int) {}
}
