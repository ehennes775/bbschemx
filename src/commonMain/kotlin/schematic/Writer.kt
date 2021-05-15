package schematic

interface Writer {
    fun writeLine(line: String)
    fun writeParams(vararg params: String)
}