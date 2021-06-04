package tools.pin

import models.schematic.Item
import models.schematic.shapes.circle.Circle
import models.schematic.shapes.pin.Pin
import models.schematic.shapes.text.Alignment
import models.schematic.shapes.text.Presentation
import models.schematic.shapes.text.Visibility
import models.schematic.types.Attributes
import models.schematic.types.ColorIndex
import kotlin.math.hypot
import kotlin.math.roundToInt

class PinCreator {

    private val creator = listOf(
        AttributeCreator(
            "pinnumber",
            "1",
            50,
            50,
            Alignment.LOWER_LEFT,
            ColorIndex.ATTRIBUTE,
            Presentation.VALUE,
            0,
            10,
            Visibility.VISIBLE
        ),
        AttributeCreator(
            "pinlabel",
            "X",
            -50,
            0,
            Alignment.CENTER_RIGHT,
            ColorIndex.GRAPHIC,
            Presentation.VALUE,
            0,
            10,
            Visibility.VISIBLE
        ),
        AttributeCreator(
            "pinseq",
            "1",
            400,
            50,
            Alignment.LOWER_LEFT,
            ColorIndex.ATTRIBUTE,
            Presentation.NAME_VALUE,
            0,
            10,
            Visibility.INVISIBLE
        ),
        AttributeCreator(
            "pintype",
            "pas",
            400,
            -50,
            Alignment.UPPER_LEFT,
            ColorIndex.ATTRIBUTE,
            Presentation.NAME_VALUE,
            0,
            10,
            Visibility.INVISIBLE
        ),
    )


    fun createPin(
        x0: Int,
        y0: Int,
        x1: Int,
        y1: Int,
        bubble: Boolean,
        bubbleRadius: Int,
        attributes: Map<String,String>
    ): List<Item> {

        val items = mutableListOf<Item>()

        val length = hypot((x0 - x1).toDouble(), (y0 - y1).toDouble())

        var tempX = x1
        var tempY = y1

        if (bubble && length > 0.0)
        {
            val u = (x0 - tempX).toDouble() / length
            val v = (y0 - tempY).toDouble() / length

            items.add(
                Circle(
                centerX = tempX + (bubbleRadius.toDouble() * u).roundToInt(),
                centerY = tempY + (bubbleRadius.toDouble() * v).roundToInt(),
                radius = bubbleRadius,
                color = ColorIndex.LOGIC_BUBBLE
            )
            )

            tempX = x1 + (2.0 * bubbleRadius.toDouble() * u).roundToInt()
            tempY = y1 + (2.0 * bubbleRadius.toDouble() * v).roundToInt()
        }

        items.add(Pin(
            x0 = x0,
            y0 = y0,
            x1 = tempX,
            y1 = tempY,
            attributes = Attributes(
                creator.map { it.createAttribute(
                    x0,
                    y0,
                    x1,
                    y1,
                    attributes[it.attributeName] ?: it.attributeDefaultValue
                )}
            )
        ))

        return items
    }


}