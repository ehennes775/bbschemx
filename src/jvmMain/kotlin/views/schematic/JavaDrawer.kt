package views.schematic

import models.schematic.shapes.text.Text
import models.schematic.types.*
import types.Point
import types.Drawer
import java.awt.*
import java.awt.geom.*
import kotlin.math.*

class JavaDrawer(
    private val graphics: Graphics2D,
    private val originalTransform: AffineTransform,
    private val currentTransform: AffineTransform
    ): Drawer {

    init {
        graphics.apply {
        }
    }

    private var currentPath = Path2D.Double()

    override fun beginDraw() {
        currentPath = Path2D.Double()
    }

    override fun endDraw(selected: Boolean, colorIndex: ColorIndex, lineStyle: LineStyle) {
        graphics.apply {
            color = itemColor(selected, colorIndex)
            stroke = createStroke(lineStyle)
            draw(currentPath)
        }
    }

    override fun endDraw(selected: Boolean, colorIndex: ColorIndex, lineStyle: LineStyle, fillStyle: FillStyle) {
        graphics.apply {
            color = itemColor(selected, colorIndex)
            stroke = createStroke(lineStyle)
            when (fillStyle.fillType) {
                FillType.HOLLOW -> draw(currentPath)
                FillType.FILL -> fill(currentPath)
                FillType.MESH -> draw(currentPath)
                FillType.HATCH -> draw(currentPath)
                FillType.VOID -> draw(currentPath)
            }
        }
    }

    override fun drawArc(centerX: Int, centerY: Int, radius: Int, startAngle: Int, sweepAngle: Int) {
        currentPath.append(
            Arc2D.Double(
                (centerX - radius).toDouble(),
                (centerY - radius).toDouble(),
                2.0 * radius,
                2.0 * radius,
                -startAngle.toDouble(),
                -sweepAngle.toDouble(),
                Arc2D.OPEN
            ),
            false
        )
    }

    override fun drawCircle(centerX: Int, centerY: Int, radius: Int) {
        currentPath.append(
            Arc2D.Double(
                (centerX - radius).toDouble(),
                (centerY - radius).toDouble(),
                2.0 * radius,
                2.0 * radius,
                0.0,
                360.0,
                Arc2D.CHORD
            ),
            false
        )
    }

    override fun moveTo(x: Int, y: Int) {
        currentPath.moveTo(x.toDouble(), y.toDouble())
    }

    override fun lineTo(x: Int, y: Int) {
        currentPath.lineTo(x.toDouble(), y.toDouble())
    }

    override fun close() {
        currentPath.closePath()
    }

    override fun drawGrid(gridSize: Int, width: Int, height: Int) {
        graphics.apply {
            val corner0 = currentTransform.inverseTransform(Point2D.Double(0.0, 0.0), null)
            val corner1 = currentTransform.inverseTransform(Point2D.Double(width.toDouble(), height.toDouble()), null)

            val y0 = ceil(minOf(corner0.y, corner1.y) / gridSize).roundToInt()
            val y1 = floor(maxOf(corner0.y, corner1.y) / gridSize).roundToInt()

            for (y in y0..y1) {
                color = gridColor(y)
                (gridSize * y).toDouble().let {
                    draw(Line2D.Double(corner0.x, it, corner1.x, it))
                }
            }

            val x0 = ceil(minOf(corner0.x, corner1.x) / gridSize).roundToInt()
            val x1 = floor(maxOf(corner0.x, corner1.x) / gridSize).roundToInt()

            for (x in x0..x1) {
                color = gridColor(x)
                (gridSize * x).toDouble().let {
                    draw(Line2D.Double(it, corner0.y, it, corner1.y))
                }
            }
        }
    }

    companion object {
        private val BACKGROUND = Color.BLACK
        private val GRAPHIC = Color.GREEN.darker()

        val COLORS = mapOf(
            ColorIndex.BACKGROUND to BACKGROUND,
            ColorIndex.PIN to Color.WHITE,
            ColorIndex.NET_ENDPOINT to Color.RED,
            ColorIndex.GRAPHIC to GRAPHIC,
            ColorIndex.NET to Color.BLUE,
            ColorIndex.ATTRIBUTE to Color.YELLOW.darker(),
            ColorIndex.LOGIC_BUBBLE to Color.CYAN,
            ColorIndex.GRID_DOTS to Color.GRAY,
            ColorIndex.DETACHED_ATTRIBUTE to Color.RED,
            ColorIndex.TEXT to Color.GREEN.darker(),
            ColorIndex.BUS to Color.GREEN.darker(),
            ColorIndex.SELECT to Color.ORANGE,
            ColorIndex.BOUNDING_BOX to Color.ORANGE,
            ColorIndex.ZOOM_BOX to Color.CYAN,
            ColorIndex.STROKE to Color.DARK_GRAY,
            ColorIndex.LOCK to Color.DARK_GRAY,
            ColorIndex.OUTPUT_BACKGROUND to BACKGROUND,
            ColorIndex.FREESTYLE1 to Color(0.0f, 0.0f, 0.0f),
            ColorIndex.FREESTYLE2 to Color(0.0f, 0.0f, 0.0f),
            ColorIndex.FREESTYLE3 to Color(0.0f, 0.0f, 0.0f),
            ColorIndex.FREESTYLE4 to Color(0.0f, 0.0f, 0.0f),
            ColorIndex.JUNCTION to Color.YELLOW,
            ColorIndex.MESH_GRID_MAJOR to Color.DARK_GRAY,
            ColorIndex.MESH_GRID_MINOR to Color.DARK_GRAY.darker()
        ).withDefault { GRAPHIC }

        private const val JOIN_TYPE = BasicStroke.JOIN_ROUND

        private const val MITER_LIMIT = 10.0f

        private fun endCaps(capType: CapType): Int = when (capType) {
            CapType.NONE -> BasicStroke.CAP_BUTT
            CapType.SQUARE -> BasicStroke.CAP_SQUARE
            CapType.ROUND -> BasicStroke.CAP_ROUND
        }

        fun createStroke(lineStyle: LineStyle) = BasicStroke(
            lineStyle.lineWidth.toFloat(),
            endCaps(lineStyle.capType),
            JOIN_TYPE,
            MITER_LIMIT
        )

        private const val FONT_NAME = "Courier"
        private const val FONT_STYLE = Font.ITALIC

        private fun createFont(size: Int) = Font(FONT_NAME, FONT_STYLE, size)

        private fun gridColor(coord: Int): Color = when {
            coord == 0 -> Color.GRAY
            coord % 5 == 0 -> COLORS[ColorIndex.MESH_GRID_MAJOR]!!
            else -> COLORS[ColorIndex.MESH_GRID_MINOR]!!
        }

        private fun itemColor(selected: Boolean, color: ColorIndex): Color =
            COLORS[if (selected) ColorIndex.SELECT else color]!!

        private fun Color.semiTransparent() = Color(
            this.red, this.green, this.blue, 32
        )

        private fun Graphics2D.withTemporaryTransform(tempTransform: AffineTransform, action: () -> Unit) {
            val savedTransform = transform
            transform = tempTransform
            action()
            transform = savedTransform
        }

        private fun createRectangle(point0: Point, point1: Point) = Rectangle(
            minOf(point0.x, point1.x),
            minOf(point0.y, point1.y),
            abs(point1.x - point0.x),
            abs(point1.y - point0.y),
        )

        private val RUBBER_BOX_STROKE = BasicStroke(1.0F)
    }

    override fun drawText(selected: Boolean, alpha: Double, text: Text) {
        graphics.apply {
            font = createFont(text.size)
            val savedTransform = transform
            translate(text.insertX, text.insertY)
            scale(20.0, -20.0)
            rotate(PI * text.rotation.toDouble() / -180.0)
            val lineX = -thing1(font, text).toFloat()
            var lineY = thing3(font, text).toFloat();
            color = itemColor(selected, text.color).let {
                Color(it.red, it.green, it.blue, (255.0 * alpha).roundToInt())
            }
            text.shownLines.forEach {
                drawString(it, lineX, lineY)
                lineY += 100.0f
            }
            transform = savedTransform
        }
    }

    override fun drawSelectBox(point0: Point, point1: Point) = drawRubberBox(
        createRectangle(point0, point1),
        COLORS[ColorIndex.BOUNDING_BOX]!!
    )

    override fun drawZoomBox(point0: Point, point1: Point) = drawRubberBox(
        createRectangle(point0, point1),
        COLORS[ColorIndex.ZOOM_BOX]!!
    )

    private fun drawRubberBox(rectangle: Rectangle, boxColor: Color) {
        rectangle.let {
            graphics.apply {
                stroke = RUBBER_BOX_STROKE
                withTemporaryTransform(originalTransform) {
                    color = boxColor.semiTransparent()
                    fill(it)
                    color = boxColor
                    draw(it)
                }
            }
        }
    }

    private fun thing1(font: Font, text: Text): Double {
        return text.alignment.horizontal * thing2(font, text.shownLines)
    }

    private fun thing2(font: Font, lines: Array<String>): Int {
        val metrics = graphics.getFontMetrics(font)
        return lines.map {
            metrics.stringWidth(it)
        }.maxOrNull() ?: throw Exception()
    }

    private fun thing3(font: Font, text: Text): Double {
        val metrics = graphics.getFontMetrics(font)

        val bounds = metrics.getStringBounds("O", graphics)
        val capHeight = -bounds.y
        val ascent = metrics.ascent


        return text.alignment.vertical * 5.0
    }
}