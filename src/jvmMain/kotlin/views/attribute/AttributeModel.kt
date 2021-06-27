package views.attribute

import models.schematic.SchematicModel
import models.schematic.types.Attribute
import views.attribute.name.AttributeName
import views.attribute.value.AttributeValue
import javax.swing.event.TableModelListener
import javax.swing.table.TableModel

class AttributeModel(private val schematicModel: SchematicModel): TableModel {

    private val rows = createRows(schematicModel)

    override fun getRowCount() = rows.size

    override fun getColumnCount() = COLUMN_NAMES.size

    override fun getColumnName(columnIndex: Int) = COLUMN_NAMES[columnIndex]

    override fun getColumnClass(columnIndex: Int): Class<*> = when (columnIndex) {
        0 -> AttributeName::class.java
        1 -> AttributeValue::class.java
        else -> String.javaClass
    }

    override fun isCellEditable(rowIndex: Int, columnIndex: Int) = true

    override fun getValueAt(rowIndex: Int, columnIndex: Int) = when (columnIndex) {
        0 -> rows[rowIndex].attributeName
        1 -> rows[rowIndex].attributeValue
        else -> "Error"
    }

    override fun setValueAt(newValue: Any?, rowIndex: Int, columnIndex: Int) {
        val name = rows[rowIndex].attributeName.name

        when (columnIndex) {
            0 -> if (newValue is String && name != newValue) {
                schematicModel.applyAttributeName(newValue) { it.attributeNameOrNull == name }
            }
            1 -> if (newValue is String) {
                schematicModel.applyAttributeValue(arrayOf(newValue)) { it.attributeNameOrNull == name }
            }
            else -> Unit
        }
    }


    private val listeners = mutableListOf<TableModelListener>()

    override fun addTableModelListener(l: TableModelListener?) {
        l?.let{ listeners.add(it) }
    }

    override fun removeTableModelListener(l: TableModelListener?) {
        l?.let { listeners.remove(it) }
    }

    companion object {

        private val COLUMN_NAMES = arrayOf("Name", "Value")

        private fun createRows(schematicModel: SchematicModel) = schematicModel.items
            .mapNotNull { it as? Attribute }
            .groupBy { it.attributeNameOrNull }
            .filterKeys { it != null }
            .map { TableRow(it.key!!, it.value) }
            .toTypedArray()
    }

}