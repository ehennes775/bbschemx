package tools.pin

import tools.ToolFactory
import tools.ToolTarget

class PinToolFactory: ToolFactory {
    override fun createTool(target: ToolTarget) = PinTool(target)
}
