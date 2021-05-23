package tools

import tools.dummy.DummyToolFactory
import views.document.DocumentView
import java.awt.event.ContainerEvent
import java.awt.event.ContainerListener
import javax.swing.JTabbedPane
import javax.swing.event.ChangeListener

class ToolThing(private val tabbedPane: JTabbedPane): ToolActionTarget {
    private val changeListener = ChangeListener {

    }

    private val containerListener = object: ContainerListener {
        override fun componentAdded(e: ContainerEvent?) {
            (e?.child as? ToolSource)?.addToolListener(toolListener)
            (e?.child as? ToolSource)?.let { it.tool = toolFactory.createTool(it.toolTarget) }
        }

        override fun componentRemoved(e: ContainerEvent?) {
            (e?.child as? ToolSource)?.removeToolListener(toolListener)
        }
    }

    private val toolListener = object : ToolListener {
    }

    init {
        tabbedPane.addChangeListener(changeListener)
        tabbedPane.addContainerListener(containerListener)
        tabbedPane.components
            .mapNotNull { it as? ToolSource }
            .forEach { it.addToolListener(toolListener) }
    }

    val currentDocument: DocumentView? get() = tabbedPane.selectedComponent as? DocumentView

    override var toolFactory: ToolFactory = DummyToolFactory()
        set(value) {
            field = value
            tabbedPane.components
                .mapNotNull { it as? ToolSource }
                .forEach { it.tool = toolFactory.createTool(it.toolTarget) }
        }
}