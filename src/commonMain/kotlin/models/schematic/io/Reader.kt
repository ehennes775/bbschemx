package models.schematic.io

interface Reader {
    fun readLine(): String?

    fun readLines(count: Int) = Array(count) { readLine() ?: throw Exception() }

    fun readParams() = readLine()?.split(" ")?.toTypedArray()
}