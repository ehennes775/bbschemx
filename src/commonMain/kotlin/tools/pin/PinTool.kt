package tools.pin

import models.schematic.shapes.pin.Pin
import types.Drawer
import types.Point
import tools.Tool
import tools.ToolFactory
import tools.ToolSettings
import tools.pin.PinTool.Companion.highestPinNumberOrZero
import views.SchematicView
import types.RevealMode

class PinTool(private val target: SchematicView) : Tool {

    override fun buttonPressed(widgetPoint: Point, drawingPoint: Point) {
        updateGeometry(drawingPoint)
        when (state) {
            State.S0 -> {
                state = State.S1;
                updateGeometry(drawingPoint)
            }
            State.S1 -> {
                prototype.addItems(target)
                reset();
            }
        }
    }

    override fun buttonReleased(widgetPoint: Point, drawingPoint: Point) {}

    override fun draw(drawer: Drawer) {
        when (state) {
            State.S0 -> Unit
            State.S1 -> prototype.paint(drawer, RevealMode.SHOWN, true)
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

    private var prototype = target.createInitialPrototype()
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
        prototype = target.createInitialPrototype()
        state = State.S0
    }

    private fun updateGeometry(drawingPoint: Point) {
        prototype = when (state) {
            State.S0 -> drawingPoint
                .snapToGrid(target.gridSize)
                .let { prototype.withValues(newX0 = it.x, newY0 = it.y) }
            State.S1 -> drawingPoint
                .snapToGrid(target.gridSize)
                //.snapOrthogonal(prototype.x0, prototype.y0)
                .let { prototype.withValues(newX1 = it.x, newY1 = it.y) }
        }
    }

    companion object : ToolFactory, ToolSettings {

        override val settings get() = this

        override fun createTool(target: SchematicView) = PinTool(target)

        override fun nextAlternativeForm() {}

        private fun SchematicView.createInitialPrototype() = this.highestPinNumberOrZero()
            .let { (it + 1).toString() }
            .let {
                PinPrototype(
                    attributeTable = mapOf(
                        "pinnumber" to it,
                        "pinseq" to it
                    )
                )
            }

        private fun SchematicView.highestPinNumberOrZero() = this.schematicModel.items
            .mapNotNull { it as? Pin }
            .flatMap { it.attributes.items }
            .asSequence()
            .filter { it.attributeNameOrNull == "pinnumber" }
            .mapNotNull { it.attributeValueOrNull?.singleOrNull()?.toIntOrNull() }
            .maxOrNull()
            .let { it ?: 0 }
    }
}