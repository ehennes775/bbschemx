package actions

import views.schematic.SchematicView
import java.awt.event.ActionEvent
import javax.swing.AbstractAction
import javax.swing.JTabbedPane

open class SelectActions(private val tabbedPane: JTabbedPane) {

    private val currentView get() = tabbedPane.selectedComponent as? SchematicView

    private val canSelectAll get() = currentView?.canSelectAll ?: false

    private val canSelectNone get() = currentView?.canSelectNone ?: false

    val selectAllAction: AbstractAction = object: AbstractAction(
        "Select All"
    ) {
        override fun actionPerformed(e: ActionEvent?) {
            currentView?.selectAll()
        }
    }.apply {
        isEnabled = canSelectAll
    }

    val selectNoneAction: AbstractAction = object: AbstractAction(
        "Select None"
    ) {
        override fun actionPerformed(e: ActionEvent?) {
            currentView?.selectNone()
        }
    }.apply {
        isEnabled = canSelectNone
    }
}