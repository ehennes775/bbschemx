package actions

import views.schematic.JavaSchematicView
import java.awt.event.ActionEvent
import javax.swing.AbstractAction
import javax.swing.JTabbedPane

open class UndoRedoActions(private val tabbedPane: JTabbedPane) {

    private val currentView get() = tabbedPane.selectedComponent as? JavaSchematicView

    private val canRedo get() = currentView?.canRedo ?: false

    private val canUndo get() = currentView?.canUndo ?: false

    val redoAction: AbstractAction = object: AbstractAction(
        "Redo"
    ) {
        override fun actionPerformed(e: ActionEvent?) {
            currentView?.redo()
        }
    }.apply {
        isEnabled = canRedo
    }

    val undoAction: AbstractAction = object: AbstractAction(
        "Undo"
    ) {
        override fun actionPerformed(e: ActionEvent?) {
            currentView?.undo()
        }
    }.apply {
        isEnabled = canUndo
    }
}