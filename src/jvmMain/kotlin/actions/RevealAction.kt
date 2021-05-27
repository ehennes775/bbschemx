package actions

import types.RevealMode
import views.schematic.SchematicView
import java.awt.event.ActionEvent
import java.awt.event.ContainerEvent
import java.awt.event.ContainerListener
import javax.swing.*

class RevealAction(name: String, private val tabbedPane: JTabbedPane): AbstractAction(name) {

    var revealMode = RevealMode.HIDDEN

    private val containerListener = object: ContainerListener {
        override fun componentAdded(event: ContainerEvent?) {
            event?.let { it.child as SchematicView }?.let { it.revealMode = revealMode }
        }

        override fun componentRemoved(event: ContainerEvent?) {}
    }

    init {
        tabbedPane.addContainerListener(containerListener)
        tabbedPane.components
            .mapNotNull { it as? SchematicView }
            .forEach { it.revealMode = revealMode }
    }

    private val selected get() = revealMode == RevealMode.SHOWN

    override fun actionPerformed(e: ActionEvent?) {
        revealMode = revealMode.next
        buttons.forEach { it.isSelected = selected }
        tabbedPane.components
            .mapNotNull { it as? SchematicView }
            .forEach { it.revealMode = revealMode }
    }

    private val buttons = mutableListOf<AbstractButton>()

    fun createToolbarButton() = JToggleButton(this)
        .also{ it.isSelected = selected }
        .also{ buttons.add(it) }

    fun createMenuItem() = JCheckBoxMenuItem(this)
        .also{ it.isSelected = selected }
        .also{ buttons.add(it) }
}