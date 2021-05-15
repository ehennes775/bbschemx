package views.schematic.io

class JavaBasedReader(reader: java.io.Reader): Reader {

    private val bufferedReader = java.io.BufferedReader(reader)

    override fun readLine(): String? = bufferedReader.readLine()
}