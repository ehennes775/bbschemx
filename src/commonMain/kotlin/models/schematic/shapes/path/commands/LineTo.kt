package models.schematic.shapes.path.commands

import models.schematic.io.Writer
import models.schematic.shapes.path.Command
import types.Drawer

class LineTo(
    private val x: Int,
    private val y: Int
): Command {
    companion object {
        const val TOKEN = "L"
    }

    override fun paint(drawer: Drawer) = drawer.lineTo(x, y)

    override fun write(writer: Writer) = writer.writeParams(
        TOKEN,
        x.toString(),
        y.toString()
    )
}
