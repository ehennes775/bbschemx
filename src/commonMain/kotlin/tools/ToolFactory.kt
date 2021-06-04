package tools

import views.SchematicView

interface ToolFactory {
    val settings: ToolSettings
    fun createTool(target: SchematicView): Tool
}