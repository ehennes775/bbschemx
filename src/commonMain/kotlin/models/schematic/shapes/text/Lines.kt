package models.schematic.shapes.text

class Lines(val lines: Array<String>) {

    val attributeNameOrNull = lines.firstOrNull()?.let {
        ATTRIBUTE_REGEX.find(it)?.groups?.get(NAME_GROUP)?.value
    }

    val isAttribute = attributeNameOrNull != null

    val attributeValueOrNull = (if (isAttribute) {
        lines.mapIndexed { index, line ->
            if (index == 0) {
                ATTRIBUTE_REGEX.find(line)?.groups?.get(VALUE_GROUP)?.value ?: line
            } else {
                line
            }
        }.toTypedArray()
    } else {
        null
    })?.let { Lines(it) }

    companion object {
        private const val NAME_GROUP = 2
        private const val VALUE_GROUP = 3

        private val ATTRIBUTE_REGEX = Regex("""(?<both>(?<name>.+?)=(?<value>.*))|(?<text>.+)""")
    }
}
