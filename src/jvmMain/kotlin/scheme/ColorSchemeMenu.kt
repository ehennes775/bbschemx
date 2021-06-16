package scheme

import settings.JavaSettingsSource
import views.ColorScheme
import java.awt.event.ActionEvent
import javax.swing.*

class ColorSchemeMenu(private val colorScheme: ColorScheme, private val settingsSource: JavaSettingsSource) {

    val colorSchemeMenu = JMenu("Color Scheme")

    init {
        updateMenu()
    }

    private fun createMenuItems(): List<JMenuItem> {
        val actions = settingsSource.colorSchemeName.map { ColorSchemeMenuAction(it) }
        val currentColorScheme = settingsSource.currentColorScheme
        val activeAction = actions.first { it.colorSchemeName == currentColorScheme }
        val buttonGroup = ButtonGroup()

        return actions
            .map { JRadioButtonMenuItem(it) }
            .onEach { it.isSelected = it.action === activeAction }
            .onEach { buttonGroup.add(it) }
    }

    private fun updateMenu() {
        colorSchemeMenu.removeAll()
        createMenuItems().forEach { colorSchemeMenu.add(it) }
    }

    private inner class ColorSchemeMenuAction(val colorSchemeName: String): AbstractAction(colorSchemeName) {
        override fun actionPerformed(e: ActionEvent?) {
            colorScheme.colorSchemeName = colorSchemeName
        }
    }
}