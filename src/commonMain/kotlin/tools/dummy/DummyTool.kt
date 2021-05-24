package tools.dummy

import types.Drawer
import types.Point
import tools.Tool
import tools.ToolFactory
import tools.ToolTarget

class DummyTool : Tool {

    override fun buttonPressed(widgetPoint: Point, drawingPoint: Point) {}

    override fun buttonReleased(widgetPoint: Point, drawingPoint: Point) {}

    override fun draw(drawer: Drawer) {}

    override fun motion(widgetPoint: Point, drawingPoint: Point) {}

    companion object : ToolFactory {

        override fun createTool(target: ToolTarget) = DummyTool()
    }

}
