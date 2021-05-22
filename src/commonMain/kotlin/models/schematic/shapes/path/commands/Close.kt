package models.schematic.shapes.path.commands

import models.schematic.io.Writer
import models.schematic.shapes.path.Command
import models.schematic.types.Drawer

class Close: Command {
    companion object {
        const val TOKEN = "Z"
    }

    override fun paint(drawer: Drawer) = drawer.close()

    override fun write(writer: Writer) = writer.writeParams(TOKEN)
}