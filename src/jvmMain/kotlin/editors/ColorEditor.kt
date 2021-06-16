package editors

import combos.color.createColorCombo
import models.schematic.SchematicModel
import models.schematic.listeners.SelectionListener
import models.schematic.types.CapType
import models.schematic.types.DashType
import models.schematic.types.FillType
import models.schematic.types.SelectedValue
import settings.JavaSettingsSource
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
            updateColorCombo()
            field?.addSelectionListener(selectionListener)
        }

    private val selectionListener = object: SelectionListener {
        override fun selectionChanged() {
            updateColorCombo()
        }
    }

    private val itemColorCombo = createColorCombo(colorScheme, settingsSource)

    private val itemColorComboActionListener = ActionListener { event ->
        if (event.actionCommand == "comboBoxChanged") {
            itemColorCombo.selectedColorIndex?.let {
                schematicModel?.setItemColor(it)
            }
        }
    }

    init {
        itemColorCombo.addActionListener(itemColorComboActionListener)
    }

    private fun updateColorCombo() {
        itemColorCombo.removeActionListener(itemColorComboActionListener)
        // TODO set value here
        itemColorCombo.addActionListener(itemColorComboActionListener)


        schematicModel?.getItemColor().also { selectedValue ->
            when (selectedValue) {
                is SelectedValue.Multiple -> {
                    itemColorCombo.isEnabled = true
                    itemColorCombo.selectedIndex = 1
                }
                is SelectedValue.None -> {
                    itemColorCombo.isEnabled = true
                    itemColorCombo.selectedIndex = 1
                }
                is SelectedValue.Single -> {
                    itemColorCombo.isEnabled = true
                    itemColorCombo.selectedItem = colorScheme.colorComboTable.single {
                        it.colorIndex == selectedValue.value
                    }
                }
            }
        }
    }

    private val lineWidths = settingsSource.lineWidth.map { it.toString() }.toTypedArray()

    private val lineWidthCombo = JComboBox(lineWidths).apply {
        isEditable = true
        maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)
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
    }

    private val dashLengths = settingsSource.dashLength.map { it.toString() }.toTypedArray()

    private val dashLengthCombo = JComboBox(dashLengths).apply {
        isEditable = true
        maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)
    }

    private val dashSpaces = settingsSource.dashSpace.map { it.toString() }.toTypedArray()

    private val dashSpaceCombo = JComboBox(dashSpaces).apply {
        isEditable = true
        maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)
    }

    private val capTypes = mapOf(
        "None" to CapType.NONE,
        "Square" to CapType.SQUARE,
        "Round" to CapType.ROUND
    )

    private val capTypeCombo = JComboBox(capTypes.keys.toTypedArray()).apply {
        maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)
    }

    private val fillTypes = mapOf(
        "Hollow" to FillType.HOLLOW,
        "Fill" to FillType.FILL,
        "Mesh" to FillType.MESH,
        "Hatch" to FillType.HATCH
    )

    private val fillTypeCombo = JComboBox(fillTypes.keys.toTypedArray()).apply {
        maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)
    }

    private val fillWidths = arrayOf(
        "10",
        "20",
        "30"
    )

    private val fillWidthCombo = createFillWidthCombo()

    private val fillWidthComboActionListener = ActionListener { event ->
        event.eventSourceTextOrNull()?.toIntOrNull()?.let {
            schematicModel?.setFillWidth(it)
        }
    }

    init {
        fillWidthCombo.addActionListener(fillWidthComboActionListener)
    }

    private val fillAngles = arrayOf(
        "0",
        "45",
        "90"
    )

    private val fillAngle1Combo = createFillAngleCombo()

    private val fillAngle1ComboActionListener = ActionListener { event ->
        event.eventSourceTextOrNull()?.toIntOrNull()?.let {
            schematicModel?.setFillAngle1(it)
        }
    }

    init {
        fillAngle1Combo.addActionListener(fillAngle1ComboActionListener)
    }

    private val fillAngle2Combo = createFillAngleCombo()

    private val fillAngle2ComboActionListener = ActionListener { event ->
        event.eventSourceTextOrNull()?.toIntOrNull()?.let {
            schematicModel?.setFillAngle2(it)
        }
    }

    init {
        fillAngle2Combo.addActionListener(fillAngle2ComboActionListener)
    }

    private val fillPitches = arrayOf(
        "100",
        "150",
        "200"
    )

    private val fillPitch1Combo = createFillPitchCombo()

    private val fillPitch1ComboActionListener = ActionListener { event ->
        event.eventSourceTextOrNull()?.toIntOrNull()?.let {
            schematicModel?.setFillPitch1(it)
        }
    }

    init {
        fillPitch1Combo.addActionListener(fillPitch1ComboActionListener)
    }

    private val fillPitch2Combo = createFillPitchCombo()

    private val fillPitch2ComboActionListener = ActionListener { event ->
        event.eventSourceTextOrNull()?.toIntOrNull()?.let {
            schematicModel?.setFillPitch2(it)
        }
    }

    init {
        fillPitch2Combo.addActionListener(fillPitch2ComboActionListener)
    }

    private val textColorCombo = createColorCombo(colorScheme, settingsSource)

    private val textColorComboActionListener = ActionListener { event ->
        if (event.actionCommand == "comboBoxChanged") {
            itemColorCombo.selectedColorIndex?.let { schematicModel?.setItemColor(it) }
        }
    }

    init {
        textColorCombo.addActionListener(textColorComboActionListener)
    }

    init {
        addWidgets(
            itemColorCombo labelledAs "Color:",
            lineWidthCombo labelledAs "Line Width:",
            dashTypeCombo labelledAs "Dash Type:",
            dashLengthCombo labelledAs "Dash Length",
            dashSpaceCombo labelledAs "Dash Space:",
            capTypeCombo labelledAs "Cap Type:",
            fillTypeCombo labelledAs "Fill Type:",
            fillWidthCombo labelledAs "Fill Width:",
            fillAngle1Combo labelledAs "Fill Angle 1:",
            fillPitch1Combo labelledAs "Fill Pitch 1:",
            fillAngle2Combo labelledAs "Fill Angle 2:",
            fillPitch2Combo labelledAs "Fill Pitch 2:",
            textColorCombo labelledAs "Text Color:"
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

