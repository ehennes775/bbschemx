import schematic.Item
import schematic.Schematic

internal data class State(val schematic: Schematic, val selection: Set<Item>) {
    fun isSelected(item: Item): Boolean {
        return item in selection
    }

    fun addItems(items: List<Item>): State {
        return State(schematic, selection)
    }

    fun deleteItems(predicate: (Item) -> Boolean): State {
        return State(schematic, selection)
    }

    fun selectItems(predicate: (Item) -> Boolean): State {
        return State(schematic, selection)
    }
}