package settings

abstract class SettingsSource {

    abstract val dashLength: List<Int>
    abstract val dashSpace: List<Int>
    abstract val lineWidth: List<Int>

    private val listeners = mutableListOf<SettingsListener>()

    fun addSettingsListener(listener: SettingsListener) {
        listeners.add(listener)
    }

    fun removeSettingsListener(listener: SettingsListener) {
        listeners.remove(listener)
    }

    fun fireSettingsChanged() {
        listeners.forEach { it.settingsChanged() }
    }
}