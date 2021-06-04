package views

interface PasteCapable {
    val canPaste: Boolean

    fun paste()
}