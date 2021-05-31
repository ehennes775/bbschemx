package views.schematic.keys

class KeymapBuilder {

    private val keyMap = mutableMapOf<KeySequence,String>()

    fun operation(s: String, function: KeySequenceBuilder.() -> Unit) {
        KeySequenceBuilder().apply {
            function()
            keyMap[build()] = s
        }
    }
}

class KeySequenceBuilder {
    private val keys = mutableListOf<Key>()
    fun build(): KeySequence = KeySequence(keys)
    fun key(key: Char) = keys.add(Key(key))
}

fun keymap(function: KeymapBuilder.() -> Unit) {
    KeymapBuilder().apply {
        function()
    }
}

