package views.attribute.value

import views.attribute.name.AttributeName
import javax.swing.AbstractCellEditor
import javax.swing.JTable
import javax.swing.JTextField
import javax.swing.table.TableCellEditor

class ValueEditor: AbstractCellEditor(), TableCellEditor {

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
                else -> it.toString()
            }
        }
    }

    private val cellEditorComponent = JTextField()
}
