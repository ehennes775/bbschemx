package views.schematic.io

import models.schematic.io.Reader

class JavaBasedReader(reader: java.io.Reader): Reader {

    private val bufferedReader = java.io.BufferedReader(reader)

    override fun readLine(): String? = bufferedReader.readLine()
}