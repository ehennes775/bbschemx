package views

interface RedoCapable {
    val canRedo: Boolean

    fun redo()
}