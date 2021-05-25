package types

import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.roundToInt

class Angle {

    companion object {

        fun calculateAngle(x0: Int, y0: Int, x1: Int, y1: Int): Double =
            atan2((y1 - y0).toDouble(), (x1 - x0).toDouble())

        fun calculateClockwiseSweep(a0: Int, a1: Int) = -calculateCounterclockwiseSweep(a1, a0)
            //.also { require((it < 0) && (it >= -360))}

        fun calculateCounterclockwiseSweep(a0: Int, a1: Int) = (normalize(a1) - normalize(a0))
            .let { sweep -> if (sweep <= 0) sweep + 360 else sweep }
            .also { require((it > 0) && (it <= 360))}

        fun fromRadians(radians: Double) = (180.0 * radians / PI).roundToInt()

        fun isNormal(angle: Int): Boolean = ((0 <= angle) && (angle < 360))

        fun isOrthographic(angle: Int): Boolean = ((angle % 90) == 0)

        fun makeOrthographic(angle: Int) = angle
            .let { ((angle / 90.0) * 90).roundToInt() }
            .also { require(isOrthographic(it)) }

        fun normalize(angle: Int): Int = angle
            .let { if (it < 0) (360 - (-it % 360)) else it }
            .let { if (it >= 360) (it % 360) else it }
            .also { require(isNormal(it)) }

        fun toRadians(angle: Int): Double = PI * angle.toDouble() / 180.0
    }
}
