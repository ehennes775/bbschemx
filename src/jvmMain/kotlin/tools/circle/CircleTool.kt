package tools.circle

import models.schematic.shapes.circle.Circle
import models.schematic.types.Drawer
import models.schematic.types.Point
import tools.Tool
import tools.ToolTarget
import kotlin.math.roundToInt

class CircleTool(private val target: ToolTarget): Tool {

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

    private var prototype: Circle = Circle()
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
        prototype = Circle()
        state = State.S0
    }

    private fun updateGeometry(drawingPoint: Point) {
        prototype = when (state) {
            State.S0 -> drawingPoint
                .let { prototype.withCenter(it.x, it.y) }
            State.S1 -> drawingPoint
                .let { it.distanceTo(prototype.centerX, prototype.centerY).roundToInt() }
                .let { prototype.withRadius(it) }
        }
    }
}
