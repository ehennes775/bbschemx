package schematic

import schematic.types.AttributeItem
import schematic.types.Version

class Schematic(
    val version: Version = Version(),
    val items: List<Item> = listOf()
) {
    fun add(newItems: List<Item>) = Schematic(
        version,
        items + newItems
    )

    fun remove(predicate: (Item) -> Boolean) = Schematic(
        version,
        items.filter { predicate(it) }
    )

    fun write(writer: Writer) {
        version.write(writer)
        items.forEach {
            it.write(writer)
            if (it is AttributeItem) {
                it.attributes.write(writer)
            }
        }
    }
}