package tools.arc

import models.schematic.shapes.arc.Arc
import types.Drawer
import types.Point
import tools.Tool
import tools.ToolFactory
import tools.ToolSettings
import tools.ToolTarget
import types.Angle
import types.RevealMode
import kotlin.math.roundToInt

class ArcTool(private val target: ToolTarget): Tool {

    override fun buttonPressed(widgetPoint: Point, drawingPoint: Point) {
        updateGeometry(drawingPoint)
        when (state) {
            State.S0 -> {
                state = State.S1
                updateGeometry(drawingPoint)
            }
            State.S1 -> {
                state = State.S2
                updateGeometry(drawingPoint)
            }
            State.S2 -> {
                state = State.S3
                updateGeometry(drawingPoint)
            }
            State.S3 -> {
                target.addItem(prototype)
                reset()
            }
        }
    }

    override fun buttonReleased(widgetPoint: Point, drawingPoint: Point) {}

    override fun draw(drawer: Drawer) {
        when (state) {
            State.S0 -> {}
            else -> prototype.paint(drawer, RevealMode.SHOWN, false)
        }
    }

    override fun motion(widgetPoint: Point, drawingPoint: Point) {
        when (state) {
            State.S0 -> {}
            else -> updateGeometry(drawingPoint)
        }
    }

    override fun removeFromListeners() {
    }

    private var prototype: Arc = createInitialArc()
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
        prototype = createInitialArc()
        state = State.S0
    }

    private fun updateGeometry(drawingPoint: Point) {
        prototype = when (state) {
            State.S0 -> drawingPoint
                .snapToGrid(target.gridSize)
                .let { prototype.withValues(newCenterX = it.x, newCenterY = it.y) }
            State.S1 -> drawingPoint
                .snapToGrid(target.gridSize).distanceTo(prototype.centerX, prototype.centerY).roundToInt()
                .let { prototype.withValues(newRadius = it) }
            State.S2 -> drawingPoint
                .let { Angle.calculateAngle(prototype.centerX, prototype.centerY, it.x, it.y) }
                .let { Angle.fromRadians(it) }
                .let { prototype.withValues(newStartAngle = it) }
            State.S3 -> drawingPoint
                .let { Angle.calculateAngle(prototype.centerX, prototype.centerY, it.x, it.y) }
                .let { Angle.fromRadians(it) }
                .let { arcDirection.calculateSweep(prototype.startAngle, it) }
                .let { prototype.withValues(newSweepAngle = it) }
        }
    }

    companion object : ToolFactory, ToolSettings {

        private var arcDirection = ArcDirection.COUNTERCLOCKWISE
        // FIXME: set() -> updateGeometry

        override fun nextAlternativeForm() {
            arcDirection = arcDirection.nextDirection
        }

        override fun createTool(target: ToolTarget) = ArcTool(target)

        private fun createInitialArc() = Arc(
            sweepAngle = arcDirection.calculateSweep(0, 180)
        )
    }
}