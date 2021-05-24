package tools.bus

import tools.ToolFactory
import tools.ToolTarget

class BusToolFactory: ToolFactory {
    override fun createTool(target: ToolTarget) = BusTool(target)
}
