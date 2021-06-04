package actions

import views.schematic.JavaSchematicView
import java.awt.event.ActionEvent
import javax.swing.AbstractAction
import javax.swing.JTabbedPane

open class ClipboardActions(private val tabbedPane: JTabbedPane) {

    private val currentView get() = tabbedPane.selectedComponent as? JavaSchematicView

    private val canCopy get() = currentView?.canCopy ?: false

    private val canCut get() = currentView?.canCut ?: false

    private val canPaste get() = currentView?.canPaste ?: false

    val copyAction: AbstractAction = object: AbstractAction(
        "Copy"
    ) {
        override fun actionPerformed(e: ActionEvent?) {
            currentView?.copy()
        }
    }.apply {
        isEnabled = canCopy
    }

    val cutAction: AbstractAction = object: AbstractAction(
        "Cut"
    ) {
        override fun actionPerformed(e: ActionEvent?) {
            currentView?.cut()
        }
    }.apply {
        isEnabled = canCut
    }

    val pasteAction: AbstractAction = object: AbstractAction(
        "Paste"
    ) {
        override fun actionPerformed(e: ActionEvent?) {
            currentView?.paste()
        }
    }.apply {
        isEnabled = canPaste
    }
}