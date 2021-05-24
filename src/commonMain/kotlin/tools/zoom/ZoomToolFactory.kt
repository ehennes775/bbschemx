package tools.zoom

import tools.ToolFactory
import tools.ToolTarget

class ZoomToolFactory: ToolFactory {
    override fun createTool(target: ToolTarget) = ZoomTool(target)
}
