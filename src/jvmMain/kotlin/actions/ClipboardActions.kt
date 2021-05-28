package actions

import java.awt.event.ActionEvent
import javax.swing.AbstractAction
import javax.swing.JTabbedPane

open class ClipboardActions(private val tabbedPane: JTabbedPane) {

    val copyAction: AbstractAction = object: AbstractAction("Copy") {
        override fun actionPerformed(e: ActionEvent?) {
            TODO("Not yet implemented")
        }
    }

    val cutAction: AbstractAction = object: AbstractAction("Cut") {
        override fun actionPerformed(e: ActionEvent?) {
            TODO("Not yet implemented")
        }
    }

    val pasteAction: AbstractAction = object: AbstractAction("Paste") {
        override fun actionPerformed(e: ActionEvent?) {
            TODO("Not yet implemented")
        }
    }
}