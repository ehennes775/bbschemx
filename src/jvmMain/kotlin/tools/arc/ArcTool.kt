package tools.arc

import models.schematic.shapes.arc.Arc
import models.schematic.types.Drawer
import tools.Tool
import tools.ToolTarget
import java.awt.geom.Point2D
import kotlin.math.roundToInt

class ArcTool(private val target: ToolTarget): Tool {

    override fun buttonPressed(drawingX: Int, drawingY: Int) {
        updateGeometry(drawingX, drawingY)
        when (state) {
            State.S0 -> {
                state = State.S1;
                updateGeometry(drawingX, drawingY)
            }
            State.S1 -> {
                state = State.S2;
                updateGeometry(drawingX, drawingY)
            }
            State.S2 -> {
                state = State.S3;
                updateGeometry(drawingX, drawingY)
            }
            State.S3 -> {
                target.add(prototype)
                reset();
            }
        }
    }

    override fun buttonReleased(drawingX: Int, drawingY: Int) {}

    override fun draw(drawer: Drawer) {
        when (state) {
            State.S0 -> {}
            else -> prototype.paint(drawer)
        }
    }

    override fun motion(drawingX: Int, drawingY: Int) {
        when (state) {
            State.S0 -> {}
            else -> updateGeometry(drawingX, drawingY)
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

    private fun updateGeometry(drawingX: Int, drawingY: Int) {
        prototype = when (state) {
            State.S0 -> prototype.withCenter(drawingX, drawingY)
            State.S1 -> {
                var radius = Point2D.distance(
                    prototype.centerX.toDouble(),
                    prototype.centerY.toDouble(),
                    drawingX.toDouble(),
                    drawingY.toDouble()
                ).roundToInt()
                prototype.withRadius(radius)
            }
            State.S2 -> {
                prototype
            }
            State.S3 -> {
                prototype
            }
        }
    }
}