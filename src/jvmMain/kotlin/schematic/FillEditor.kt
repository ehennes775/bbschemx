package schematic

import LineStyleItem
import java.awt.Dimension
import javax.swing.JComboBox

class FillEditor(schematicView: SchematicView) : PropertyEditorPanel() {

    private val fillTypes = mapOf(
        "None" to 0,
        "Square" to 1,
        "Round" to 2
    )

    private val fillTypeCombo = JComboBox(fillTypes.keys.toTypedArray()).apply {
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

    private val fillWidths = arrayOf(
        "10",
        "20",
        "30"
    )

    private val fillWidthCombo = JComboBox(fillWidths).apply {
        isEditable = true
        maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)
        addActionListener(object : ApplyToSelection<Int>(schematicView) {
            override fun getContent(): String? {
                return selectedItem?.toString()
            }

            override fun parseValue(content: String): Int {
                return content.toIntOrNull() ?: throw Exception("Invalid fill width '$content'")
            }

            override fun applyValue(item: Item, value: Int): Item {
                return if (item is LineStyleItem) item.withLineWidth(value) else item
            }
        })
    }

    private val fillAngles = arrayOf(
        "0",
        "45",
        "90"
    )

    private val fillAngle1Combo = JComboBox(fillAngles).apply {
        maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)
        addActionListener(object : ApplyToSelection<Int>(schematicView) {
            override fun getContent(): String? {
                return selectedItem?.toString()
            }

            override fun parseValue(content: String): Int {
                return content.toIntOrNull() ?: throw Exception("Invalid fill pitch '$content'")
            }

            override fun applyValue(item: Item, value: Int): Item {
                return if (item is LineStyleItem) item.withCapType(value) else item
            }
        })
    }

    private val fillAngle2Combo = JComboBox(fillAngles).apply {
        maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)
        addActionListener(object : ApplyToSelection<Int>(schematicView) {
            override fun getContent(): String? {
                return selectedItem?.toString()
            }

            override fun parseValue(content: String): Int {
                return content.toIntOrNull() ?: throw Exception("Invalid fill pitch '$content'")
            }

            override fun applyValue(item: Item, value: Int): Item {
                return if (item is LineStyleItem) item.withCapType(value) else item
            }
        })
    }

    private val fillPitches = arrayOf(
        "100",
        "150",
        "200"
    )

    private val fillPitch1Combo = JComboBox(fillPitches).apply {
        maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)
        addActionListener(object : ApplyToSelection<Int>(schematicView) {
            override fun getContent(): String? {
                return selectedItem?.toString()
            }

            override fun parseValue(content: String): Int {
                return content.toIntOrNull() ?: throw Exception("Invalid fill pitch '$content'")
            }

            override fun applyValue(item: Item, value: Int): Item {
                return if (item is LineStyleItem) item.withCapType(value) else item
            }
        })
    }

    private val fillPitch2Combo = JComboBox(fillTypes.keys.toTypedArray()).apply {
        maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)
        addActionListener(object : ApplyToSelection<Int>(schematicView) {
            override fun getContent(): String? {
                return selectedItem?.toString()
            }

            override fun parseValue(content: String): Int {
                return content.toIntOrNull() ?: throw Exception("Invalid fill pitch '$content'")
            }

            override fun applyValue(item: Item, value: Int): Item {
                return if (item is LineStyleItem) item.withCapType(value) else item
            }
        })
    }

    init {
        addWidgets(
            fillTypeCombo labelledAs "Fill Type:",
            fillWidthCombo labelledAs "Fill Width:",
            fillAngle1Combo labelledAs "Fill Angle 1:",
            fillPitch1Combo labelledAs "Fill Pitch 1:",
            fillAngle2Combo labelledAs "Fill Angle 2:",
            fillPitch2Combo labelledAs "Fill Pitch 2:",
        )
    }
}