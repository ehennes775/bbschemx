package tools

import views.SchematicView

interface ToolSource {
    fun addToolListener(listener: ToolListener)
    fun removeToolListener(listener: ToolListener)
    var tool: Tool
    val toolTarget: SchematicView
}