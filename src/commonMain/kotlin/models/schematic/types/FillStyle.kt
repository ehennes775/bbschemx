package models.schematic.types

class FillStyle(
    val fillType: FillType = FillType.HOLLOW,
    val fillWidth: Int = DEFAULT_WIDTH,
    val fillAngle1: Int = DEFAULT_ANGLE1,
    val fillPitch1: Int = DEFAULT_PITCH,
    val fillAngle2: Int = DEFAULT_ANGLE2,
    val fillPitch2: Int = DEFAULT_PITCH
) {

    fun withValues(
        newFillType: FillType = fillType,
        newFillWidth: Int = fillWidth,
        newFillAngle1: Int = fillAngle1,
        newFillPitch1: Int = fillPitch1,
        newFillAngle2: Int = fillAngle2,
        newFillPitch2: Int = fillPitch2
    ) = FillStyle(newFillType, newFillWidth, newFillAngle1, newFillPitch1, newFillAngle2, newFillPitch2)

    val fillTypeFileValue get() = fillType.fileValue

    val fillWidthFileValue get() = if (fillType.usesFillWidth) fillWidth else UNUSED_VALUE

    val fillAngle1FileValue get() = if (fillType.usesFirstSet) fillAngle1 else UNUSED_VALUE

    val fillPitch1FileValue get() = if (fillType.usesFirstSet) fillPitch1 else UNUSED_VALUE

    val fillAngle2FileValue get() = if (fillType.usesSecondSet) fillAngle2 else UNUSED_VALUE

    val fillPitch2FileValue get() = if (fillType.usesSecondSet) fillPitch2 else UNUSED_VALUE

    companion object {

        private const val DEFAULT_ANGLE1 = 45

        private const val DEFAULT_ANGLE2 = 135

        private const val DEFAULT_PITCH = 100

        private const val DEFAULT_WIDTH = 10

        private const val UNUSED_VALUE = -1

        fun fromFileParams(
            fillType: String,
            fillWidth: String,
            fillAngle1: String,
            fillPitch1: String,
            fillAngle2: String,
            fillPitch2: String
        ) = FillStyle(
            FillType.fromFileValue(fillType.toInt()),
            fromFileParam(fillWidth, DEFAULT_WIDTH, ::validLength),
            fromFileParam(fillAngle1, DEFAULT_ANGLE1, ::validAngle),
            fromFileParam(fillPitch1, DEFAULT_PITCH, ::validLength),
            fromFileParam(fillAngle2, DEFAULT_ANGLE2, ::validAngle),
            fromFileParam(fillPitch2, DEFAULT_PITCH, ::validLength),
        )

        private fun fromFileParam(fileParam: String, defaultValue: Int, valid: (Int) -> Boolean) =
            fileParam.toIntOrNull().let {
                if (it != null && valid(it)) it else defaultValue
            }

        private fun validAngle(angle: Int) = angle != UNUSED_VALUE

        private fun validLength(length: Int) = length > 0
    }
}