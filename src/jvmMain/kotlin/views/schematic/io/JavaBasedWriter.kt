package views.schematic.io

class JavaBasedWriter(writer: java.io.Writer) : Writer {

    private val bufferedWriter = java.io.BufferedWriter(writer)

    override fun writeLine(line: String) = bufferedWriter.write("$line\n")
}