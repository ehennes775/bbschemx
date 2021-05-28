package types

enum class GridMode {

    OFF {
        override val isVisible get() = false

        override val next get() = ON
    },

    ON {
        override val isVisible get() = true

        override val next get() = OFF
    };

    abstract val isVisible: Boolean

    abstract val next: GridMode
}
