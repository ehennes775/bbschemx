package views.schematic

import models.schematic.Item
import models.schematic.types.ColorItem
import java.awt.Dimension
import javax.swing.JComboBox

class ColorEditor(schematicView: SchematicView) : PropertyEditorPanel() {

    private val fillTypes = mapOf(
        "Background" to 0,
    )

    private val colorCombo = JComboBox(fillTypes.keys.toTypedArray()).apply {
        maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)
        addActionListener(object : ApplyToSelection<Int>(schematicView) {
            override fun getContent(): String? {
                return selectedItem?.toString()
            }

            override fun parseValue(content: String): Int {
                return fillTypes.getOrElse(content) { throw Exception("Unknown cap type '$content'") }
            }

            override fun applyValue(item: Item, value: Int): Item {
                return if (item is ColorItem) item.withItemColor(value) else item
            }
        })
    }

    init {
        addWidgets(
            colorCombo labelledAs "Color:"
        )
    }
}
