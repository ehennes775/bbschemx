package models.schematic.types

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
            maxX = maxOf(x1, x1),
            maxY = maxOf(y0, y1)
        )

        fun fromCorners(x0: Int, y0: Int, x1: Int, y1: Int, lineWidth: Int) = fromCorners(x0, y0, x1, y1).also{
            it.expand(lineWidth)
        }
    }

    val empty = (minX > maxX) || (minY > maxY)

    fun expand(expand: Int) = if (empty) this else Bounds(
        minX = minX - expand,
        minY = minY - expand,
        maxX = maxX + expand,
        maxY = maxY + expand
    )
}
