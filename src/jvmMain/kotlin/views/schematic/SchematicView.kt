package views.schematic

import RedoCapable
import SaveCapable
import SelectCapable
import models.schematic.listeners.SelectionListener
import State
import UndoCapable
import actions.DocumentListener
import models.schematic.listeners.InvalidateListener
import models.schematic.Item
import models.schematic.Schematic
import models.schematic.SchematicModel
import models.schematic.types.Bounds
import models.schematic.types.ColorIndex
import models.schematic.types.Point
import tools.Tool
import tools.ToolListener
import tools.ToolSource
import tools.ToolTarget
import tools.line.LineTool
import views.document.DocumentView
import views.schematic.io.JavaBasedReader
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import java.awt.geom.AffineTransform
import java.awt.geom.Point2D
import javax.swing.JPanel
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.round
import kotlin.math.roundToInt

class SchematicView(_schematic: Schematic = Schematic()) : JPanel(), DocumentView, SaveCapable, RedoCapable, SelectCapable, UndoCapable, ToolSource, ToolTarget {

    init {
        background = JavaDrawer.COLORS[ColorIndex.BACKGROUND]
    }

    var schematic = _schematic
        set(value) {
            field = value
            schematicModel = SchematicModel(field)
            zoomExtents()
        }


    private var currentState: State = State(Schematic(), setOf())
    private val redoStack = mutableListOf<State>()
    private val undoStack = mutableListOf<State>()

    private val selectionListeners = mutableListOf<SelectionListener>()


    private val invalidateListener = object : InvalidateListener {
        override fun invalidateItem(item: Item) {
            repaint()
        }
    }




    fun addSelectionListener(listener: SelectionListener) {
        selectionListeners.add(listener)
    }

    fun removeSelectionListener(listener: SelectionListener) {
        selectionListeners.remove(listener)
    }

    fun applyToSelection(transform: (Item) -> Unit) {
    }


    private var _schematicModel: SchematicModel = SchematicModel(schematic)

    private var schematicModel: SchematicModel
        get() = _schematicModel
        set (value) {
            _schematicModel.removeInvalidateListener(invalidateListener)
            _schematicModel = value
            _schematicModel.addInvalidateListener(invalidateListener)
            zoomExtents()
        }

    init {
        schematicModel = SchematicModel(schematic)
    }



    override val canSave: Boolean
        get() = false

    override fun save() {
    }

    private fun canRedo(): Boolean {
        return redoStack.isNotEmpty()
    }

    override val canRedo: Boolean
        get() = true

    override fun redo() {
        if (redoStack.isNotEmpty()) {
            undoStack.add(0, currentState)
            currentState = redoStack.removeFirst()
        }
    }

    private fun canUndo(): Boolean {
        return undoStack.isNotEmpty()
    }

    override fun undo() {
        if (undoStack.isNotEmpty()) {
            redoStack.add(0, currentState)
            currentState = undoStack.removeFirst()
        }
    }

    override fun addItem(item: Item) {
        addItems(listOf(item))
    }

    fun addItems(items: List<Item>) {
        val nextState = currentState.addItems(items)
        undoStack.add(currentState)
        currentState = nextState
        redoStack.clear()
    }

    fun deleteSelectedItems() {
        deleteItems { currentState.isSelected(it) }
    }

    override val canSelectAll: Boolean
        get() = true

    override val canSelectNone: Boolean
        get() = true

    override fun selectAll() {
        selectItems { true }
    }

    override fun selectNone() {
        selectItems { false }
    }


    private fun deleteItems(predicate: (Item) -> Boolean) {
        val nextState = currentState.deleteItems(predicate)
        undoStack.add(currentState)
        currentState = nextState
        redoStack.clear()
    }

    private fun selectItems(predicate: (Item) -> Boolean) {
        val nextState = currentState.selectItems(predicate)
        undoStack.add(currentState)
        currentState = nextState
        redoStack.clear()
    }

    companion object {
        fun load(file: java.io.File): SchematicView {
            val reader = JavaBasedReader(file.reader())
            val schematic = Schematic.read(reader)
            return SchematicView(schematic)
        }
    }

    override fun addDocumentListener(documentListener: DocumentListener) {

    }

    override fun removeDocumentListener(documentListener: DocumentListener) {

    }

    override fun paintComponent(graphics: Graphics?) {
        super.paintComponent(graphics)
        (graphics as Graphics2D).also { g ->
            val oldTransform = g.transform
            g.transform(currentTransform)
            JavaDrawer(g, oldTransform).also { d ->
                schematicModel.paint(d)
                tool.draw(d)
            }
        }
    }

    private var currentTransform = AffineTransform()

    private fun zoomExtents() {
        if ((width > 0) && (height > 0)) {
            AffineTransform().apply {
                translate(round(width / 2.0), round(height / 2.0))

                var bounds = schematicModel.calculateBounds()

                if (bounds.empty) {
                    bounds = Bounds.fromCorners(-500, -500, 1500, 1500)
                }

                var initialScale: Double = minOf(
                    (0.9 * width / abs(bounds.width)),
                    (0.9 * height / abs(bounds.height))
                )

                var finalScale = floor(100.0 * initialScale) / 100.0;

                scale(finalScale, -finalScale);

                translate(
                    (bounds.maxX + bounds.minX + 1) / -2.0,
                    (bounds.maxY + bounds.minY + 1) / -2.0
                )

                currentTransform = AffineTransform(
                    scaleX,
                    shearY,
                    shearX,
                    scaleY,
                    round(translateX),
                    round(translateY)
                )
            }

            repaint()
        }
    }

    override var tool: Tool = LineTool(this)
    override val toolTarget: ToolTarget get() = this

    override fun addToolListener(listener: ToolListener) {
    }

    override fun removeToolListener(listener: ToolListener) {
    }



    private val mouseListener = object: MouseListener, MouseMotionListener {
        override fun mouseClicked(e: MouseEvent?) {
        }

        override fun mousePressed(event: MouseEvent?) {
            event?.let {
                val point = currentTransform.transform(Point2D.Double(event.x.toDouble(), event.y.toDouble()), null)
                tool.buttonPressed(Point(event.x, event.y), Point(point.x.roundToInt(), point.y.roundToInt()))
            }
        }

        override fun mouseReleased(event: MouseEvent?) {
            event?.let {
                val point = currentTransform.transform(Point2D.Double(event.x.toDouble(), event.y.toDouble()), null)
                tool.buttonReleased(Point(event.x, event.y), Point(point.x.roundToInt(), point.y.roundToInt()))
            }
        }

        override fun mouseEntered(e: MouseEvent?) {
        }

        override fun mouseExited(e: MouseEvent?) {
        }

        override fun mouseDragged(event: MouseEvent?) {
            event?.let {
                val point = currentTransform.transform(Point2D.Double(event.x.toDouble(), event.y.toDouble()), null)
                tool.motion(Point(event.x, event.y), Point(point.x.roundToInt(), point.y.roundToInt()))
            }
        }

        override fun mouseMoved(event: MouseEvent?) {
            event?.let {
                val point = currentTransform.transform(Point2D.Double(event.x.toDouble(), event.y.toDouble()), null)
                tool.motion(Point(event.x, event.y), Point(point.x.roundToInt(), point.y.roundToInt()))
            }
        }
    }

    init {
        addMouseListener(mouseListener)
        addMouseMotionListener(mouseListener)
    }

    override val gridSize: Int = 100



    override fun repaint(item: Item) {
        repaint()
    }
}
