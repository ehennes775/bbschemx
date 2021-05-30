package views.attribute.value

sealed class AttributeValue(private val shown: String) {

    override fun toString() = shown

    class MultipleLines() : AttributeValue("Multiple") {
    }

    class SingleLine(value: String): AttributeValue(value) {
    }

    class MultipleValues(): AttributeValue("Hello") {
    }
}
