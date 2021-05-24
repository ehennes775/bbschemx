package tools.arc

import tools.ToolFactory
import tools.ToolTarget

class ArcToolFactory: ToolFactory {
    override fun createTool(target: ToolTarget) = ArcTool(target)
}
