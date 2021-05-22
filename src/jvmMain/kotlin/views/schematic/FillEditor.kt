package views.schematic

import models.schematic.SchematicModel
import models.schematic.types.FillType
import java.awt.Dimension
import javax.swing.JComboBox

class FillEditor(schematicView: SchematicView) : PropertyEditorPanel() {

    private val fillTypes = mapOf(
        "Hollow" to FillType.HOLLOW,
        "Fill" to FillType.FILL,
        "Mesh" to FillType.MESH,
        "Hatch" to FillType.HATCH
    )

    private val fillTypeCombo = JComboBox(fillTypes.keys.toTypedArray()).apply {
        maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)
        addActionListener(object : ApplyToSelection(schematicView) {
            override fun applyValue(model: SchematicModel) {
                TODO("Not yet implemented")
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
        addActionListener(object : ApplyToSelection(schematicView) {
            override fun applyValue(model: SchematicModel) {
                TODO("Not yet implemented")
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
        addActionListener(object : ApplyToSelection(schematicView) {
            override fun applyValue(model: SchematicModel) {
                selectedItem?.let { model.setFillAngle1(it.toString()) }
            }
        })
    }

    private val fillAngle2Combo = JComboBox(fillAngles).apply {
        maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)
        addActionListener(object : ApplyToSelection(schematicView) {
            override fun applyValue(model: SchematicModel) {
                selectedItem?.let { model.setFillAngle2(it.toString()) }
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
        addActionListener(object : ApplyToSelection(schematicView) {
            override fun applyValue(model: SchematicModel) {
                selectedItem?.let { model.setFillPitch1(it.toString()) }
            }
        })
    }

    private val fillPitch2Combo = JComboBox(fillTypes.keys.toTypedArray()).apply {
        maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)
        addActionListener(object : ApplyToSelection(schematicView) {
            override fun applyValue(model: SchematicModel) {
                selectedItem?.let { model.setFillPitch2(it.toString()) }
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