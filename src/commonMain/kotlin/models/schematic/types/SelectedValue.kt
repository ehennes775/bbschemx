package models.schematic.types

sealed class SelectedValue<out T> {

    class None<out T>() : SelectedValue<T>()

    data class Single<out T>(val value: T): SelectedValue<T>()

    class Multiple<out T>: SelectedValue<T>()
}