package actions

import java.awt.event.ActionEvent
import javax.swing.AbstractAction
import javax.swing.JTabbedPane

open class UndoRedoActions(private val tabbedPane: JTabbedPane) {

    val redoAction: AbstractAction = object: AbstractAction("Redo") {
        override fun actionPerformed(e: ActionEvent?) {
            TODO("Not yet implemented")
        }
    }

    val undoAction: AbstractAction = object: AbstractAction("Undo") {
        override fun actionPerformed(e: ActionEvent?) {
            TODO("Not yet implemented")
        }
    }
}