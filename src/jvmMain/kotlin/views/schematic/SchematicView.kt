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
import views.document.DocumentView
import views.schematic.io.JavaBasedReader
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JPanel

class SchematicView(val schematic: Schematic = Schematic()) : JPanel(), DocumentView, SaveCapable, RedoCapable, SelectCapable, UndoCapable {

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

    fun addItem(item: Item) {
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


    override fun paint(g: Graphics?) {
        super.paint(g)
        var drawer = JavaDrawer(g as Graphics2D)
        g.scale(0.25, 0.25)
        drawer.let {
            schematicModel.paint(it)
        }
    }
}
