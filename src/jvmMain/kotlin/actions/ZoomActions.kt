package actions

import views.IconLoader
import views.schematic.SchematicView
import java.awt.event.ActionEvent
import javax.swing.AbstractAction
import javax.swing.JTabbedPane

class ZoomActions(private val tabbedPane: JTabbedPane, iconLoader: IconLoader) {

    private val currentView get() = tabbedPane.selectedComponent as? SchematicView

    val zoomExtentsAction: AbstractAction = object: AbstractAction(
        "Zoom Extents",
        iconLoader.loadIcon("ZoomExtents.png")
    ) {
        override fun actionPerformed(e: ActionEvent?) {
            currentView?.zoomExtents()
        }
    }

    val zoomInAction: AbstractAction = object: AbstractAction(
        "Zoom In",
        iconLoader.loadIcon("ZoomIn.png")
    ) {
        override fun actionPerformed(e: ActionEvent?) {
            currentView?.zoomIn()
        }
    }

    val zoomOutAction: AbstractAction = object: AbstractAction(
        "Zoom Out",
        iconLoader.loadIcon("ZoomOut.png")
    ) {
        override fun actionPerformed(e: ActionEvent?) {
            currentView?.zoomOut()
        }
    }
}