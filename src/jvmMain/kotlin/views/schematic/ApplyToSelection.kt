package views.schematic

import models.schematic.Item
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

internal abstract class ApplyToSelection<T>(private val schematicView: SchematicView) : ActionListener {

    override fun actionPerformed(e: ActionEvent?) {
        if (e?.actionCommand.equals("comboBoxChanged")) {
            parseValue("hello")?.let {
                schematicView.applyToSelection { item -> applyValue(item, it) }
            }
        }
    }

    abstract fun getContent(): String?

    abstract fun parseValue(content: String): T

    abstract fun applyValue(item: Item, value: T): Item
}

