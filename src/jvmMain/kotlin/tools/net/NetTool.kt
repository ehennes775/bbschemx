package tools.net

import models.schematic.shapes.net.Net
import models.schematic.types.Drawer
import models.schematic.types.Point
import tools.Tool
import tools.ToolTarget

class NetTool(private val target: ToolTarget) : Tool {

    override fun buttonPressed(drawingPoint: Point) {
        updateGeometry(drawingPoint)
        when (state) {
            State.S0 -> {
                state = State.S1;
                updateGeometry(drawingPoint)
            }
            State.S1 -> {
                target.add(prototype)
                reset();
            }
        }
    }

    override fun buttonReleased(drawingPoint: Point) {}

    override fun draw(drawer: Drawer) {
        when (state) {
            State.S0 -> {}
            State.S1 -> prototype.paint(drawer)
        }
    }

    override fun motion(drawingPoint: Point) {
        when (state) {
            State.S0 -> {}
            State.S1 -> updateGeometry(drawingPoint)
        }
    }

    private var prototype: Net = Net()
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
        prototype = Net()
        state = State.S0
    }

    private fun updateGeometry(drawingPoint: Point) {
        prototype = when (state) {
            State.S0 -> drawingPoint
                .snapToGrid(target.gridSize)
                .let { prototype.withValues(x0 = it.x, y0 = it.y) }
            State.S1 -> drawingPoint
                .snapToGrid(target.gridSize)
                .snapOrthogonal(prototype.x0, prototype.y0)
                .let { prototype.withValues(x1 = it.x, y1 = it.y) }
        }
    }
}