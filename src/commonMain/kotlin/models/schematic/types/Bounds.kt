package models.schematic.types

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt

class Bounds private constructor(val minX: Int, val minY: Int, val maxX: Int, val maxY: Int) {

    companion object {

        val EMPTY = Bounds(
            minX = Int.MAX_VALUE,
            minY = Int.MAX_VALUE,
            maxX = Int.MIN_VALUE,
            maxY = Int.MIN_VALUE
        )

        fun fromCorners(x0: Int, y0: Int, x1: Int, y1: Int) = Bounds(
            minX = minOf(x0, x1),
            minY = minOf(y0, y1),
            maxX = maxOf(x0, x1),
            maxY = maxOf(y0, y1)
        )

        fun fromCorners(x0: Int, y0: Int, x1: Int, y1: Int, lineWidth: Int) = fromCorners(x0, y0, x1, y1).also{
            it.expand(lineWidth)
        }

        fun fromCorners(x0: Double, y0: Double, x1:Double, y1: Double) = Bounds(
            minX = floor(minOf(x0, x1)).roundToInt(),
            minY = floor(minOf(y0, y1)).roundToInt(),
            maxX = ceil(maxOf(x0, x1)).roundToInt(),
            maxY = ceil(maxOf(y0, y1)).roundToInt(),
        )
    }

    val empty = (minX > maxX) || (minY > maxY)

    val width = if (empty) 0 else maxX - minX + 1

    val height = if (empty) 0 else maxY - minY + 1

    fun expand(expand: Int) = if (empty) this else Bounds(
        minX = minX - expand,
        minY = minY - expand,
        maxX = maxX + expand,
        maxY = maxY + expand
    )

    fun inside(x: Int, y: Int) = (x in minX..maxX) && (y in minY..maxY)

    fun union(other: Bounds) = Bounds(
        minX = minOf(minX, other.minX),
        minY = minOf(minY, other.minY),
        maxX = maxOf(maxX, other.maxX),
        maxY = maxOf(maxY, other.maxY)
    )
}
