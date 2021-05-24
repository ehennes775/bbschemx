package tools.net

import tools.ToolFactory
import tools.ToolTarget

class NetToolFactory: ToolFactory {
    override fun createTool(target: ToolTarget) = NetTool(target)
}
