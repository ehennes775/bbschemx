package tools.line

import models.schematic.shapes.line.Line
import models.schematic.types.Drawer
import tools.Tool
import tools.ToolTarget

class LineTool(private val target: ToolTarget) : Tool {

    override fun buttonPressed(drawingX: Int, drawingY: Int) {
        updateGeometry(drawingX, drawingY)
        when (state) {
            State.S0 -> {
                state = State.S1;
                updateGeometry(drawingX, drawingY)
            }
            State.S1 -> {
                target.add(prototype)
                reset();
            }
        }
    }

    override fun buttonReleased(drawingX: Int, drawingY: Int) {}

    override fun draw(drawer: Drawer) {
        when (state) {
            State.S0 -> {}
            State.S1 -> prototype.paint(drawer)
        }
    }

    override fun motion(drawingX: Int, drawingY: Int) {
        when (state) {
            State.S0 -> {}
            State.S1 -> updateGeometry(drawingX, drawingY)
        }
    }

    private var prototype: Line = Line()
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
        prototype = Line()
        state = State.S0
    }

    private fun updateGeometry(drawingX: Int, drawingY: Int) {
        prototype = when (state) {
            State.S0 -> prototype.withPoint0(drawingX, drawingY)
            State.S1 -> prototype.withPoint1(drawingX, drawingY)
        }
    }
}