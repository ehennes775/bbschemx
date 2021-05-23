package tools

interface ToolFactory {
    fun createTool(target: ToolTarget): Tool
}