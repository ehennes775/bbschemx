package types

import models.schematic.shapes.text.Text
import models.schematic.types.ColorIndex
import models.schematic.types.FillStyle
import models.schematic.types.LineStyle

interface Drawer {
    fun beginDraw()
    fun endDraw(color: ColorIndex, lineStyle: LineStyle)
    fun endDraw(color: ColorIndex, lineStyle: LineStyle, fillStyle: FillStyle)

    fun drawCircle(centerX: Int, centerY: Int, radius: Int)
    fun drawText(text: Text)

    fun drawZoomBox(point0: Point, point1: Point)

    fun moveTo(x: Int, y: Int)
    fun lineTo(x: Int, y: Int)
    fun close()
}
