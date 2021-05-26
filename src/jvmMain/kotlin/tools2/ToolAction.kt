package tools2

import tools.ToolFactory
import java.awt.event.ActionEvent
import javax.swing.AbstractAction
import javax.swing.ImageIcon

open class ToolAction(
    icon: ImageIcon,
    private val toolTarget: ToolActionTarget,
    private val toolFactory: ToolFactory
): AbstractAction(null, icon) {

    override fun actionPerformed(e: ActionEvent?) {
        toolTarget.toolFactory = toolFactory
    }
}
