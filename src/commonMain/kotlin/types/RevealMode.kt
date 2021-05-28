package types

import models.schematic.shapes.text.Visibility

enum class RevealMode {

    HIDDEN {
        override fun alpha(visibility: Visibility): Double = 1.0

        override fun textIsVisible(visibility: Visibility) = (visibility == Visibility.VISIBLE)

        override val next get() = SHOWN
    },

    SHOWN {
        override fun alpha(visibility: Visibility): Double = when(visibility) {
            Visibility.INVISIBLE -> 0.5
            Visibility.VISIBLE -> 1.0
        }

        override fun textIsVisible(visibility: Visibility) = true

        override val next get() = HIDDEN
    };

    abstract fun alpha(visibility: Visibility): Double

    abstract fun textIsVisible(visibility: Visibility): Boolean

    abstract val next: RevealMode
}