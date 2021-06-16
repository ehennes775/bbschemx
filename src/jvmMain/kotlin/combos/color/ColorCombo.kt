package combos.color

import models.schematic.types.ColorIndex
import settings.SettingsSource
import views.ColorComboEntry
import views.ColorScheme
import javax.swing.JComboBox

class ColorCombo(colorScheme: ColorScheme, settingsSource: SettingsSource) : JComboBox<ColorComboEntry>(ColorComboAdapter(colorScheme)) {


    val selectedColorIndex: ColorIndex? get() = (selectedItem as? ColorComboEntry)?.colorIndex

}

fun createColorCombo(colorScheme: ColorScheme, settingsSource: SettingsSource): ColorCombo {
    return ColorCombo(colorScheme, settingsSource).apply {
        // Need to assign renderer here, for some reason
        // Assigning elsewhere seems to result in a default renderer even though the property contains the
        // specialized renderer, including inside a companion object
        renderer = ColorComboRenderer()
    }
}


