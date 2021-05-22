package views.schematic

import models.schematic.SchematicModel
import models.schematic.types.CapType
import models.schematic.types.DashType
import models.schematic.types.LineItem
import models.schematic.types.SelectedValue
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
            addActionListener(object : ApplyToSelection(schematicView) {
                override fun applyValue(model: SchematicModel) {
                    selectedItem?.let { model.setLineWidth(10) }
            }
        })
    }

    init {
        schematicView.addSelectionListener(object : UpdateFromSelection<Int>() {
            override fun updateValue(model: SchematicModel) {
                model.getLineWidth().also {
                    when (it) {
                        is SelectedValue.None -> {
                            lineWidthCombo.isEnabled = false
                            lineWidthCombo.selectedItem = ""
                        }
                        is SelectedValue.Single -> {
                            lineWidthCombo.isEnabled = true
                            lineWidthCombo.selectedItem = it.value.toString()
                        }
                        is SelectedValue.Multiple -> {
                            lineWidthCombo.isEditable = true
                            lineWidthCombo.selectedItem = ""
                        }
                    }
                }
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
        addActionListener(object : ApplyToSelection(schematicView) {
            override fun applyValue(model: SchematicModel) {
                selectedItem?.let {
                    model.setDashType(dashTypes.getOrElse(it.toString()) {
                        throw Exception("Unknown cap type '$it'")
                    })
                }
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
        addActionListener(object : ApplyToSelection(schematicView) {
            override fun applyValue(model: SchematicModel) {
                selectedItem?.let { model.setDashLength(it.toString()) }
            }
        })
    }

    init {
        schematicView.addSelectionListener(object : UpdateFromSelection<Int>() {
            override fun updateValue(model: SchematicModel) {
                model.getDashLength().also {
                    when (it) {
                        is SelectedValue.None -> {
                            dashLengthCombo.isEnabled = false
                            dashLengthCombo.selectedItem = ""
                        }
                        is SelectedValue.Single -> {
                            dashLengthCombo.isEnabled = true
                            dashLengthCombo.selectedItem = it.value.toString()
                        }
                        is SelectedValue.Multiple -> {
                            dashLengthCombo.isEditable = true
                            dashLengthCombo.selectedItem = ""
                        }
                    }
                }
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
        addActionListener(object : ApplyToSelection(schematicView) {
            override fun applyValue(model: SchematicModel) {
                selectedItem?.let { model.setDashSpace(it.toString()) }
            }
        })
    }

    init {
        schematicView.addSelectionListener(object : UpdateFromSelection<Int>() {
            override fun updateValue(model: SchematicModel) {
                model.getDashSpace().also {
                    when (it) {
                        is SelectedValue.None -> {
                            dashSpaceCombo.isEnabled = false
                            dashSpaceCombo.selectedItem = ""
                        }
                        is SelectedValue.Single -> {
                            dashSpaceCombo.isEnabled = true
                            dashSpaceCombo.selectedItem = it.value.toString()
                        }
                        is SelectedValue.Multiple -> {
                            dashSpaceCombo.isEditable = true
                            dashSpaceCombo.selectedItem = ""
                        }
                    }
                }
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
        addActionListener(object : ApplyToSelection(schematicView) {
            override fun applyValue(model: SchematicModel) {
                selectedItem?.let { model.setCapType(
                    CapType.NONE
                )}
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
