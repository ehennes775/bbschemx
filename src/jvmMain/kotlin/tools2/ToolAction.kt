package tools2

import tools.ToolFactory
import java.awt.event.ActionEvent
import javax.swing.AbstractAction

open class ToolAction(
    name: String,
    private val toolTarget: ToolActionTarget,
    private val toolFactory: ToolFactory
): AbstractAction(name) {

    override fun actionPerformed(e: ActionEvent?) {
        toolTarget.toolFactory = toolFactory
    }
}
