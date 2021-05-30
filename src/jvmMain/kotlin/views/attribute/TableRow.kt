package views.attribute

import models.schematic.types.Attribute
import views.attribute.name.AttributeName
import views.attribute.value.AttributeValue

class TableRow(name: String, items: List<Attribute>) {

    val attributeName = AttributeName(name)

    val attributeValue = AttributeValue.SingleLine(createValue1(items))

    companion object {

        private fun createValue1(items: List<Attribute>) = items
            .mapNotNull { it.attributeValueOrNull }
            .let { outerIt ->
                if (outerIt.any { it.size > 1 }) {
                    "Not Editable"
                } else {
                    createValue2(outerIt.map { it.first() } )
                }
            }

        private fun createValue2(values: List<String>) = values
            .distinct()
            .let {
                when (it.size) {
                    0 -> "None"
                    1 -> it.single()
                    else -> "Multiple"
                }
            }
    }
}