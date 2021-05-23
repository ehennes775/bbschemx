package actions

import views.document.DocumentView
import java.awt.event.ContainerEvent
import java.awt.event.ContainerListener
import javax.swing.AbstractAction
import javax.swing.JTabbedPane
import javax.swing.event.ChangeListener

abstract class DocumentAction(name: String, private val pane: JTabbedPane) : AbstractAction(name) {

    private val changeListener = ChangeListener {
        val oldEnabled = enabled
        enabled = calculateEnabled(currentDocument)
        firePropertyChange("enabled", oldEnabled, enabled)
    }

    private val containerListener = object: ContainerListener {
        override fun componentAdded(e: ContainerEvent?) {
            (e?.child as DocumentView)?.let {
                it.addDocumentListener(documentListener)
            }
        }

        override fun componentRemoved(e: ContainerEvent?) {
            (e?.child as DocumentView)?.let {
                it.removeDocumentListener(documentListener)
            }
        }
    }

    private val documentListener = object : DocumentListener {
        override fun documentChanged() {
        }
    }

    init {
        pane.addChangeListener(changeListener)
        pane.addContainerListener(containerListener)
        pane.components
            .mapNotNull { it as? DocumentView }
            .forEach { it.addDocumentListener(documentListener) }
    }

    val currentDocument: DocumentView get() = pane.selectedComponent as DocumentView

    abstract fun calculateEnabled(currentDocument: DocumentView): Boolean
}