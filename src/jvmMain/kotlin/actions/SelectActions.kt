package actions

import java.awt.event.ActionEvent
import javax.swing.AbstractAction
import javax.swing.JTabbedPane

open class SelectActions(private val tabbedPane: JTabbedPane) {

    val selectAllAction: AbstractAction = object: AbstractAction("Select All") {
        override fun actionPerformed(e: ActionEvent?) {
            TODO("Not yet implemented")
        }
    }

    val selectNoneAction: AbstractAction = object: AbstractAction("Select None") {
        override fun actionPerformed(e: ActionEvent?) {
            TODO("Not yet implemented")
        }
    }
}