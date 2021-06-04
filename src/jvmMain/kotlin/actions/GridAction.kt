package actions

import types.GridMode
import views.schematic.JavaSchematicView
import java.awt.event.ActionEvent
import java.awt.event.ContainerEvent
import java.awt.event.ContainerListener
import javax.swing.*

class GridAction(name: String, private val tabbedPane: JTabbedPane): AbstractAction(name) {

    var gridMode = GridMode.ON

    private val containerListener = object: ContainerListener {
        override fun componentAdded(event: ContainerEvent?) {
            event?.let { it.child as JavaSchematicView }?.let { it.gridMode = gridMode }
        }

        override fun componentRemoved(event: ContainerEvent?) {}
    }

    init {
        tabbedPane.addContainerListener(containerListener)
        tabbedPane.components
            .mapNotNull { it as? JavaSchematicView }
            .forEach { it.gridMode = gridMode }
    }

    private val selected get() = gridMode == GridMode.ON

    override fun actionPerformed(e: ActionEvent?) {
        gridMode = gridMode.next
        buttons.forEach { it.isSelected = selected }
        tabbedPane.components
            .mapNotNull { it as? JavaSchematicView }
            .forEach { it.gridMode = gridMode }
    }

    private val buttons = mutableListOf<AbstractButton>()

    fun createToolbarButton() = JToggleButton(this)
        .also{ it.isSelected = selected }
        .also{ buttons.add(it) }

    fun createMenuItem() = JCheckBoxMenuItem(this)
        .also{ it.isSelected = selected }
        .also{ buttons.add(it) }
}