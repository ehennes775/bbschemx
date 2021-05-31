package tools.pin

import models.schematic.Item
import models.schematic.shapes.circle.Circle
import models.schematic.shapes.pin.Pin
import models.schematic.shapes.text.Alignment
import models.schematic.shapes.text.Presentation
import models.schematic.shapes.text.Text
import models.schematic.shapes.text.Visibility
import models.schematic.types.ColorIndex
import tools.ToolTarget
import types.Angle
import types.Drawer
import types.RevealMode
import kotlin.math.atan2
import kotlin.math.hypot
import kotlin.math.roundToInt

class PinPrototype(
    val x0: Int = 0,
    val y0: Int = 0,
    private val x1: Int = 0,
    private val y1: Int = 0,
    private val bubble: Boolean = true,
    private val radius: Int = 50
) {

    fun withValues(
        newX0: Int = x0,
        newY0: Int = y0,
        newX1: Int = x1,
        newY1: Int = y1,
        newBubble: Boolean = bubble,
        newRadius: Int = radius
    ) = PinPrototype(newX0, newY0, newX1, newY1, newBubble, newRadius)

    private val pinNumber = "1"
    private val pinType = "pas"

    private val items: List<Item> = mutableListOf<Item>().let { items ->

        val length = hypot((x0 - x1).toDouble(), (y0 - y1).toDouble())

        var tempX = x1
        var tempY = y1

        if (bubble && length > 0.0)
        {
            val u = (x0 - tempX).toDouble() / length
            val v = (y0 - tempY).toDouble() / length

            items.add(Circle(
                centerX = tempX + (radius.toDouble() * u).roundToInt(),
                centerY = tempY + (radius.toDouble() * v).roundToInt(),
                radius = radius,
                color = ColorIndex.LOGIC_BUBBLE
            ))

            tempX = x1 + (2.0 * radius.toDouble() * u).roundToInt()
            tempY = y1 + (2.0 * radius.toDouble() * v).roundToInt()
        }

        items.add(Pin(x0 = x0, y0 = y0, x1 = tempX, y1 = tempY))


        if (length > 0.0) {
            val u = (x0 - tempX).toDouble() / length
            val v = (y0 - tempY).toDouble() / length

            val rotation = Angle.normalize(Angle.fromRadians(atan2((y0 - y1).toDouble(), (x0 - x1).toDouble())))

            val alignment = when (rotation) {
                in 91..270 -> Alignment.LOWER_RIGHT
                else -> Alignment.LOWER_LEFT
            }

            val adjustedRotation = when (rotation) {
                in 91..270 -> rotation + 180
                else -> rotation
            }

            items.add(
                Text(
                    insertX = tempX + (50.0 * u).roundToInt(),
                    insertY = tempY + (50.0 * v).roundToInt(),
                    alignment = alignment,
                    color = ColorIndex.ATTRIBUTE,
                    rotation = adjustedRotation,
                    presentation = Presentation.VALUE,
                    size = 10,
                    lines = arrayOf("pinnumber=$pinNumber")
                )
            )
        }


        if (length > 0.0) {
            val u = (x0 - x1).toDouble() / length
            val v = (y0 - y1).toDouble() / length

            val rotation = Angle.normalize(Angle.fromRadians(atan2((y0 - y1).toDouble(), (x0 - x1).toDouble())))

            val alignment = when (rotation) {
                in 91..270 -> Alignment.LOWER_RIGHT
                else -> Alignment.LOWER_LEFT
            }

            val adjustedRotation = when (rotation) {
                in 91..270 -> rotation + 180
                else -> rotation
            }

            items.add(
                Text(
                    insertX = x0 + (50.0 * u).roundToInt(),
                    insertY = y0 + (50.0 * v).roundToInt(),
                    alignment = alignment,
                    color = ColorIndex.ATTRIBUTE,
                    rotation = adjustedRotation,
                    presentation = Presentation.NAME_VALUE,
                    visibility = Visibility.INVISIBLE,
                    size = 10,
                    lines = arrayOf("pinseq=$pinNumber")
                )
            )
        }

        if (length > 0.0) {
            val u = (x0 - x1).toDouble() / length
            val v = (y0 - y1).toDouble() / length

            val rotation = Angle.normalize(Angle.fromRadians(atan2((y0 - y1).toDouble(), (x0 - x1).toDouble())))

            val alignment = when (rotation) {
                in 91..270 -> Alignment.UPPER_RIGHT
                else -> Alignment.UPPER_LEFT
            }

            val adjustedRotation = when (rotation) {
                in 91..270 -> rotation + 180
                else -> rotation
            }

            items.add(
                Text(
                    insertX = x0 + (50.0 * u).roundToInt(),
                    insertY = y0 + (50.0 * v).roundToInt(),
                    alignment = alignment,
                    color = ColorIndex.ATTRIBUTE,
                    rotation = adjustedRotation,
                    presentation = Presentation.NAME_VALUE,
                    visibility = Visibility.INVISIBLE,
                    size = 10,
                    lines = arrayOf("pintype=$pinType")
                )
            )
        }

        items
    }

    fun paint(drawer: Drawer, shown: RevealMode, b: Boolean) = items.forEach { it.paint(drawer, shown, b) }

    fun repaint(target: ToolTarget) = items.forEach { target.repaint(it) }

    fun addItems(target: ToolTarget) = target.addItems(items)
}