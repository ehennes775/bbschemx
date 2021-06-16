package settings

import scheme.ColorEntry
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

    val colors
        get() =
            runQuery("SELECT colorId, ColorName.colorName as colorName, red, green, blue FROM Color JOIN ColorName ON Color.colorId = ColorName.colorIndex WHERE schemeId = (SELECT currentColorScheme FROM Settings)") { resultSet ->
                val indexIndex = resultSet.findColumn("colorId")
                val nameIndex = resultSet.findColumn("colorName")
                val redIndex = resultSet.findColumn("red")
                val greenIndex = resultSet.findColumn("green")
                val blueIndex = resultSet.findColumn("blue")
                resultSet.map {
                    ColorEntry(
                        it.getInt(indexIndex),
                        it.getString(nameIndex),
                        it.getDouble(redIndex),
                        it.getDouble(greenIndex),
                        it.getDouble(blueIndex)
                    )
                }
            }

    val colorSchemeName
        get() =
            runQuery("SELECT schemeName FROM ColorScheme ORDER BY schemeId") { resultSet ->
                val columnIndex = resultSet.findColumn("schemeName")
                resultSet.map {
                    it.getString(columnIndex)
                }
            }

    var currentColorScheme: String
        get() =
            runQuery("SELECT schemeName FROM ColorScheme JOIN Settings ON ColorScheme.schemeId = Settings.currentColorScheme") { resultSet ->
                val columnIndex = resultSet.findColumn("schemeName")
                resultSet
                    .map { it.getString(columnIndex) }
                    .first()
            }
        set(value) {
            runUpdate("UPDATE Settings SET currentColorScheme = (SELECT schemeId FROM ColorScheme WHERE schemeName = '$value')")
        }


    private fun <R> runQuery(querySql: String, action: (ResultSet) -> R): R =
        DriverManager.getConnection(connectionString).use { connection ->
            connection.createStatement().use { statement ->
                statement.executeQuery(querySql).use {
                    action(it)
                }
            }
        }

    private fun runUpdate(querySql: String) =
        DriverManager.getConnection(connectionString).use { connection ->
            connection.createStatement().use { statement ->
                statement.executeUpdate(querySql)
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
