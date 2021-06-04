package views.schematic

import models.schematic.SchematicModel
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

internal abstract class ApplyToSelection(private val schematicView: JavaSchematicView) : ActionListener {

    override fun actionPerformed(e: ActionEvent?) {
        if (e?.actionCommand.equals("comboBoxChanged")) {
        }
    }

    abstract fun applyValue(model: SchematicModel)
}

