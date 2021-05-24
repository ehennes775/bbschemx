package tools.line

import tools.ToolFactory
import tools.ToolTarget

class LineToolFactory: ToolFactory {
    override fun createTool(target: ToolTarget) = LineTool(target)
}
