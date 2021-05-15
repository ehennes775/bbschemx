package views.schematic.types

import views.schematic.Item
import views.schematic.io.Reader

interface Creator {
    fun read(params: Array<String>, reader: Reader): Item
}