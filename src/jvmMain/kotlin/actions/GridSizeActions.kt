package actions

import views.schematic.SchematicView
import java.awt.event.ActionEvent
import javax.swing.AbstractAction
import javax.swing.JTabbedPane

class GridSizeActions(private val tabbedPane: JTabbedPane) {
    private var currentSize: Int = DEFAULT_SIZE
        set(value) {
            field = value
            scaleDownAction.isEnabled = canScaleDown
            scaleUpAction.isEnabled = canScaleUp
            tabbedPane.components
                .mapNotNull { it as SchematicView }
                .forEach { it.gridSize = value }
        }

    private val canScaleDown: Boolean get() = gridSizes.any { it < currentSize }

    private val canScaleUp: Boolean get() = gridSizes.any { it > currentSize }

    private fun resetGrid() {
        currentSize = DEFAULT_SIZE
    }

    private fun scaleGridDown() {
        currentSize = gridSizes
            .filter { it < currentSize }
            .maxOrNull()
            ?: DEFAULT_SIZE
    }

    private fun scaleGridUp() {
        currentSize = gridSizes
            .filter { it > currentSize }
            .minOrNull()
            ?: DEFAULT_SIZE
    }

    val resetGridAction = object : AbstractAction("Reset Grid Size") {
        override fun actionPerformed(e: ActionEvent?) {
            resetGrid()
        }
    }

    val scaleDownAction = object : AbstractAction("Scale Grid Down") {
        override fun actionPerformed(e: ActionEvent?) {
            scaleGridDown()
        }
    }.apply { isEnabled = canScaleDown }

    val scaleUpAction = object : AbstractAction("Scale Grid Up") {
        override fun actionPerformed(e: ActionEvent?) {
            scaleGridUp()
        }
    }.apply { isEnabled = canScaleUp }

    companion object {
        private const val DEFAULT_SIZE = 100

        private var gridSizes = listOf(
            25,
            50,
            100,
            200,
            400,
            800
        )
    }

    init {
        currentSize = DEFAULT_SIZE
    }
}
