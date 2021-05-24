package tools.pin

import types.Drawer
import types.Point
import tools.Tool
import tools.ToolFactory
import tools.ToolTarget

class PinTool(private val target: ToolTarget) : Tool {

    override fun buttonPressed(widgetPoint: Point, drawingPoint: Point) {
        updateGeometry(drawingPoint)
        when (state) {
            State.S0 -> {
                state = State.S1;
                updateGeometry(drawingPoint)
            }
            State.S1 -> {
                //target.add(prototype)
                reset();
            }
        }
    }

    override fun buttonReleased(widgetPoint: Point, drawingPoint: Point) {}

    override fun draw(drawer: Drawer) {
        when (state) {
            State.S0 -> {}
            State.S1 -> prototype.draw(drawer)
        }
    }

    override fun motion(widgetPoint: Point, drawingPoint: Point) {
        when (state) {
            State.S0 -> {}
            State.S1 -> updateGeometry(drawingPoint)
        }
    }

    private var prototype: PinItemGroup = BasicPinItemGroup()
        set(value) {
            field.repaint(target)
            field = value
            field.repaint(target)
        }

    private enum class State {
        S0,
        S1
    }

    private var state = State.S0

    private fun reset() {
        prototype = BasicPinItemGroup()
        state = State.S0
    }

    private fun updateGeometry(drawingPoint: Point) {
        prototype = when (state) {
            State.S0 -> drawingPoint
                .snapToGrid(target.gridSize)
                .let { prototype.withFirstPoint(it.x, it.y) }
            State.S1 -> drawingPoint
                .snapToGrid(target.gridSize)
                .snapOrthogonal(prototype.x0, prototype.y0)
                .let { prototype.withSecondPoint(it.x, it.y) }
        }
    }

    companion object : ToolFactory {

        override fun createTool(target: ToolTarget) = PinTool(target)
    }
}