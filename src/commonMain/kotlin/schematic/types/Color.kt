package schematic.types

inline class Color(val fileValue: Int) {
    companion object {
        val ATTRIBUTE = Color(5)
        val BACKGROUND = Color(0)
        val BUS = Color(10)
        val GRAPHIC = Color(3)
        val NET = Color(4)
        val PIN = Color(1)
        val TEXT = Color(9)


//        2	NET_ENDPOINT_COLOR
//        6	LOGIC_BUBBLE_COLOR
//        7	DOTS_GRID_COLOR
//        8	DETACHED_ATTRIBUTE_COLOR
//        11	SELECT_COLOR
//        12	BOUNDINGBOX_COLOR
//        13	ZOOM_BOX_COLOR
//        14	STROKE_COLOR
//        15	LOCK_COLOR
//        16	OUTPUT_BACKGROUND_COLOR
//        17	FREESTYLE1_COLOR
//        18	FREESTYLE2_COLOR
//        19	FREESTYLE3_COLOR
//        20	FREESTYLE4_COLOR
//
//        21	JUNCTION_COLOR
//        22	MESH_GRID_MAJOR_COLOR
//        23	MESH_GRID_MINOR_COLOR
//        24	ORIGIN_COLOR
//        25	PLACE_ORIGIN_COLOR
    }
}