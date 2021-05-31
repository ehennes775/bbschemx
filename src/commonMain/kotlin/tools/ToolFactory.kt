package tools

interface ToolFactory {
    val settings: ToolSettings
    fun createTool(target: ToolTarget): Tool
}