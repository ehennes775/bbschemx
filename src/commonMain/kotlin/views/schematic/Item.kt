package views.schematic

import views.schematic.io.Writer

interface Item {
    fun write(writer: Writer)
}