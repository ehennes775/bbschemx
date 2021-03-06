import actions.*
import scheme.ColorSchemeMenu
import settings.JavaSettingsSource
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
import views.*
import views.attribute.AttributePanel
import views.document.DocumentView
import views.library.LibraryPanel
import editors.ColorEditor
import views.schematic.JavaSchematicView
import views.schematic.keys.KeyHandler
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ComponentListener
import java.awt.event.ContainerListener
import javax.swing.*
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener
import javax.swing.plaf.FontUIResource

class Application : JFrame(), IconLoader {

   init {
        //UIManager.setLookAndFeel(DarculaLaf())
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName())

        val font = FontUIResource("Sans", Font.PLAIN, 16)

        UIManager.getDefaults().keys.forEach {
            if (UIManager.get(it) is FontUIResource) {
                UIManager.put(it, font)
            }
        }
    }


    private val settingsSource: JavaSettingsSource = JavaSettingsSource().also {
        it.colorScheme
        it.lineWidth
        it.dashLength
        it.dashSpace
    }

    private val colorScheme = ColorScheme(settingsSource)
    private val colorSchemeMenu = ColorSchemeMenu(colorScheme, settingsSource)

    private val tabbedDocumentPane = JTabbedPane().apply {
        //addTab("Thing 1", SchematicView())
        //addTab("Thing 2", SchematicView())
    }


    private val toolTarget = ToolThing(tabbedDocumentPane)


    private val arcToolAction = object : ToolAction(ImageIcon(this.javaClass.getResource("24x24/ArcTool.png")), toolTarget, ArcTool) {}
    private val busToolAction = object : ToolAction(ImageIcon(this.javaClass.getResource("24x24/BusTool.png")), toolTarget, BusTool) {}
    private val boxToolAction = object : ToolAction(ImageIcon(this.javaClass.getResource("24x24/BoxTool.png")), toolTarget, BoxTool) {}
    private val circleToolAction = object : ToolAction(ImageIcon(this.javaClass.getResource("24x24/CircleTool.png")), toolTarget, CircleTool) {}
    private val lineToolAction = object : ToolAction(ImageIcon(this.javaClass.getResource("24x24/LineTool.png")), toolTarget, LineTool) {}
    private val netToolAction = object : ToolAction(ImageIcon(this.javaClass.getResource("24x24/NetTool.png")), toolTarget, NetTool) {}
    private val pinToolAction = object : ToolAction(ImageIcon(this.javaClass.getResource("24x24/PinTool.png")), toolTarget, PinTool) {}
    private val selectToolAction = object : ToolAction(ImageIcon(this.javaClass.getResource("24x24/SelectTool.png")), toolTarget, SelectTool) {}
    private val zoomToolAction = object : ToolAction(ImageIcon(this.javaClass.getResource("24x24/ZoomTool.png")), toolTarget, ZoomTool) {}

    private val clipboardActions = ClipboardActions(tabbedDocumentPane)
    private val gridAction = GridAction("Grid Mode", tabbedDocumentPane)
    private val gridSizeActions: GridSizeActions = GridSizeActions(tabbedDocumentPane)
    private val revealAction = RevealAction("Reveal", tabbedDocumentPane)
    private val selectActions = SelectActions(tabbedDocumentPane)
    private val undoRedoActions = UndoRedoActions(tabbedDocumentPane)
    private val zoomActions = ZoomActions(tabbedDocumentPane, this)

    private val alternateFormAction = AlternateFormAction(toolTarget)

    private val libraryTree = LibraryPanel(colorScheme)

    private val attributePanel = AttributePanel(tabbedDocumentPane)

    private val keyHandler = KeyHandler(tabbedDocumentPane)

    private val colorEditor = ColorEditor(colorScheme, settingsSource)

    private val propertyPanel = JPanel().apply {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        add(colorEditor)
    }

    private val tabbedToolPane = JTabbedPane().apply {
        addTab("Library", libraryTree)
        addTab("Attributes", attributePanel)
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
            add(alternateFormAction) // TODO temporary
            add(colorSchemeMenu.colorSchemeMenu)
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
        add(zoomActions.zoomInAction)
        add(zoomActions.zoomOutAction)
        add(zoomActions.zoomExtentsAction)
        createToolButtons(zoomToolAction).forEach {
            add(it)
            toolGroup.add(it)
        }
        addSeparator()
        add(revealAction.createToolbarButton())
        addSeparator()
        add(alternateFormAction)
    }

    private fun createToolButtons(vararg actions: AbstractAction) = actions.map { JToggleButton(it) }

    private fun createMain() = JSplitPane().apply {
        orientation = JSplitPane.HORIZONTAL_SPLIT
        leftComponent = tabbedToolPane
        rightComponent = tabbedDocumentPane
    }


    private inner class NewAction : DocumentAction("New", tabbedDocumentPane) {
        override fun calculateEnabled(currentDocument: DocumentView) = true

        override fun actionPerformed(e: ActionEvent?) {
            tabbedDocumentPane.addTab("Untitled", JavaSchematicView(colorScheme))
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
                tabbedDocumentPane.addTab(it.name, JavaSchematicView.load(colorScheme, it))
            }
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

    override fun loadIcon(name: String): ImageIcon = ImageIcon(this.javaClass.getResource(name))



    private val thingListener = ChangeListener {
        colorEditor.schematicModel = (tabbedDocumentPane.selectedComponent as? SchematicView)?.schematicModel
    }

    init {
        tabbedDocumentPane.addChangeListener(thingListener)
    }
}


fun main() {
    EventQueue.invokeLater {
        Application().apply {
            isVisible = true
        }
    }
}
