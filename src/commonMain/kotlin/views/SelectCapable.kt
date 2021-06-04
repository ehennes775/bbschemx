package views

interface SelectCapable {
    val canSelectAll: Boolean
    val canSelectNone: Boolean
    fun selectAll()
    fun selectNone()
}