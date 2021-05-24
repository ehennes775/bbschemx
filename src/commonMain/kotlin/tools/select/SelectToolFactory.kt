package tools.select

import tools.ToolFactory
import tools.ToolTarget

class SelectToolFactory : ToolFactory {
    override fun createTool(target: ToolTarget) = SelectTool()
}
