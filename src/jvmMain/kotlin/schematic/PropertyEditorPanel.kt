package schematic

import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

abstract class PropertyEditorPanel : JPanel() {

    protected data class NoNameYet(val label: String, val editor: JComponent)

    protected infix fun JComponent.labelledAs(label: String) : NoNameYet {
        return NoNameYet(label, this)
    }

    init {
        layout = GridBagLayout()
    }

    protected fun addWidgets(vararg widgets: NoNameYet) {
        val childAnchor = GridBagConstraints.CENTER
        val childFill = GridBagConstraints.HORIZONTAL
        val childInsets = Insets(5, 5, 5, 5)
        val childIPadX = 5
        val childIPadY = 5

        widgets.forEachIndexed { row, pair ->
            add(JLabel(pair.label).apply {
                Dimension(Int.MAX_VALUE, preferredSize.height).also { maximumSize = it }
            }, GridBagConstraints().apply {
                gridx = 0
                gridy = row
                anchor = childAnchor
                fill =  childFill
                ipadx = childIPadX
                ipady = childIPadY
                insets = childInsets
            })
            add(pair.editor, GridBagConstraints().apply {
                gridx = 1
                gridy = row
                anchor = childAnchor
                fill = childFill
                ipadx = childIPadX
                ipady = childIPadY
                insets = childInsets
            })
        }
    }
}