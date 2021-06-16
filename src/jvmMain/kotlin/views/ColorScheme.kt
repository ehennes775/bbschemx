package views

import models.schematic.types.ColorIndex
import scheme.ColorEntry
import settings.JavaSettingsSource
import java.awt.Color

class ColorScheme(private val settingsSource: JavaSettingsSource) {

    var colorComboTable = createColorComboTable()
        private set

    var colorSchemeName: String
        get() = settingsSource.currentColorScheme
        set(value) {
            settingsSource.currentColorScheme = value
            updateTables()
        }

    fun lookupColor(colorIndex: ColorIndex): Color = colorTable[colorIndex] ?: Color.WHITE

    fun lookupGridColor(gridIndex: Int) = lookupColor(
        when {
            gridIndex == 0 -> ColorIndex.ORIGIN
            gridIndex % 5 == 0 -> ColorIndex.MESH_GRID_MAJOR
            else -> ColorIndex.MESH_GRID_MINOR
        }
    )

    fun lookupItemColor(colorIndex: ColorIndex, selected: Boolean) = lookupColor(
        if (selected) ColorIndex.SELECT else colorIndex
    )

    private var colorTable: Map<ColorIndex,Color> = fetchColorTable()

    private fun createColorComboTable() = settingsSource.colors.map {
        ColorComboEntry(ColorIndex(it.index), it.name, it.toColor())
    }

    private fun fetchColorTable() = settingsSource.colors.associate { ColorIndex(it.index) to it.toColor() }

    private fun updateTables() {
        colorComboTable = createColorComboTable()
        colorTable = fetchColorTable()
    }

    companion object {
        fun Color.semiTransparent() = Color(this.red, this.green, this.blue, 32)

        fun ColorEntry.toColor() = Color(this.red.toFloat(), this.green.toFloat(), this.blue.toFloat())
    }
}