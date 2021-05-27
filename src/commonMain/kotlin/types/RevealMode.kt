package types

import models.schematic.shapes.text.Visibility

enum class RevealMode {

    HIDDEN {
        override fun textIsVisible(visibility: Visibility) = (visibility == Visibility.VISIBLE)

        override val next get() = SHOWN
    },

    SHOWN {
        override fun textIsVisible(visibility: Visibility) = true

        override val next get() = HIDDEN
    };

    abstract fun textIsVisible(visibility: Visibility): Boolean

    abstract val next: RevealMode
}