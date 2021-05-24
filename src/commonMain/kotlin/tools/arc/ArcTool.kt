package tools.arc

import models.schematic.shapes.arc.Arc
import models.schematic.types.Drawer
import models.schematic.types.Point
import tools.Tool
import tools.ToolTarget
import kotlin.math.roundToInt

class ArcTool(private val target: ToolTarget): Tool {

    override fun buttonPressed(widgetPoint: Point, drawingPoint: Point) {
        updateGeometry(drawingPoint)
        when (state) {
            State.S0 -> {
                state = State.S1;
                updateGeometry(drawingPoint)
            }
            State.S1 -> {
                state = State.S2;
                updateGeometry(drawingPoint)
            }
            State.S2 -> {
                state = State.S3;
                updateGeometry(drawingPoint)
            }
            State.S3 -> {
                target.addItem(prototype)
                reset();
            }
        }
    }

    override fun buttonReleased(widgetPoint: Point, drawingPoint: Point) {}

    override fun draw(drawer: Drawer) {
        when (state) {
            State.S0 -> {}
            else -> prototype.paint(drawer)
        }
    }

    override fun motion(widgetPoint: Point, drawingPoint: Point) {
        when (state) {
            State.S0 -> {}
            else -> updateGeometry(drawingPoint)
        }
    }

    private var prototype: Arc = Arc()
        set(value) {
            target.repaint(field)
            field = value
            target.repaint(field)
        }

    private enum class State {
        S0,
        S1,
        S2,
        S3
    }

    private var state = State.S0

    private fun reset() {
        prototype = Arc()
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
            State.S2 -> {
                prototype
            }
            State.S3 -> {
                prototype
            }
        }
    }
}