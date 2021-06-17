package editors

import models.schematic.types.SelectedValue
import java.awt.event.ActionListener
import javax.swing.JComboBox
import javax.swing.JTextField

abstract class PropertyEditor(val comboBox: JComboBox<*>) {

    private val actionListener = ActionListener { event ->
        if (event.actionCommand == "comboBoxChanged") {
            apply()
        }
    }

    init {
        comboBox.addActionListener(actionListener)
    }

    abstract fun apply()

    fun quietly(action: () -> Unit) {
        comboBox.removeActionListener(actionListener)
        action()
        comboBox.addActionListener(actionListener)
    }

    abstract fun update()

    fun <T> updateComboBoxItem(selectedValue: SelectedValue<T>?, locateItem: (T) -> Any?) {
        quietly {
            comboBox.isEnabled = isEnabled(selectedValue)
            comboBox.selectedItem = selectedItem(selectedValue, locateItem)
        }
    }

    fun <T> updateComboBoxText(selectedValue: SelectedValue<T>?, convertValue: (T) -> String) {
        quietly {
            comboBox.isEnabled = isEnabled(selectedValue)
            comboBox.editor.editorComponent.apply {
                require(this is JTextField)
                text = selectedText(selectedValue, convertValue)
            }
        }
    }

    companion object {
        private fun <T> isEnabled(selectedValue: SelectedValue<T>?) = when (selectedValue) {
            is SelectedValue.Multiple -> true
            is SelectedValue.None -> false
            is SelectedValue.Single -> true
            null -> false
        }

        private fun <T> selectedItem(selectedValue: SelectedValue<T>?, locateItem: (T) -> Any?) = when (selectedValue) {
            is SelectedValue.Multiple -> null
            is SelectedValue.None -> null
            is SelectedValue.Single -> locateItem(selectedValue.value)
            null -> null
        }

        private fun <T> selectedText(selectedValue: SelectedValue<T>?, convertValue: (T) -> String) = when (selectedValue) {
            is SelectedValue.Multiple -> null
            is SelectedValue.None -> null
            is SelectedValue.Single -> convertValue(selectedValue.value)
            null -> null
        }
    }
}