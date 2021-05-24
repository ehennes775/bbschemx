package views.schematic

import models.schematic.shapes.text.Text
import models.schematic.types.*
import types.Point
import types.Drawer
import java.awt.*
import java.awt.geom.AffineTransform
import java.awt.geom.Arc2D
import java.awt.geom.Path2D
import kotlin.math.PI
import kotlin.math.abs

class JavaDrawer(private val graphics: Graphics2D, private val oldTransform: AffineTransform): Drawer {

    init {
        graphics.apply {
            //translate(100.0, 700.0)
            //scale(1.0, -1.0)
        }
    }

    private var currentPath = Path2D.Double()

    override fun beginDraw() {
        currentPath = Path2D.Double()
    }

    override fun endDraw(colorIndex: ColorIndex, lineStyle: LineStyle) {
        graphics.apply {
            color = COLORS[colorIndex]
            stroke = createStroke(lineStyle)
            draw(currentPath)
        }
    }

    override fun endDraw(colorIndex: ColorIndex, lineStyle: LineStyle, fillStyle: FillStyle) {
        graphics.apply {
            color = COLORS[colorIndex]
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
            ColorIndex.MESH_GRID_MAJOR to Color.GRAY,
            ColorIndex.MESH_GRID_MINOR to Color.GRAY
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
    }

    override fun drawText(text: Text) {
        graphics.apply {
            font = createFont(text.size)
            val savedTransform = transform
            translate(text.insertX, text.insertY)
            scale(20.0, -20.0)
            rotate(PI * text.rotation.toDouble() / -180.0)
            val lineX = -thing1(font, text).toFloat()
            var lineY = thing3(font, text).toFloat();
            color = COLORS[text.color]
            text.shownLines.forEach {
                drawString(it, lineX, lineY)
                lineY += 100.0f
            }
            transform = savedTransform
        }
    }

    override fun drawZoomBox(point0: Point, point1: Point) {
        graphics.apply {
            val savedTransform = transform
            transform = oldTransform
            color = COLORS[ColorIndex.ZOOM_BOX]
            draw(Rectangle(
                minOf(point0.x, point1.x),
                minOf(point0.y, point1.y),
                abs(point1.x - point0.x),
                abs(point1.y - point0.y),
            ))
            transform = savedTransform
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