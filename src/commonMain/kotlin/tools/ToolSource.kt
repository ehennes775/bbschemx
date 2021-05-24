package tools

interface ToolSource {
    fun addToolListener(listener: ToolListener)
    fun removeToolListener(listener: ToolListener)
    var tool: Tool
    val toolTarget: ToolTarget
}