package tools.arc

import types.Angle

enum class ArcDirection {

    CLOCKWISE {
        override fun calculateSweep(a0: Int, a1: Int) = Angle.calculateClockwiseSweep(a0, a1)

        override val nextDirection: ArcDirection get() = COUNTERCLOCKWISE
    },

    COUNTERCLOCKWISE {
        override fun calculateSweep(a0: Int, a1: Int) = Angle.calculateCounterclockwiseSweep(a0, a1)

        override val nextDirection = CLOCKWISE
    };

    abstract fun calculateSweep(a0: Int, a1: Int): Int

    abstract val nextDirection: ArcDirection
}