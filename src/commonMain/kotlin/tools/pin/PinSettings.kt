package tools.pin

import models.schematic.shapes.text.Text
import models.schematic.types.ColorIndex

class PinSettings(
    val initialBubbleColor: ColorIndex = ColorIndex.LOGIC_BUBBLE,
    val initialPinColor: ColorIndex = ColorIndex.PIN,
    val bubbleRadius: Int = 50
) {

    fun getDefaultAttributes(): Map<String,String> {
        return mapOf()
    }

    fun createPin(
        x0: Int,
        y0: Int,
        x1: Int,
        y1: Int,
        attributeValues: Map<String,String> = getDefaultAttributes()
    ): Unit {

    }
}
