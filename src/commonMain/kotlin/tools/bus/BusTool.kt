package tools.bus

import models.schematic.shapes.bus.Bus
import types.Drawer
import types.Point
import tools.Tool
import tools.ToolFactory
import tools.ToolTarget

class BusTool(private val target: ToolTarget) : Tool {

    override fun buttonPressed(widgetPoint: Point, drawingPoint: Point) {
        updateGeometry(drawingPoint)
        when (state) {
            State.S0 -> {
                state = State.S1;
                updateGeometry(drawingPoint)
            }
            State.S1 -> {
                target.addItem(prototype)
                reset();
            }
        }
    }

    override fun buttonReleased(widgetPoint: Point, drawingPoint: Point) {}

    override fun draw(drawer: Drawer) {
        when (state) {
            State.S0 -> {}
            State.S1 -> prototype.paint(drawer)
        }
    }

    override fun motion(widgetPoint: Point, drawingPoint: Point) {
        when (state) {
            State.S0 -> {}
            State.S1 -> updateGeometry(drawingPoint)
        }
    }

    private var prototype: Bus = Bus()
        set(value) {
            target.repaint(field)
            field = value
            target.repaint(field)
        }

    private enum class State {
        S0,
        S1
    }

    private var state = State.S0

    private fun reset() {
        prototype = Bus()
        state = State.S0
    }

    private fun updateGeometry(drawingPoint: Point) {
        prototype = when (state) {
            State.S0 -> drawingPoint
                .snapToGrid(target.gridSize)
                .let { prototype.withPoint0(it.x, it.y) }
            State.S1 -> drawingPoint
                .snapToGrid(target.gridSize)
                .snapOrthogonal(prototype.x0, prototype.y0)
                .let { prototype.withPoint1(it.x, it.y) }
        }
    }

    companion object : ToolFactory {
        override fun createTool(target: ToolTarget) = BusTool(target)
    }

}