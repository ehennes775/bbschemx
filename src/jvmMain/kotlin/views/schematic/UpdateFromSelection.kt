package views.schematic

import models.schematic.listeners.SelectionListener
import models.schematic.SchematicModel

internal abstract class UpdateFromSelection<T> : SelectionListener {
    override fun selectionChanged() {
        TODO("Not yet implemented")
    }
    abstract fun updateValue(model: SchematicModel)
}
