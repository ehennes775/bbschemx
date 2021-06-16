package combos.color

import views.ColorComboEntry
import views.ColorScheme
import javax.swing.ComboBoxModel
import javax.swing.event.ListDataListener

class ColorComboAdapter(private val colorScheme: ColorScheme): ComboBoxModel<ColorComboEntry> {
    override fun getSize() = colorScheme.colorComboTable.size

    override fun getElementAt(index: Int) = colorScheme.colorComboTable[index]

    override fun addListDataListener(l: ListDataListener?) {
    }

    override fun removeListDataListener(l: ListDataListener?) {
    }

    private var selectedItem: Any = colorScheme.colorComboTable.first()

    override fun setSelectedItem(anItem: Any?) {
        anItem?.also { selectedItem = it }
    }

    override fun getSelectedItem(): Any? = selectedItem
}