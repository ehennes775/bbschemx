package models.schematic.listeners

import models.schematic.Item

interface InvalidateListener {
    fun invalidateAll()
    fun invalidateItem(item: Item)
}