import schematic.Item

interface LineStyleItem : Item {
    fun withLineWidth(lineWidth: Int) : Item
    fun withDashType(dashType: Int): Item
    fun withDashLength(dashLength: Int): Item
    fun withDashSpace(dashSpace: Int): Item
    fun withCapType(capType: Int): Item
}