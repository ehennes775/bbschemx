package tools.inert

import types.Drawer
import types.Point
import tools.Tool
import tools.ToolFactory
import tools.ToolSettings
import views.SchematicView

class InertTool : Tool {

    override fun buttonPressed(widgetPoint: Point, drawingPoint: Point) {}

    override fun buttonReleased(widgetPoint: Point, drawingPoint: Point) {}

    override fun draw(drawer: Drawer) {}

    override fun motion(widgetPoint: Point, drawingPoint: Point) {}

    override fun removeFromListeners() {}

    companion object : ToolFactory, ToolSettings {

        override val settings get() = this

        override fun createTool(target: SchematicView) = InertTool()

        override fun nextAlternativeForm() {}
    }

}
