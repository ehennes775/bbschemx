package views.schematic

import models.schematic.SchematicModel
import models.schematic.types.ColorIndex
import models.schematic.types.ColorItem
import java.awt.Dimension
import javax.swing.JComboBox

class ColorEditor(schematicView: SchematicView) : PropertyEditorPanel() {

    private val fillTypes = mapOf(
        "Background" to 0,
    )

    private val colorCombo = JComboBox(fillTypes.keys.toTypedArray()).apply {
        maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)
        addActionListener(object : ApplyToSelection(schematicView) {
            override fun applyValue(model: SchematicModel) {
                selectedItem?.let { model.setItemColor(ColorIndex.GRAPHIC) }
            }
        })
    }

    init {
        addWidgets(
            colorCombo labelledAs "Color:"
        )
    }
}
