package views.schematic

import SelectionListener

internal abstract class UpdateFromSelection<T> : SelectionListener {
    override fun selectionChanged() {
        TODO("Not yet implemented")
    }
    abstract fun updateValue(value: T)
}
