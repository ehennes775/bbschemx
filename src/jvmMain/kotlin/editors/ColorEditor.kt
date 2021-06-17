package editors

import combos.color.createColorCombo
import models.schematic.SchematicModel
import models.schematic.listeners.SelectionListener
import models.schematic.types.*
import settings.JavaSettingsSource
import views.ColorComboEntry
import views.ColorScheme
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JComboBox
import javax.swing.JTextField

class ColorEditor(
    private val colorScheme: ColorScheme,
    settingsSource: JavaSettingsSource
) : PropertyEditorPanel() {

    var schematicModel: SchematicModel? = null
        set(value) {
            field?.removeSelectionListener(selectionListener)
            field = value
            itemColorEditor.update()
            lineWidthEditor.update()
            dashTypeEditor.update()
            dashLengthEditor.update()
            dashSpaceEditor.update()
            capTypeEditor.update()
            fillTypeEditor.update()
            fillAngle1Editor.update()
            fillAngle2Editor.update()
            fillPitch1Editor.update()
            fillPitch2Editor.update()
            fillWidthEditor.update()
            textColorEditor.update()
            field?.addSelectionListener(selectionListener)
        }

    private val selectionListener = object: SelectionListener {
        override fun selectionChanged() {
            itemColorEditor.update()
            lineWidthEditor.update()
            dashTypeEditor.update()
            dashLengthEditor.update()
            dashSpaceEditor.update()
            capTypeEditor.update()
            fillTypeEditor.update()
            fillAngle1Editor.update()
            fillAngle2Editor.update()
            fillPitch1Editor.update()
            fillPitch2Editor.update()
            fillWidthEditor.update()
            textColorEditor.update()
        }
    }

    private val itemColorEditor = object: PropertyEditor(
        comboBox = createColorCombo(colorScheme, settingsSource)
    ) {
        override fun apply() {
            comboBox.selectedItem.let {
                require(it is ColorComboEntry)
                schematicModel?.setItemColor(it.colorIndex)
            }
        }

        override fun update() {
            updateComboBoxItem(selectedValue = schematicModel?.getItemColor()) { colorIndex ->
                colorScheme.colorComboTable.single { it.colorIndex == colorIndex }
            }
        }
    }

    private val lineWidths = settingsSource.lineWidth.map { it.toString() }.toTypedArray()

    private val lineWidthEditor = object: PropertyEditor(
        comboBox = JComboBox(lineWidths).apply { isEditable = true }
    ) {
        override fun apply() {
            comboBox.editor.editorComponent.let {
                require(it is JTextField)
                schematicModel?.setLineWidth(it.text.toInt())
            }
        }

        override fun update() {
            updateComboBoxText(selectedValue = schematicModel?.getLineWidth()) { lineWidth ->
                lineWidth.toString()
            }
        }
    }

    private val dashTypes = mapOf(
        "Solid" to DashType.SOLID,
        "Dotted" to DashType.DOTTED,
        "Dashed" to DashType.DASHED,
        "Center" to DashType.CENTER,
        "Phantom" to DashType.PHANTOM
    )

    private val dashTypeEditor = object: PropertyEditor(
        comboBox = JComboBox(dashTypes.keys.toTypedArray())
    ) {
        override fun apply() {
        }

        override fun update() {
        }
    }

    private val dashLengths = settingsSource.dashLength.map { it.toString() }.toTypedArray()

    private val dashLengthEditor = object: PropertyEditor(
        comboBox = JComboBox(dashLengths).apply { isEditable = true }
    ) {
        override fun apply() {
            comboBox.editor.editorComponent.let {
                require(it is JTextField)
                schematicModel?.setDashLength(it.text.toInt())
            }
        }

        override fun update() {
        }
    }


    private val dashSpaces = settingsSource.dashSpace.map { it.toString() }.toTypedArray()

    private val dashSpaceEditor = object: PropertyEditor(
        comboBox = JComboBox(dashSpaces).apply { isEditable = true }
    ) {
        override fun apply() {
            comboBox.editor.editorComponent.let {
                require(it is JTextField)
                schematicModel?.setDashSpace(it.text.toInt())
            }
        }

        override fun update() {
        }
    }

    private val capTypes = mapOf(
        "None" to CapType.NONE,
        "Square" to CapType.SQUARE,
        "Round" to CapType.ROUND
    )

    private val capTypeEditor = object: PropertyEditor(
        comboBox = JComboBox(capTypes.keys.toTypedArray())
    ) {
        override fun apply() {
        }

        override fun update() {
        }
    }

    private val fillTypes = mapOf(
        "Hollow" to FillType.HOLLOW,
        "Fill" to FillType.FILL,
        "Mesh" to FillType.MESH,
        "Hatch" to FillType.HATCH
    )

    private val fillTypeEditor = object: PropertyEditor(
        comboBox = JComboBox(fillTypes.keys.toTypedArray())
    ) {
        override fun apply() {
        }

        override fun update() {
        }
    }

    private val fillWidths = arrayOf(
        "10",
        "20",
        "30"
    )

    private val fillWidthEditor = object: PropertyEditor(
        comboBox = createFillWidthCombo()
    ) {
        override fun apply() {
            comboBox.editor.editorComponent.let {
                require(it is JTextField)
                schematicModel?.setFillWidth(it.text.toInt())
            }
        }

        override fun update() {
        }
    }

    private val fillAngles = arrayOf(
        "0",
        "45",
        "90"
    )

    private val fillAngle1Editor = object: PropertyEditor(
        comboBox = createFillAngleCombo()
    ) {
        override fun apply() {
            comboBox.editor.editorComponent.let {
                require(it is JTextField)
                schematicModel?.setFillAngle1(it.text.toInt())
            }
        }

        override fun update() {
        }
    }

    private val fillAngle2Editor = object: PropertyEditor(
        comboBox = createFillAngleCombo()
    ) {
        override fun apply() {
            comboBox.editor.editorComponent.let {
                require(it is JTextField)
                schematicModel?.setFillAngle2(it.text.toInt())
            }
        }

        override fun update() {
        }
    }

    private val fillPitches = arrayOf(
        "100",
        "150",
        "200"
    )

    private val fillPitch1Editor = object: PropertyEditor(
        comboBox = createFillPitchCombo()
    ) {
        override fun apply() {
            comboBox.editor.editorComponent.let {
                require(it is JTextField)
                schematicModel?.setFillPitch1(it.text.toInt())
            }
        }

        override fun update() {
        }
    }

    private val fillPitch2Editor = object: PropertyEditor(
        comboBox = createFillPitchCombo()
    ) {
        override fun apply() {
            comboBox.editor.editorComponent.let {
                require(it is JTextField)
                schematicModel?.setFillPitch2(it.text.toInt())
            }
        }

        override fun update() {
        }
    }

    private val textColorEditor = object: PropertyEditor(
        comboBox = createColorCombo(colorScheme, settingsSource)
    ) {
        override fun apply() {
            comboBox.selectedItem.let {
                require(it is ColorComboEntry)
                schematicModel?.setTextColor(it.colorIndex)
            }
        }

        override fun update() {
            updateComboBoxItem(selectedValue = schematicModel?.getTextColor()) { colorIndex ->
                colorScheme.colorComboTable.single { it.colorIndex == colorIndex }
            }
        }
    }

    init {
        addWidgets(
            itemColorEditor.comboBox labelledAs "Color:",
            lineWidthEditor.comboBox labelledAs "Line Width:",
            dashTypeEditor.comboBox labelledAs "Dash Type:",
            dashLengthEditor.comboBox labelledAs "Dash Length",
            dashSpaceEditor.comboBox labelledAs "Dash Space:",
            capTypeEditor.comboBox labelledAs "Cap Type:",
            fillTypeEditor.comboBox labelledAs "Fill Type:",
            fillWidthEditor.comboBox labelledAs "Fill Width:",
            fillAngle1Editor.comboBox labelledAs "Fill Angle 1:",
            fillPitch1Editor.comboBox labelledAs "Fill Pitch 1:",
            fillAngle2Editor.comboBox labelledAs "Fill Angle 2:",
            fillPitch2Editor.comboBox labelledAs "Fill Pitch 2:",
            textColorEditor.comboBox labelledAs "Text Color:"
        )
    }

    private fun createFillWidthCombo() = JComboBox(fillWidths).apply {
        isEditable = true
    }

    private fun createFillAngleCombo() = JComboBox(fillAngles).apply {
        isEditable = true
    }

    private fun createFillPitchCombo() = JComboBox(fillPitches).apply {
        isEditable = true
    }

    companion object {

        fun <E> JComboBox<E>.getTextOrNull(): String? {
            return (this.editor.editorComponent as JTextField).text
        }

        fun ActionEvent.eventSourceTextOrNull(): String? {
            if (this.actionCommand == "comboBoxChanged") {
                if (this.source is JComboBox<*>) {
                    (this.source as JComboBox<*>).getTextOrNull()?.let {
                        return it
                    }
                }
            }
            return null
        }
    }
}
