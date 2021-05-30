package views.attribute.name

import javax.swing.AbstractCellEditor
import javax.swing.JTable
import javax.swing.JTextField
import javax.swing.table.TableCellEditor

class NameEditor: AbstractCellEditor(), TableCellEditor {

    override fun getCellEditorValue(): String = cellEditorComponent.text

    override fun getTableCellEditorComponent(
        table: JTable?,
        value: Any?,
        isSelected: Boolean,
        row: Int,
        column: Int
    ) = cellEditorComponent.apply {
        text = value?.let {
            when (it) {
                is AttributeName -> it.name
                else -> it.toString()
            }
        }
    }

    private val cellEditorComponent = JTextField()
}
