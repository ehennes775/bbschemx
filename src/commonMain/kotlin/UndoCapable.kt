interface UndoCapable {
    val canUndo: Boolean
    fun undo()
}