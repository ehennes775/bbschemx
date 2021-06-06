package settings

import java.nio.file.Paths
import java.sql.DriverManager
import java.sql.ResultSet

class JavaSettingsSource: SettingsSource() {

    private val settingsDatabase =
        Paths.get(
            System.getProperty("user.home"),
            APPLICATION_FOLDER,
            DATABASE_FILE
        ).toFile()

    private val connectionString = "jdbc:sqlite:${settingsDatabase.absolutePath}"

    init {
        if (!settingsDatabase.exists()) {
            javaClass.getResourceAsStream(DATABASE_FILE).use { inputStream ->
                if (inputStream == null) {
                    throw Exception("Cannot open initial settings database resource '$DATABASE_FILE'")
                }
                settingsDatabase.parentFile.mkdirs()
                settingsDatabase.outputStream().use { outputStream ->
                    inputStream.transferTo(outputStream)
                }
            }
        }
    }

    init {
        Class.forName("org.sqlite.JDBC")
    }

    val colorScheme
        get() =
            runQuery("SELECT schemeName FROM ColorScheme ORDER BY schemeId") { resultSet ->
                val columnIndex = resultSet.findColumn("schemeName")
                resultSet.map {
                    it.getString(columnIndex)
                }
            }

    override val dashLength
        get() =
            runQuery("SELECT value FROM DashLength ORDER BY value") { resultSet ->
                val columnIndex = resultSet.findColumn("value")
                resultSet.map {
                    it.getInt(columnIndex)
                }
            }


    override val dashSpace
        get() =
            runQuery("SELECT value FROM DashSpace ORDER BY value") { resultSet ->
                val columnIndex = resultSet.findColumn("value")
                resultSet.map {
                    it.getInt(columnIndex)
                }
            }

    val gridSize
        get() =
            runQuery("SELECT value FROM GridSize ORDER BY value") { resultSet ->
                val columnIndex = resultSet.findColumn("value")
                resultSet.map {
                    it.getInt(columnIndex)
                }
            }

    override val lineWidth
        get() =
            runQuery("SELECT value FROM LineWidth ORDER BY value") { resultSet ->
                val columnIndex = resultSet.findColumn("value")
                resultSet.map {
                    it.getInt(columnIndex)
                }
            }

    val textRotation
        get() =
            runQuery("SELECT value FROM TextRotation ORDER BY value") { resultSet ->
                val columnIndex = resultSet.findColumn("value")
                resultSet.map {
                    it.getInt(columnIndex)
                }
            }

    val textSize
        get() =
            runQuery("SELECT value FROM TextSize ORDER BY value") { resultSet ->
                val columnIndex = resultSet.findColumn("value")
                resultSet.map {
                    it.getInt(columnIndex)
                }
            }

    private fun <R> runQuery(querySql: String, action: (ResultSet) -> R): R =
        DriverManager.getConnection(connectionString).use { connection ->
            connection.createStatement().use { statement ->
                statement.executeQuery(querySql).use {
                    action(it)
                }
            }
        }

    companion object {
        private const val DATABASE_FILE = "settings.db"

        private const val APPLICATION_FOLDER = ".bbschemx"

        private fun <R> ResultSet.map(transform: (ResultSet) -> R): List<R> = mutableListOf<R>().also {
            while (this.next()) {
                it.add(transform(this))
            }
        }
    }
}
