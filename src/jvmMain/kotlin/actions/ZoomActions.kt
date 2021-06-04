package actions

import views.IconLoader
import views.schematic.JavaSchematicView
import java.awt.event.ActionEvent
import javax.swing.AbstractAction
import javax.swing.JTabbedPane

class ZoomActions(private val tabbedPane: JTabbedPane, iconLoader: IconLoader) {

    private val currentView get() = tabbedPane.selectedComponent as? JavaSchematicView

    val zoomExtentsAction: AbstractAction = object: AbstractAction(
        "Zoom Extents",
        iconLoader.loadIcon("12x12/ZoomExtents.png")
    ) {
        override fun actionPerformed(e: ActionEvent?) {
            currentView?.zoomExtents()
        }

        init {
            putValue(LARGE_ICON_KEY, iconLoader.loadIcon("24x24/ZoomExtents.png"))
        }
    }

    val zoomInAction: AbstractAction = object: AbstractAction(
        "Zoom In",
        iconLoader.loadIcon("12x12/ZoomIn.png")
    ) {
        override fun actionPerformed(e: ActionEvent?) {
            currentView?.zoomIn()
        }

        init {
            putValue(LARGE_ICON_KEY, iconLoader.loadIcon("24x24/ZoomIn.png"))
        }
    }

    val zoomOutAction: AbstractAction = object: AbstractAction(
        "Zoom Out",
        iconLoader.loadIcon("12x12/ZoomOut.png")
    ) {
        override fun actionPerformed(e: ActionEvent?) {
            currentView?.zoomOut()
        }

        init {
            putValue(LARGE_ICON_KEY, iconLoader.loadIcon("24x24/ZoomOut.png"))
        }
    }
}