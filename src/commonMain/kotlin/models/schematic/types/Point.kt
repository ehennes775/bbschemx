package models.schematic.types

import kotlin.math.abs

data class Point(val x: Int, val y: Int) {

    fun distanceTo(otherX: Int, otherY: Int): Double {
        return 10.0
    }

    fun snapToGrid(grid: Int) = Point(
        x = Internal.snap(x, grid),
        y = Internal.snap(y, grid)
    )

    fun snapOrthogonal(firstX: Int, firstY: Int): Point = if (abs(x - firstX) > abs(y - firstY)) {
        Point(x, firstY)
    } else {
        Point(firstX, y)
    }

    private object Internal {
        fun snap(coord: Int, grid: Int): Int {
            val sign = if (coord >= 0) 1 else -1
            val `val`: Int = abs(coord)
            val dividend = `val` / grid
            val remainder = `val` % grid
            var result2 = dividend * grid
            if (remainder > grid / 2) {
                result2 += grid
            }
            return sign * result2
        }
    }
}
