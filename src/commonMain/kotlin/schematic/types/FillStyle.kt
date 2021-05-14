package schematic.types

class FillStyle(
    val fillType: FillType = FillType.HOLLOW,
    val fillWidth: Int = 10,
    val fillAngle1: Int = 45,
    val fillPitch1: Int = 100,
    val fillAngle2: Int = 135,
    val fillPitch2: Int = 100
) {
    val fillTypeFileValue get() = fillType.fileValue

    val fillWidthFileValue get() = if (fillType.usesFillWidth) fillWidth else -1

    val fillAngle1FileValue get() = if (fillType.usesFirstSet) fillAngle1 else -1

    val fillPitch1FileValue get() = if (fillType.usesFirstSet) fillPitch1 else -1

    val fillAngle2FileValue get() = if (fillType.usesSecondSet) fillAngle2 else -1

    val fillPitch2FileValue get() = if (fillType.usesSecondSet) fillPitch2 else -1

    fun withFillType(newFillType: FillType) = FillStyle(
        newFillType,
        fillWidth,
        fillAngle1,
        fillPitch1,
        fillAngle2,
        fillPitch2
    )

    fun withFillWidth(newFillWidth: Int) = FillStyle(
        fillType,
        newFillWidth,
        fillAngle1,
        fillPitch1,
        fillAngle2,
        fillPitch2
    )

    fun withFillAngle1(newFillAngle1: Int) = FillStyle(
        fillType,
        fillWidth,
        newFillAngle1,
        fillPitch1,
        fillAngle2,
        fillPitch2
    )

    fun withFillPitch1(newFillPitch1: Int) = FillStyle(
        fillType,
        fillWidth,
        fillAngle1,
        newFillPitch1,
        fillAngle2,
        fillPitch2
    )

    fun withFillAngle2(newFillAngle2: Int) = FillStyle(
        fillType,
        fillWidth,
        fillAngle1,
        fillPitch1,
        newFillAngle2,
        fillPitch2
    )

    fun withFillPitch2(newFillPitch2: Int) = FillStyle(
        fillType,
        fillWidth,
        fillAngle1,
        fillPitch1,
        fillAngle2,
        newFillPitch2
    )
}