package schematic

class JavaWriter(private val writer: java.io.Writer) : Writer {
    override fun writeLine(line: String) = writer.write("$line\n")

    override fun writeParams(vararg params: String) = writeLine(params.joinToString(separator = " ") { it })
}