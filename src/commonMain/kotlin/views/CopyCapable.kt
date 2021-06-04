package views

interface CopyCapable {
    val canCopy: Boolean
    fun copy()
}