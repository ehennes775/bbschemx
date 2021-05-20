package views.schematic

import SelectionListener
import models.schematic.SchematicModel
import models.schematic.types.SelectedValue

internal abstract class UpdateFromSelection<T> : SelectionListener {
    override fun selectionChanged() {
        TODO("Not yet implemented")
    }
    abstract fun updateValue(model: SchematicModel)
}
