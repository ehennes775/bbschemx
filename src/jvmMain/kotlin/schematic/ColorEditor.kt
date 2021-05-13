package schematic

import LineStyleItem
import java.awt.Dimension
import javax.swing.JComboBox

class ColorEditor(schematicView: SchematicView) : PropertyEditorPanel() {

    private val fillTypes = mapOf(
        "None" to 0,
        "Square" to 1,
        "Round" to 2
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
                return if (item is LineStyleItem) item.withCapType(value) else item
            }
        })
    }

    init {
        addWidgets(
            colorCombo labelledAs "Color:"
        )
    }
}
