package actions

import tools2.ToolThing
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent.VK_SLASH
import javax.swing.AbstractAction
import javax.swing.Action
import javax.swing.KeyStroke

class AlternateFormAction(private val toolThing: ToolThing): AbstractAction(
    "Alternate Form"
) {
    override fun actionPerformed(e: ActionEvent?) {
        toolThing.currentSettings.nextAlternativeForm()
    }

    init {
        putValue(MNEMONIC_KEY, VK_SLASH)
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('a', ActionEvent.CTRL_MASK))
    }
}