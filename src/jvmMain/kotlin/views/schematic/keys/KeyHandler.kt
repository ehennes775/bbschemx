package views.schematic.keys

import views.schematic.SchematicView
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JTabbedPane
import javax.swing.event.ChangeListener

class KeyHandler(private val tabbedPane: JTabbedPane) {

    private var currentSchematicView: SchematicView? = null
        set(value) {
            field?.removeKeyListener(keyListener)
            field = value
            field?.addKeyListener(keyListener)
        }

    private val changeListener = ChangeListener {
        currentSchematicView = tabbedPane.selectedComponent as? SchematicView
    }

    private val keyListener = object: KeyListener {
        override fun keyTyped(event: KeyEvent?) {
            println("keyTyped")
        }

        override fun keyPressed(event: KeyEvent?) {
        }

        override fun keyReleased(event: KeyEvent?) {
        }
    }

    init {
        tabbedPane.addChangeListener(changeListener)
        currentSchematicView = tabbedPane.selectedComponent as? SchematicView
    }
}