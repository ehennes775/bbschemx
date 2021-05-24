package tools.dummy

import tools.ToolFactory
import tools.ToolTarget

class DummyToolFactory : ToolFactory {
    override fun createTool(target: ToolTarget) = DummyTool()
}
