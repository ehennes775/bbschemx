package views.schematic.io

interface Writer {
    fun writeLine(line: String)

    fun writeParams(vararg params: String) = writeLine(params.joinToString(separator = " ") { it })
}