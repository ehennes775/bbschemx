package models.schematic.shapes.path.commands

import models.schematic.io.Writer
import models.schematic.shapes.path.Command
import models.schematic.types.Drawer

class MoveTo(
    private val x: Int,
    private val y: Int
): Command {
    companion object {
        const val TOKEN = "M"
    }

    override fun paint(drawer: Drawer) = drawer.moveTo(x, y)

    override fun write(writer: Writer) = writer.writeParams(
        TOKEN,
        x.toString(),
        y.toString()
    )
}