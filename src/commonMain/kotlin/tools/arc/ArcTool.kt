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
            State.S0 -> Unit
            else -> prototype.paint(drawer, RevealMode.SHOWN, false)
        }
    }

    override fun motion(widgetPoint: Point, drawingPoint: Point) {
        when (state) {
            State.S0 -> Unit
            else -> updateGeometry(drawingPoint)
        }
    }

    override fun removeFromListeners() {
        removeListener(this)
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

    private fun updateDirection() {
        prototype = prototype.withValues(
            newSweepAngle = arcDirection.calculateSweep(
                prototype.startAngle,
                prototype.startAngle + prototype.sweepAngle
            )
        )
    }

    private fun updateGeometry(drawingPoint: Point) {
        prototype = when (state) {
            State.S0 -> drawingPoint
                .snapToGrid(target.gridSize)
                .let { prototype.withValues(newCenterX = it.x, newCenterY = it.y) }
            State.S1 -> prototype.withValues(
                newRadius = drawingPoint
                    .snapToGrid(target.gridSize)
                    .distanceTo(prototype.centerX, prototype.centerY)
                    .roundToInt(),
                newStartAngle = drawingPoint
                    .snapToGrid(target.gridSize)
                    .let { Angle.calculateAngle(prototype.centerX, prototype.centerY, it.x, it.y) }
                    .let { Angle.fromRadians(it) }
            )
            State.S2 -> prototype.withValues(
                newStartAngle = drawingPoint
                    .let { Angle.calculateAngle(prototype.centerX, prototype.centerY, it.x, it.y) }
                    .let { Angle.fromRadians(it) }
            )
            State.S3 -> prototype.withValues(
                newSweepAngle = drawingPoint
                    .let { Angle.calculateAngle(prototype.centerX, prototype.centerY, it.x, it.y) }
                    .let { Angle.fromRadians(it) }
                    .let { arcDirection.calculateSweep(prototype.startAngle, it) }
            )
        }
    }

    companion object : ToolFactory, ToolSettings {

        private var arcDirection = ArcDirection.COUNTERCLOCKWISE
            set(value) {
                field = value
                fireSettingsChanged()
            }

        override fun nextAlternativeForm() {
            arcDirection = arcDirection.nextDirection
        }

        override val settings get() = this

        override fun createTool(target: ToolTarget) = ArcTool(target).also { addListener(it) }

        private fun createInitialArc() = Arc(
            sweepAngle = arcDirection.calculateSweep(0, 270)
        )

        private val listeners = mutableListOf<ArcTool>()

        private fun addListener(arcTool: ArcTool) {
            listeners.add(arcTool)
        }

        private fun removeListener(arcTool: ArcTool) {
            listeners.remove(arcTool)
        }

        private fun fireSettingsChanged() = listeners.forEach { it.updateDirection() }
    }
}