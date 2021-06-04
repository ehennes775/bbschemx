package models.schematic

import types.Drawer
import types.RevealMode

internal data class State(val schematic: Schematic, val selection: Set<Item>) {
    fun isSelected(item: Item): Boolean {
        return item in selection
    }

    fun addItems(items: List<Item>): State {
        return State(Schematic(schematic.version, schematic.items + items), selection)
    }

    fun deleteItems(predicate: (Item) -> Boolean): State {
        return State(schematic, selection)
    }

    fun selectItems(predicate: (Item) -> Boolean): State {
        return State(schematic, schematic.items.filter(predicate).toSet())
    }

    fun paint(drawer: Drawer, revealMode: RevealMode) = schematic.paint(
        drawer,
        revealMode
    ) { isSelected(it) }


}