package views.schematic.keys

data class KeySequence(private val keys: List<Key>) {
    override fun toString() = keys
        .joinToString(
            prefix = "'", postfix = "'"
        ) { it.toString() }
}
