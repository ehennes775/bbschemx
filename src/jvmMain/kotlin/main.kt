import actions.*
import tools.arc.ArcTool
import tools2.ToolAction
import tools2.ToolThing
import tools.box.BoxTool
import tools.bus.BusTool
import tools.circle.CircleTool
import tools.line.LineTool
import tools.net.NetTool
import tools.pin.PinTool
import tools.select.SelectTool
import tools.zoom.ZoomTool
import views.IconLoader
import views.document.DocumentView
import views.library.LibraryPanel
import views.schematic.ColorEditor
import views.schematic.FillEditor
import views.schematic.LineEditor
import views.schematic.SchematicView
import java.awt.*
import java.awt.event.ActionEvent
import javax.swing.*
import javax.swing.plaf.FontUIResource

class Application : JFrame(), IconLoader {

    init {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName())

        val font = FontUIResource("Sans", Font.PLAIN, 16)

        UIManager.getDefaults().keys.forEach {
            if (UIManager.get(it) is FontUIResource) {
                UIManager.put(it, font)
            }
        }
    }


    private val tabbedDocumentPane = JTabbedPane().apply {
        //addTab("Thing 1", SchematicView())
        //addTab("Thing 2", SchematicView())
    }


    private val toolTarget = ToolThing(tabbedDocumentPane)


    private val arcToolAction = object : ToolAction(ImageIcon(this.javaClass.getResource("ArcTool.png")), toolTarget, ArcTool) {}
    private val busToolAction = object : ToolAction(ImageIcon(this.javaClass.getResource("BusTool.png")), toolTarget, BusTool) {}
    private val boxToolAction = object : ToolAction(ImageIcon(this.javaClass.getResource("BoxTool.png")), toolTarget, BoxTool) {}
    private val circleToolAction = object : ToolAction(ImageIcon(this.javaClass.getResource("CircleTool.png")), toolTarget, CircleTool) {}
    private val lineToolAction = object : ToolAction(ImageIcon(this.javaClass.getResource("LineTool.png")), toolTarget, LineTool) {}
    private val netToolAction = object : ToolAction(ImageIcon(this.javaClass.getResource("NetTool.png")), toolTarget, NetTool) {}
    private val pinToolAction = object : ToolAction(ImageIcon(this.javaClass.getResource("PinTool.png")), toolTarget, PinTool) {}
    private val selectToolAction = object : ToolAction(ImageIcon(this.javaClass.getResource("SelectTool.png")), toolTarget, SelectTool) {}
    private val zoomToolAction = object : ToolAction(ImageIcon(this.javaClass.getResource("ZoomTool.png")), toolTarget, ZoomTool) {}

    private val clipboardActions = ClipboardActions(tabbedDocumentPane)
    private val gridAction = GridAction("Grid Mode", tabbedDocumentPane)
    private val gridSizeActions: GridSizeActions = GridSizeActions(tabbedDocumentPane)
    private val revealAction = RevealAction("Reveal", tabbedDocumentPane)
    private val selectActions = SelectActions(tabbedDocumentPane)
    private val undoRedoActions = UndoRedoActions(tabbedDocumentPane)
    private val zoomActions = ZoomActions(tabbedDocumentPane, this)

    private val libraryTree = LibraryPanel()

    private val propertyPanel = JPanel().apply {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        add(ColorEditor(SchematicView()))
        Box.createVerticalGlue()
        add(LineEditor(SchematicView()))
        Box.createVerticalGlue()
        add(FillEditor(SchematicView()))
    }

    private val tabbedToolPane = JTabbedPane().apply {
        addTab("Library", libraryTree)
        addTab("Properties", propertyPanel)
    }

    init {
        title = "bbschemx"
        setSize(1000, 500)
        defaultCloseOperation = EXIT_ON_CLOSE

        jMenuBar = createMenu()

        contentPane.also {
            add(createToolBar(), BorderLayout.NORTH)
            add(createMain(), BorderLayout.CENTER)
        }
    }

    private fun createMenu() = JMenuBar().apply {
        add(JMenu("File").apply {
            add(JMenuItem(NewAction()))
            add(JMenuItem(OpenAction()))
            addSeparator()
            add(JMenuItem(SaveAction()))
            add(JMenuItem(SaveAllAction()))
        })
        add(JMenu("Edit").apply {
            add(undoRedoActions.undoAction)
            add(undoRedoActions.redoAction)
            addSeparator()
            add(clipboardActions.cutAction)
            add(clipboardActions.copyAction)
            add(clipboardActions.pasteAction)
            addSeparator()
            add(selectActions.selectAllAction)
            add(selectActions.selectNoneAction)
        })
        add(JMenu("View").apply {
            add(zoomActions.zoomExtentsAction)
            add(zoomActions.zoomInAction)
            add(zoomActions.zoomOutAction)
            addSeparator()
            add(gridAction.createMenuItem())
            add(revealAction.createMenuItem())
            addSeparator()
            add(gridSizeActions.resetGridAction)
            add(gridSizeActions.scaleDownAction)
            add(gridSizeActions.scaleUpAction)
        })
    }

    private fun createToolBar() = JToolBar().apply {
        layout = FlowLayout(FlowLayout.LEFT)
        val toolGroup = ButtonGroup()
        createToolButtons(selectToolAction).forEach {
            add(it)
            toolGroup.add(it)
        }
        addSeparator()
        createToolButtons(arcToolAction, boxToolAction, circleToolAction, lineToolAction).forEach {
            add(it)
            toolGroup.add(it)
        }
        addSeparator()
        createToolButtons(busToolAction, netToolAction, pinToolAction).forEach {
            add(it)
            toolGroup.add(it)
        }
        addSeparator()
        createToolButtons(zoomToolAction).forEach {
            add(it)
            toolGroup.add(it)
        }
        add(revealAction.createToolbarButton())
        addSeparator()
        add(zoomActions.zoomInAction)
        add(zoomActions.zoomOutAction)
        add(zoomActions.zoomExtentsAction)
    }

    private fun createToolButtons(vararg actions: AbstractAction) = actions.map { JToggleButton(it) }

    private fun createMain() = JSplitPane().apply {
        orientation = JSplitPane.HORIZONTAL_SPLIT
        leftComponent = tabbedToolPane
        rightComponent = tabbedDocumentPane
    }


    private inner class CopyAction : DocumentAction("Copy", tabbedDocumentPane) {
        override fun calculateEnabled(currentDocument: DocumentView) =
            currentDocument.let { it is CopyCapable && it.canCopy }

        override fun actionPerformed(e: ActionEvent?) {
            currentDocument.also { if (it is CopyCapable) it.copy() }
        }
    }

    private inner class CutAction : DocumentAction("Cut", tabbedDocumentPane) {
        override fun calculateEnabled(currentDocument: DocumentView) =
            currentDocument.let { it is CutCapable && it.canCut }

        override fun actionPerformed(e: ActionEvent?) {
            currentDocument.also { if (it is CutCapable) it.cut() }
        }
    }

    private inner class NewAction : DocumentAction("New", tabbedDocumentPane) {
        override fun calculateEnabled(currentDocument: DocumentView) = true

        override fun actionPerformed(e: ActionEvent?) {
            tabbedDocumentPane.addTab("Untitled", SchematicView())
        }
    }

    private inner class OpenAction : DocumentAction("Open...", tabbedDocumentPane) {
        var dialog = FileDialog(this@Application, "Open...", FileDialog.LOAD).apply {
            isMultipleMode = true
        }

        override fun calculateEnabled(currentDocument: DocumentView) = true

        override fun actionPerformed(e: ActionEvent?) {
            dialog.isVisible = true
            dialog.files.forEach {
                tabbedDocumentPane.addTab(it.name, SchematicView.load(it))
            }
        }
    }

    private inner class PasteAction : DocumentAction("Paste", tabbedDocumentPane) {
        override fun calculateEnabled(currentDocument: DocumentView) =
            currentDocument.let { it is PasteCapable && it.canPaste }

        override fun actionPerformed(e: ActionEvent?) {
            currentDocument.also { if (it is PasteCapable) it.paste() }
        }
    }

    private inner class RedoAction : DocumentAction("Redo", tabbedDocumentPane) {
        override fun calculateEnabled(currentDocument: DocumentView) =
            currentDocument.let { it is RedoCapable && it.canRedo }

        override fun actionPerformed(e: ActionEvent?) {
            currentDocument.also { if (it is RedoCapable) it.redo() }
        }
    }

    private inner class SaveAction : DocumentAction("Save", tabbedDocumentPane) {
        override fun calculateEnabled(currentDocument: DocumentView) =
            currentDocument.let { it is SaveCapable && it.canSave }

        override fun actionPerformed(e: ActionEvent?) {
            currentDocument.also { if (it is SaveCapable) it.save() }
        }
    }

    private inner class SaveAllAction : DocumentAction("Save All", tabbedDocumentPane) {
        override fun calculateEnabled(currentDocument: DocumentView) =
            tabbedDocumentPane.components.any { it is SaveCapable && it.canSave }

        override fun actionPerformed(e: ActionEvent?) {
            tabbedDocumentPane.components.forEach { document ->
                document.also { if (it is SaveCapable) it.save() }
            }
        }
    }

    private inner class UndoAction : DocumentAction("Undo", tabbedDocumentPane) {
        override fun calculateEnabled(currentDocument: DocumentView) =
            currentDocument.let { it is CopyCapable && it.canCopy }

        override fun actionPerformed(e: ActionEvent?) {
            currentDocument.also { if (it is UndoCapable) it.undo() }
        }
    }

    private inner class SelectAllAction : DocumentAction("Select All", tabbedDocumentPane) {
        override fun calculateEnabled(currentDocument: DocumentView) =
            currentDocument.let { it is SelectCapable && it.canSelectAll }

        override fun actionPerformed(e: ActionEvent?) {
            currentDocument.also { if (it is SelectCapable) it.selectAll() }
        }
    }

    private inner class SelectNoneAction : DocumentAction("Select None", tabbedDocumentPane) {
        override fun calculateEnabled(currentDocument: DocumentView) =
            currentDocument.let { it is SelectCapable && it.canSelectNone }

        override fun actionPerformed(e: ActionEvent?) {
            currentDocument.also { if (it is SelectCapable) it.selectNone() }
        }
    }

    override fun loadIcon(name: String): ImageIcon = ImageIcon(this.javaClass.getResource(name))
}


fun main() {
    EventQueue.invokeLater {
        Application().apply {
            isVisible = true
        }
    }
}
