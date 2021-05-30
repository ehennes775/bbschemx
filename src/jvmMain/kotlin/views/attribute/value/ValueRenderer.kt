package views.attribute.value

import java.awt.Component
import javax.swing.JTable
import javax.swing.table.DefaultTableCellRenderer

class ValueRenderer: DefaultTableCellRenderer() {

    override fun getTableCellRendererComponent(
        table: JTable?,
        value: Any?,
        isSelected: Boolean,
        hasFocus: Boolean,
        row: Int,
        column: Int
    ): Component {
        text = value?.let {
            when (it) {
                else -> it.toString()
            }
        }

        table?.let {
            background = if (isSelected) { it.selectionBackground } else { it.background }
        }

        return this
    }
}