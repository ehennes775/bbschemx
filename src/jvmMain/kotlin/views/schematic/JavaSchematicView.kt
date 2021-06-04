package views.schematic

import views.RedoCapable
import views.SaveCapable
import views.SelectCapable
import models.schematic.listeners.SelectionListener
import views.UndoCapable
import actions.DocumentListener
import models.schematic.listeners.InvalidateListener
import models.schematic.Item
import models.schematic.Schematic
import models.schematic.SchematicModel
import models.schematic.types.Bounds
import models.schematic.types.ColorIndex
import types.Point
import tools.Tool
import tools.ToolListener
import tools.ToolSource
import views.SchematicView
import tools.inert.InertTool
import types.GridMode
import types.RevealMode
import views.document.DocumentView
import views.schematic.io.JavaBasedReader
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.*
import java.awt.geom.AffineTransform
import java.awt.geom.Point2D
import javax.swing.JPanel
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.round
import kotlin.math.roundToInt

class JavaSchematicView(_schematic: Schematic = Schematic()) : JPanel(), DocumentView, SaveCapable, RedoCapable,
    SelectCapable, UndoCapable, ToolSource,
    SchematicView {

    init {
        background = JavaDrawer.COLORS[ColorIndex.BACKGROUND]
    }


    var gridMode: GridMode = GridMode.ON
        set(value) {
            field = value
            repaint()
        }

    override var gridSize: Int = 100
        set(value) {
            field = value
            repaint()
        }

    var revealMode: RevealMode = RevealMode.HIDDEN
        set(value) {
            field = value
            repaint()
        }


    private val invalidateListener = object : InvalidateListener {
        override fun invalidateItem(item: Item) {
            repaint()
        }
    }

    private lateinit var _schematicModel: SchematicModel

    override var schematicModel: SchematicModel
        get() = _schematicModel
        set (value) {
            _schematicModel.removeInvalidateListener(invalidateListener)
            _schematicModel = value
            _schematicModel.addInvalidateListener(invalidateListener)
            zoomExtents()
        }

    init {
        SchematicModel(_schematic).apply {
            _schematicModel = this
            schematicModel = this
        }
    }


    private val selectionListeners = mutableListOf<SelectionListener>()

    fun addSelectionListener(listener: SelectionListener) {
        selectionListeners.add(listener)
    }

    fun removeSelectionListener(listener: SelectionListener) {
        selectionListeners.remove(listener)
    }

    fun applyToSelection(transform: (Item) -> Unit) {
    }

    companion object {
        fun load(file: java.io.File): JavaSchematicView {
            val reader = JavaBasedReader(file.reader())
            val schematic = Schematic.read(reader)
            return JavaSchematicView(schematic)
        }
    }

    override fun addDocumentListener(documentListener: DocumentListener) {

    }

    override fun removeDocumentListener(documentListener: DocumentListener) {

    }

    private var firstZoom = true

    override fun paintComponent(graphics: Graphics?) {
        super.paintComponent(graphics)
        if (firstZoom) {
            zoomExtents()
            firstZoom = false
        }
        (graphics as Graphics2D).also { g ->
            val oldTransform = g.transform
            g.transform(currentTransform)
            JavaDrawer(g, oldTransform, currentTransform).also { d ->
                if (gridMode == GridMode.ON) {
                    d.drawGrid(gridSize, width, height)
                }
                schematicModel.paint(d, revealMode)
                tool.draw(d)
            }
        }
    }

    private var currentTransform = AffineTransform()


    override var tool: Tool = InertTool.createTool(this)
        set(value) {
            field.removeFromListeners()
            field = value
        }


    override val toolTarget: SchematicView get() = this

    override fun addToolListener(listener: ToolListener) {
    }

    override fun removeToolListener(listener: ToolListener) {
    }



    private val mouseListener = object: MouseListener, MouseMotionListener {

        override fun mouseClicked(e: MouseEvent?) {}

        override fun mousePressed(event: MouseEvent?) {
            event?.let { tool.buttonPressed(calculateEventPoint(it), calculateDrawingPoint(it)) }
        }

        override fun mouseReleased(event: MouseEvent?) {
            event?.let { tool.buttonReleased(calculateEventPoint(it), calculateDrawingPoint(it)) }
        }

        override fun mouseEntered(e: MouseEvent?) {}

        override fun mouseExited(e: MouseEvent?) {}

        override fun mouseDragged(event: MouseEvent?) {
            event?.let { tool.motion(calculateEventPoint(it), calculateDrawingPoint(it)) }
        }

        override fun mouseMoved(event: MouseEvent?) {
            event?.let { tool.motion(calculateEventPoint(it), calculateDrawingPoint(it)) }
        }

        private fun calculateDrawingPoint(event: MouseEvent) = event
            .let { Point2D.Double(it.x.toDouble(), it.y.toDouble()) }
            .let { currentTransform.inverseTransform(it, null) }
            .let { Point(it.x.roundToInt(), it.y.roundToInt()) }

        private fun calculateEventPoint(event: MouseEvent) = event
            .let { Point(it.x, it.y) }
    }

    init {
        addMouseListener(mouseListener)
        addMouseMotionListener(mouseListener)
    }


    override fun addItem(item: Item) = schematicModel.addItems(listOf(item))

    override fun addItems(items: List<Item>) = schematicModel.addItems(items)



    override fun repaint(item: Item) {
        repaint()
    }

    private fun Point.inverseTransform() = currentTransform.inverseTransform(
        Point2D.Double(
            this.x.toDouble(),
            this.y.toDouble()
        ), null
    )

    override fun selectBox(point0: Point, point1: Point) {
        val p0 = point0.inverseTransform()
        val p1 = point1.inverseTransform()

        val bounds = Bounds.fromCorners(p0.x, p0.y, p1.x, p1.y)

        schematicModel.selectItems(bounds)
    }

    override fun zoomBox(p0: Point, p1: Point) {
        val dx = abs(p1.x - p0.x);
        val dy = abs(p1.y - p0.y);

        if ((dx >= 1.0) && (dy >= 1.0))
        {
            zoomPoint(
                centerX = ((p1.x + p0.x) / 2.0).roundToInt(),
                centerY = ((p1.y + p0.y) / 2.0).roundToInt(),
                factor = minOf(
                    width.toDouble() / dx.toDouble(),
                    height.toDouble() / dy.toDouble()
                )
            )
        }
    }

    fun zoomExtents() {
        if ((width > 0) && (height > 0)) {
            AffineTransform().apply {
                translate(round(width / 2.0), round(height / 2.0))

                var bounds = schematicModel.calculateBounds(revealMode)

                if (bounds.empty) {
                    bounds = Bounds.fromCorners(-500, -500, 1500, 1500)
                }

                val initialScale: Double = minOf(
                    (0.9 * width / abs(bounds.width)),
                    (0.9 * height / abs(bounds.height))
                )

                val finalScale = floor(100.0 * initialScale) / 100.0;

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

    fun zoomIn() {
        zoomPoint(width / 2, height / 2, 1.25);
    }

    fun zoomOut() {
        zoomPoint(width / 2, height / 2, 0.8);
    }

    private fun zoomPoint(centerX: Int, centerY: Int, factor: Double) {
        val dxy = currentTransform.inverseTransform(
            Point2D.Double(centerX.toDouble(), centerY.toDouble()),
            Point2D.Double()
        )

        currentTransform = AffineTransform(
            currentTransform.scaleX * factor,
            currentTransform.shearY,
            currentTransform.shearX,
            currentTransform.scaleY * factor,
            round(width.toDouble() / 2.0),
            round(height.toDouble() / 2.0)
        )

        currentTransform.translate(-dxy.x, -dxy.y);

        currentTransform = AffineTransform(
            currentTransform.scaleX,
            currentTransform.shearY,
            currentTransform.shearX,
            currentTransform.scaleY,
            round(currentTransform.translateX),
            round(currentTransform.translateY)
        )

        repaint()
    }

    val canCopy = true

    fun copy() {
    }

    val canCut = true

    fun cut() {
    }

    val canPaste = true

    fun paste() {
    }

    override val canRedo: Boolean get() = schematicModel.canRedo
    override fun redo() = schematicModel.redo()

    override val canSave: Boolean get() = schematicModel.canSave
    override fun save() = schematicModel.save()

    override val canSelectAll: Boolean get() = schematicModel.canSelectAll
    override val canSelectNone: Boolean get() = schematicModel.canSelectNone
    override fun selectAll() = schematicModel.selectAll()
    override fun selectNone() = schematicModel.selectNone()

    override val canUndo: Boolean get() = schematicModel.canUndo
    override fun undo() = schematicModel.undo()

    init {
        isFocusable = true
    }
}
