package views

interface SaveCapable {
    val canSave: Boolean

    fun save()
}