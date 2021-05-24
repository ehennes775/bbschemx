package tools.box

import tools.ToolFactory
import tools.ToolTarget

class BoxToolFactory: ToolFactory {
    override fun createTool(target: ToolTarget) = BoxTool(target)
}
