package models.schematic.types

inline class ColorIndex(val fileValue: Int) {

    companion object {
        val BACKGROUND = ColorIndex(0)
        val PIN = ColorIndex(1)
        val NET_ENDPOINT = ColorIndex(2)
        val GRAPHIC = ColorIndex(3)
        val NET = ColorIndex(4)
        val ATTRIBUTE = ColorIndex(5)
        val LOGIC_BUBBLE = ColorIndex(6)
        val GRID_DOTS = ColorIndex(7)
        val DETACHED_ATTRIBUTE = ColorIndex(8)
        val TEXT = ColorIndex(9)
        val BUS = ColorIndex(10)
        val SELECT = ColorIndex(11)
        val BOUNDING_BOX = ColorIndex(12)
        val ZOOM_BOX = ColorIndex(13)
        val STROKE = ColorIndex(14)
        val LOCK = ColorIndex(15)
        val OUTPUT_BACKGROUND = ColorIndex(16)
        val FREESTYLE1 = ColorIndex(17)
        val FREESTYLE2 = ColorIndex(18)
        val FREESTYLE3 = ColorIndex(19)
        val FREESTYLE4 = ColorIndex(20)
        val JUNCTION = ColorIndex(21)
        val MESH_GRID_MAJOR = ColorIndex(22)
        val MESH_GRID_MINOR = ColorIndex(23)
        val ORIGIN = ColorIndex(24)
    }
}