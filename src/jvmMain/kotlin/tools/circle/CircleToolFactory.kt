package tools.circle

import tools.ToolFactory
import tools.ToolTarget

class CircleToolFactory: ToolFactory {
    override fun createTool(target: ToolTarget) = CircleTool(target)
}
