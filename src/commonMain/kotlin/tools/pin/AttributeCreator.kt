package tools.pin

import models.schematic.shapes.text.*
import models.schematic.types.ColorIndex
import types.Angle
import kotlin.math.atan2
import kotlin.math.hypot
import kotlin.math.roundToInt

class AttributeCreator(
    val attributeName: String = "pintype",
    val attributeDefaultValue: String = "pas",
    private val horizontalDisplacement: Int = 50,
    private val verticalDisplacement: Int = 50,
    private val initialAlignment: Alignment = Alignment.LOWER_LEFT,
    private val initialColor: ColorIndex = ColorIndex.ATTRIBUTE,
    private val initialPresentation: Presentation = Presentation.NAME_VALUE,
    private val initialRotation: Int = 0,
    private val initialSize: Int = 10,
    private val initialVisibility: Visibility = Visibility.INVISIBLE
) {

    fun createAttribute(
        x0: Int,
        y0: Int,
        x1: Int,
        y1: Int,
        attributeValue: String
    ): Text {

        val length = hypot((x0 - x1).toDouble(), (y0 - y1).toDouble())

        val u: Double = if (length > 0.0) (x0 - x1).toDouble() / length else 300.0
        val v: Double = if (length > 0.0) (y0 - y1).toDouble() / length else 0.0

        val rotation = Angle.normalize(Angle.fromRadians(atan2((y0 - y1).toDouble(), (x0 - x1).toDouble())))

        val adjustedVerticalDisplacement = when (rotation) {
            in 91..270 -> verticalDisplacement
            else -> -verticalDisplacement
        }
        return Text(
            insertX = x1 + (horizontalDisplacement * u + adjustedVerticalDisplacement * v).roundToInt(),
            insertY = y1 + (horizontalDisplacement * v - adjustedVerticalDisplacement * u).roundToInt(),
            alignment = when (rotation) {
                in 91..270 -> initialAlignment.mirrorX
                else -> initialAlignment
            },
            color = initialColor,
            presentation = initialPresentation,
            rotation = when (rotation) {
                in 91..270 -> rotation + 180
                else -> rotation
            },
            size = initialSize,
            visibility = initialVisibility,
            lines = Lines(arrayOf("$attributeName=$attributeValue"))
        )
    }
}
