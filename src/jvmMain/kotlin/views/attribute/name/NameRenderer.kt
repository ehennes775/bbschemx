package views.attribute.name

import java.awt.Component
import javax.swing.JTable
import javax.swing.table.DefaultTableCellRenderer

class NameRenderer: DefaultTableCellRenderer() {

    override fun getTableCellRendererComponent(
        table: JTable?,
        value: Any?,
        isSelected: Boolean,
        hasFocus: Boolean,
        row: Int,
        column: Int
    ): Component {
        if (value is AttributeName) {
            text = value.name
        }

        table?.let {
            background = if (isSelected) { it.selectionBackground } else { it.background }
        }

        return this
    }
}