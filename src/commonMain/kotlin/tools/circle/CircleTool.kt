package tools.circle

import models.schematic.shapes.circle.Circle
import types.Drawer
import types.Point
import tools.Tool
import tools.ToolFactory
import tools.ToolTarget
import types.RevealMode
import kotlin.math.roundToInt

class CircleTool(private val target: ToolTarget): Tool {

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
            State.S1 -> prototype.paint(drawer, RevealMode.SHOWN, false)
        }
    }

    override fun motion(widgetPoint: Point, drawingPoint: Point) {
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
                .snapToGrid(target.gridSize)
                .let { prototype.withCenter(it.x, it.y) }
            State.S1 -> drawingPoint
                .snapToGrid(target.gridSize)
                .let { it.distanceTo(prototype.centerX, prototype.centerY).roundToInt() }
                .let { prototype.withRadius(it) }
        }
    }

    companion object : ToolFactory {

        override fun createTool(target: ToolTarget) = CircleTool(target)
    }

}
