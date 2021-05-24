package tools.zoom

import models.schematic.types.Drawer
import models.schematic.types.Point
import tools.Tool
import tools.ToolTarget

class ZoomTool(private val target: ToolTarget) : Tool {

    override fun buttonPressed(widgetPoint: Point, drawingPoint: Point) {
        updateGeometry(drawingPoint)
        when (state) {
            State.S0 -> {
                state = State.S1
                updateGeometry(widgetPoint)
            }
            State.S1 -> Unit
        }
    }

    override fun buttonReleased(widgetPoint: Point, drawingPoint: Point) {
        when (state) {
            State.S0 -> Unit
            State.S1 -> {
                updateGeometry(widgetPoint)
                reset()
            }
        }
    }

    override fun draw(drawer: Drawer) {
        when (state) {
            State.S0 -> Unit
            State.S1 -> drawer.drawZoomBox(point0, point1)
        }
    }

    override fun motion(widgetPoint: Point, drawingPoint: Point) {
        when (state) {
            State.S0 -> Unit
            State.S1 -> point1 = widgetPoint
        }
    }

    private enum class State {
        S0,
        S1
    }

    private var state = State.S0

    private fun reset() {
        state = State.S0
    }

    private var point0 = Point(0, 0)
        set(value) {
            field = value
            target.repaint()
        }

    private var point1 = Point(0, 0)
        set(value) {
            field = value
            target.repaint()
        }

    private fun updateGeometry(widgetPoint: Point) {
        when (state) {
            State.S0 -> point0 = widgetPoint
            State.S1 -> point1 = widgetPoint
        }
    }
}
