package views.schematic

import views.schematic.types.CapType
import views.schematic.types.DashType
import views.schematic.types.LineItem
import java.awt.Dimension
import javax.swing.JComboBox

class LineEditor(schematicView: SchematicView) : PropertyEditorPanel() {

    private val lineWidths = arrayOf(
        "10",
        "20",
        "30"
    )

    private val lineWidthCombo = JComboBox(lineWidths).apply {
            isEditable = true
            maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)
            addActionListener(object : ApplyToSelection<Int>(schematicView) {
                override fun getContent(): String? {
                    return selectedItem?.toString()
                }
                override fun parseValue(content: String): Int {
                    return content.toIntOrNull() ?: throw Exception("Invalid line width '$content'")
                }
                override fun applyValue(item: Item, value: Int): Item {
                    return if (item is LineItem) item.withLineStyle(item.lineStyle.withLineWidth(value)) else item
                }
            })
        }

    init {
        schematicView.addSelectionListener(object : UpdateFromSelection<Int>() {
            override fun updateValue(value: Int) {
                lineWidthCombo.selectedItem = value.toString()
            }
        })
    }

    private val dashTypes = mapOf(
        "Solid" to DashType.SOLID,
        "Dotted" to DashType.DOTTED,
        "Dashed" to DashType.DASHED,
        "Center" to DashType.CENTER,
        "Phantom" to DashType.PHANTOM
    )

    private val dashTypeCombo = JComboBox(dashTypes.keys.toTypedArray()).apply {
        maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)
        addActionListener(object : ApplyToSelection<DashType>(schematicView) {
            override fun getContent(): String? {
                return selectedItem?.toString()
            }
            override fun parseValue(content: String): DashType {
                return dashTypes.getOrElse(content) { throw Exception("Unknown cap type '$content'") }
            }
            override fun applyValue(item: Item, value: DashType): Item {
                return if (item is LineItem) item.withLineStyle(item.lineStyle.withDashType(value)) else item
            }
        })
    }

    private val dashLengths = arrayOf(
        "10",
        "20",
        "30"
    )

    private val dashLengthCombo = JComboBox(dashLengths).apply {
        isEditable = true
        maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)
        addActionListener(object : ApplyToSelection<Int>(schematicView) {
            override fun getContent(): String? {
                return selectedItem?.toString()
            }
            override fun parseValue(content: String): Int {
                return content.toIntOrNull() ?: throw Exception("Invalid dash length '$content'")
            }
            override fun applyValue(item: Item, value: Int): Item {
                return if (item is LineItem) item.withLineStyle(item.lineStyle.withDashLength(value)) else item
            }
        })
    }

    init {
        schematicView.addSelectionListener(object : UpdateFromSelection<Int>() {
            override fun updateValue(value: Int) {
                dashLengthCombo.selectedItem = value.toString()
            }
        })
    }

    private val dashSpaces = arrayOf(
        "10",
        "20",
        "30"
    )

    private val dashSpaceCombo = JComboBox(dashSpaces).apply {
        isEditable = true
        maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)
        addActionListener(object : ApplyToSelection<Int>(schematicView) {
            override fun getContent(): String? {
                return selectedItem?.toString()
            }
            override fun parseValue(content: String): Int {
                return content.toIntOrNull() ?: throw Exception("Invalid dash space '$content'")
            }
            override fun applyValue(item: Item, value: Int): Item {
                return if (item is LineItem) item.withLineStyle(item.lineStyle.withDashSpace(value)) else item
            }
        })
    }

    init {
        schematicView.addSelectionListener(object : UpdateFromSelection<Int>() {
            override fun updateValue(value: Int) {
                dashSpaceCombo.selectedItem = value.toString()
            }
        })
    }

    private val capTypes = mapOf(
        "None" to CapType.NONE,
        "Square" to CapType.SQUARE,
        "Round" to CapType.ROUND
    )

    private val capTypeCombo = JComboBox(capTypes.keys.toTypedArray()).apply {
        maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)
        addActionListener(object : ApplyToSelection<CapType>(schematicView) {
            override fun getContent(): String? {
                return selectedItem?.toString()
            }
            override fun parseValue(content: String): CapType {
                return capTypes.getOrElse(content) { throw Exception("Unknown cap type '$content'") }
            }
            override fun applyValue(item: Item, value: CapType): Item {
                return if (item is LineItem) item.withLineStyle(item.lineStyle.withCapType(value)) else item
            }
        })
    }

    init {
        addWidgets(
            lineWidthCombo labelledAs "Line Width:",
            dashTypeCombo labelledAs "Dash Type:",
            dashLengthCombo labelledAs "Dash Length",
            dashSpaceCombo labelledAs "Dash Space:",
            capTypeCombo labelledAs "Cap Type:"
        )
    }
}
