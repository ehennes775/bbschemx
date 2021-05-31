package tools.box

import models.schematic.shapes.box.Box
import types.Drawer
import types.Point
import tools.Tool
import tools.ToolFactory
import tools.ToolSettings
import tools.ToolTarget
import types.RevealMode

class BoxTool(private val target: ToolTarget) : Tool {

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

    override fun removeFromListeners() {
    }

    private var prototype: Box = Box()
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
        prototype = Box()
        state = State.S0
    }

    private fun updateGeometry(drawingPoint: Point) {
        prototype = when (state) {
            State.S0 -> drawingPoint
                .snapToGrid(target.gridSize)
                .let { prototype.withValues(newLowerX = it.x, newLowerY = it.y) }
            State.S1 -> drawingPoint
                .snapToGrid(target.gridSize)
                .let { prototype.withValues(newUpperX = it.x, newUpperY = it.y) }
        }
    }

    companion object : ToolFactory, ToolSettings {

        override val settings get() = this

        override fun createTool(target: ToolTarget) = BoxTool(target)

        override fun nextAlternativeForm() {}
    }
}