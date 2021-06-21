package editors

import combos.color.createColorCombo
import models.schematic.SchematicModel
import models.schematic.listeners.SelectionListener
import models.schematic.shapes.pin.PinType
import models.schematic.shapes.text.Alignment
import models.schematic.types.CapType
import models.schematic.types.DashType
import models.schematic.types.FillType
import settings.JavaSettingsSource
import views.ColorComboEntry
import views.ColorScheme
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
            update()
            field?.addSelectionListener(selectionListener)
        }

    private val selectionListener = object: SelectionListener {
        override fun selectionChanged() {
            update()
        }
    }

    private fun update() {
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
        textSizeEditor.update()
        textRotationEditor.update()
        textAlignmentEditor.update()
        pinTypeEditor.update()
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
            updateComboBoxItem(selectedValue = schematicModel?.queryItemColor()) { colorIndex ->
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
            updateComboBoxText(selectedValue = schematicModel?.queryLineWidth()) { lineWidth ->
                lineWidth.toString()
            }
        }
    }

    private val dashTypeEditor = object: PropertyEditor(
        comboBox = JComboBox(dashTypes.keys.toTypedArray())
    ) {
        override fun apply() {
            comboBox.selectedItem.let { item ->
                require(item is String)
                dashTypes[item]?.let {
                    schematicModel?.setDashType(it)
                }
            }
        }

        override fun update() {
            updateComboBoxItem(selectedValue = schematicModel?.queryDashType()) { dashType ->
                dashTypes.entries.single { it.value == dashType }.key
            }
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
            updateComboBoxText(selectedValue = schematicModel?.queryDashLength()) {
                it.toString()
            }
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
            updateComboBoxText(selectedValue = schematicModel?.queryDashSpace()) { dashSpace ->
                dashSpace.toString()
            }
        }
    }

    private val capTypeEditor = object: PropertyEditor(
        comboBox = JComboBox(capTypes.keys.toTypedArray())
    ) {
        override fun apply() {
            comboBox.selectedItem.let { item ->
                require(item is String)
                capTypes[item]?.let {
                    schematicModel?.setCapType(it)
                }
            }
        }

        override fun update() {
            updateComboBoxItem(selectedValue = schematicModel?.queryCapType()) { capType ->
                capTypes.entries.single { it.value == capType }.key
            }
        }
    }

    private val fillTypeEditor = object: PropertyEditor(
        comboBox = JComboBox(fillTypes.keys.toTypedArray())
    ) {
        override fun apply() {
            comboBox.selectedItem.let { item ->
                require(item is String)
                fillTypes[item]?.let {
                    schematicModel?.setFillType(it)
                }
            }
        }

        override fun update() {
            updateComboBoxItem(selectedValue = schematicModel?.queryFillType()) { fillType ->
                fillTypes.entries.single { it.value == fillType }.key
            }
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
            updateComboBoxText(selectedValue = schematicModel?.queryFillWidth()) { fillWidth ->
                fillWidth.toString()
            }
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
            updateComboBoxText(selectedValue = schematicModel?.queryFillAngle1()) { fillAngle ->
                fillAngle.toString()
            }
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
            updateComboBoxText(selectedValue = schematicModel?.queryFillAngle2()) { fillAngle ->
                fillAngle.toString()
            }
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
            updateComboBoxText(selectedValue = schematicModel?.queryFillPitch1()) { fillPitch ->
                fillPitch.toString()
            }
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
            updateComboBoxText(selectedValue = schematicModel?.queryFillPitch2()) { fillPitch ->
                fillPitch.toString()
            }
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
            updateComboBoxItem(selectedValue = schematicModel?.queryTextColor()) { colorIndex ->
                colorScheme.colorComboTable.single { it.colorIndex == colorIndex }
            }
        }
    }

    val textSizes = listOf(8, 10, 12, 14, 16)

    private val textSizeEditor = object: PropertyEditor(
        comboBox = JComboBox(textSizes.toTypedArray()).apply { isEditable = true }
    ) {
        override fun apply() {
            comboBox.editor.editorComponent.let {
                require(it is JTextField)
                schematicModel?.setTextSize(it.text.toInt())
            }
        }

        override fun update() {
            updateComboBoxText(selectedValue = schematicModel?.queryTextSize()) { textSize ->
                textSize.toString()
            }
        }
    }

    val textRotations = listOf(0, 90, 180, 270)

    private val textRotationEditor = object: PropertyEditor(
        comboBox = JComboBox(textRotations.toTypedArray()).apply { isEditable = true }
    ) {
        override fun apply() {
            comboBox.editor.editorComponent.let {
                require(it is JTextField)
                schematicModel?.setTextRotation(it.text.toInt())
            }
        }

        override fun update() {
            updateComboBoxText(selectedValue = schematicModel?.queryTextRotation()) { textRotation ->
                textRotation.toString()
            }
        }
    }

    private val textAlignmentEditor = object: PropertyEditor(
        comboBox = JComboBox(textAlignments.keys.toTypedArray())
    ) {
        override fun apply() {
            comboBox.selectedItem.let { item ->
                require(item is String)
                textAlignments[item]?.let {
                    schematicModel?.setTextAlignment(it)
                }
            }
        }

        override fun update() {
            updateComboBoxItem(selectedValue = schematicModel?.queryTextAlignment()) { textAlignment ->
                textAlignments.entries.single { it.value == textAlignment }.key
            }
        }
    }

    private val pinTypeEditor = object: PropertyEditor(
        comboBox = JComboBox(pinTypes.keys.toTypedArray())
    ) {
        override fun apply() {
            comboBox.selectedItem.let { item ->
                require(item is String)
                pinTypes[item]?.let {
                    schematicModel?.setPinType(it)
                }
            }
        }

        override fun update() {
            updateComboBoxItem(selectedValue = schematicModel?.queryPinType()) { pinType ->
                pinTypes.entries.single { it.value == pinType }.key
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
            textColorEditor.comboBox labelledAs "Text Color:",
            textSizeEditor.comboBox labelledAs "Text Size:",
            textRotationEditor.comboBox labelledAs "Text Rotation:",
            textAlignmentEditor.comboBox labelledAs "Text Alignment:",
            pinTypeEditor.comboBox labelledAs "PinType:"
        )
    }

    init {
        update()
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
        private val capTypes = mapOf(
            "None" to CapType.NONE,
            "Square" to CapType.SQUARE,
            "Round" to CapType.ROUND
        )

        private val dashTypes = mapOf(
            "Solid" to DashType.SOLID,
            "Dotted" to DashType.DOTTED,
            "Dashed" to DashType.DASHED,
            "Center" to DashType.CENTER,
            "Phantom" to DashType.PHANTOM
        )

        private val fillTypes = mapOf(
            "Hollow" to FillType.HOLLOW,
            "Fill" to FillType.FILL,
            "Mesh" to FillType.MESH,
            "Hatch" to FillType.HATCH
        )

        private val pinTypes = mapOf(
            "Net" to PinType.NET,
            "Bus" to PinType.BUS
        )

        private val textAlignments = mapOf(
            "Upper Left" to Alignment.UPPER_LEFT,
            "Center Left" to Alignment.CENTER_LEFT,
            "Lower Left" to Alignment.LOWER_LEFT,
            "Upper Center" to Alignment.UPPER_CENTER,
            "Center Center" to Alignment.CENTER_CENTER,
            "Lower Center" to Alignment.LOWER_CENTER,
            "Upper Right" to Alignment.UPPER_RIGHT,
            "Center Right" to Alignment.CENTER_RIGHT,
            "Lower Right" to Alignment.LOWER_RIGHT,
        )
    }
}
