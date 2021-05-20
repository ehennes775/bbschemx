package models.schematic.listeners

import models.schematic.Item

interface InvalidateListener {
    fun invalidateItem(item: Item)
}