package models.schematic.types

import models.schematic.Item
import models.schematic.io.Reader

interface Creator {
    fun read(params: Array<String>, reader: Reader): Item
}