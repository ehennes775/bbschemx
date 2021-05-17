package models.schematic.types

import models.schematic.io.Writer

class Version(
    private val first: String = "",
    private val second: String = ""
) {
    companion object {
        const val TOKEN = "v"

        fun read(params: Array<String>): Version {
            return Version()
        }
    }

    fun write(writer: Writer) = writer.writeParams(TOKEN, first, second)
}