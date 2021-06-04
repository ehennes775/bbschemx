package tools.pin

import models.schematic.types.AttributeItem
import views.SchematicView
import types.Drawer
import types.RevealMode

class PinPrototype(
    val x0: Int = 0,
    val y0: Int = 0,
    private val x1: Int = 0,
    private val y1: Int = 0,
    private val bubble: Boolean = true,
    private val radius: Int = 50,
    private val creator: PinCreator = PinCreator(),
    private val attributeTable: Map<String,String>
) {

    fun withValues(
        newX0: Int = x0,
        newY0: Int = y0,
        newX1: Int = x1,
        newY1: Int = y1,
        newBubble: Boolean = bubble,
        newRadius: Int = radius,
        newCreator: PinCreator = creator,
        newAttributeTable: Map<String,String> = attributeTable
    ) = PinPrototype(newX0, newY0, newX1, newY1, newBubble, newRadius, newCreator, newAttributeTable)

    private val items = creator.createPin(x0, y0, x1, y1, bubble, radius, attributeTable)

    fun paint(drawer: Drawer, shown: RevealMode, b: Boolean) = items.forEach {
        it.paint(drawer, shown, b)
        if (it is AttributeItem) {
            it.attributes.items.forEach { it2 -> it2.paint(drawer, shown, b)}
        }
    }

    fun repaint(target: SchematicView) = items.forEach { target.repaint(it) }

    fun addItems(target: SchematicView) = target.addItems(items)
}