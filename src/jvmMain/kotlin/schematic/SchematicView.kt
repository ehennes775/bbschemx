package schematic

import RedoCapable
import SaveCapable
import SelectCapable
import SelectionListener
import State
import UndoCapable
import javax.swing.JPanel

class SchematicView : JPanel(), SaveCapable, RedoCapable, SelectCapable, UndoCapable {

    private var currentState: State = State(Schematic(), setOf())
    private val redoStack = mutableListOf<State>()
    private val undoStack = mutableListOf<State>()

    private val selectionListeners = mutableListOf<SelectionListener>()

    fun addSelectionListener(listener: SelectionListener) {
        selectionListeners.add(listener)
    }

    fun removeSelectionListener(listener: SelectionListener) {
        selectionListeners.remove(listener)
    }

    fun applyToSelection(transform: (Item) -> Unit) {
    }

    override fun save() {
    }

    private fun canRedo(): Boolean {
        return redoStack.isNotEmpty()
    }

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

    override fun selectAllItems() {
        selectItems { true }
    }

    override fun unselectAllItems() {
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
}
