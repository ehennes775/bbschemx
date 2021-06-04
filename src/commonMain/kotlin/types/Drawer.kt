package types

import models.schematic.shapes.text.Text
import models.schematic.types.ColorIndex
import models.schematic.types.FillStyle
import models.schematic.types.LineStyle

interface Drawer {
    fun beginDraw()
    fun endDraw(selected: Boolean, color: ColorIndex, lineStyle: LineStyle)
    fun endDraw(selected: Boolean, color: ColorIndex, lineStyle: LineStyle, fillStyle: FillStyle)

    fun drawArc(centerX: Int, centerY: Int, radius: Int, startAngle: Int, sweepAngle: Int)
    fun drawCircle(centerX: Int, centerY: Int, radius: Int)
    fun drawText(selected: Boolean, alpha: Double, text: Text)

    fun drawZoomBox(point0: Point, point1: Point)

    fun moveTo(x: Int, y: Int)
    fun lineTo(x: Int, y: Int)
    fun close()

    fun drawGrid(gridSize: Int, width: Int, height: Int)
    fun drawSelectBox(point0: Point, point1: Point)
}
