import actions.DocumentAction
import views.document.DocumentView
import views.schematic.ColorEditor
import views.schematic.FillEditor
import views.schematic.LineEditor
import views.schematic.SchematicView
import java.awt.*
import java.awt.event.ActionEvent
import javax.swing.*

class Application : JFrame() {

    init {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName())
    }

    private val tabbedPane = JTabbedPane().apply {
        addTab("Thing 1", SchematicView())
        addTab("Thing 2", SchematicView())
    }

    init {
        title = "bbschemx"
        setSize(1000, 500)
        defaultCloseOperation = EXIT_ON_CLOSE
        jMenuBar = createMenu()
        contentPane = createContent()
    }


    private fun createContent() = JPanel().apply {
        layout = BorderLayout()
        add(JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            add(ColorEditor(SchematicView()))
            Box.createVerticalGlue()
            add(LineEditor(SchematicView()))
            Box.createVerticalGlue()
            add(FillEditor(SchematicView()))
        }, BorderLayout.WEST)
        add(tabbedPane, BorderLayout.CENTER)
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
            add(JMenuItem(UndoAction()))
            add(JMenuItem(RedoAction()))
            addSeparator()
            add(JMenuItem(CutAction()))
            add(JMenuItem(CopyAction()))
            add(JMenuItem(PasteAction()))
            addSeparator()
            add(JMenuItem(SelectAllAction()))
            add(JMenuItem(SelectNoneAction()))
        })
    }

    private inner class CopyAction : DocumentAction("Copy", tabbedPane) {
        override fun calculateEnabled(currentDocument: DocumentView) =
            currentDocument.let { it is CopyCapable && it.canCopy }

        override fun actionPerformed(e: ActionEvent?) {
            currentDocument.also { if (it is CopyCapable) it.copy() }
        }
    }

    private inner class CutAction : DocumentAction("Cut", tabbedPane) {
        override fun calculateEnabled(currentDocument: DocumentView) =
            currentDocument.let { it is CutCapable && it.canCut }

        override fun actionPerformed(e: ActionEvent?) {
            currentDocument.also { if (it is CutCapable) it.cut() }
        }
    }

    private inner class NewAction : DocumentAction("New", tabbedPane) {
        override fun calculateEnabled(currentDocument: DocumentView) = true

        override fun actionPerformed(e: ActionEvent?) {
            tabbedPane.addTab("Untitled", SchematicView())
        }
    }

    private inner class OpenAction : DocumentAction("Open...", tabbedPane) {
        var dialog = FileDialog(this@Application, "Open...", FileDialog.LOAD).apply {
            isMultipleMode = true
        }

        override fun calculateEnabled(currentDocument: DocumentView) = true

        override fun actionPerformed(e: ActionEvent?) {
            dialog.isVisible = true
            dialog.files.forEach {
                tabbedPane.addTab(it.name, SchematicView.load(it))
            }
        }
    }

    private inner class PasteAction : DocumentAction("Paste", tabbedPane) {
        override fun calculateEnabled(currentDocument: DocumentView) =
            currentDocument.let { it is PasteCapable && it.canPaste }

        override fun actionPerformed(e: ActionEvent?) {
            currentDocument.also { if (it is PasteCapable) it.paste() }
        }
    }

    private inner class RedoAction : DocumentAction("Redo", tabbedPane) {
        override fun calculateEnabled(currentDocument: DocumentView) =
            currentDocument.let { it is RedoCapable && it.canRedo }

        override fun actionPerformed(e: ActionEvent?) {
            currentDocument.also { if (it is RedoCapable) it.redo() }
        }
    }

    private inner class SaveAction : DocumentAction("Save", tabbedPane) {
        override fun calculateEnabled(currentDocument: DocumentView) =
            currentDocument.let { it is SaveCapable && it.canSave }

        override fun actionPerformed(e: ActionEvent?) {
            currentDocument.also { if (it is SaveCapable) it.save() }
        }
    }

    private inner class SaveAllAction : DocumentAction("Save All", tabbedPane) {
        override fun calculateEnabled(currentDocument: DocumentView) =
            tabbedPane.components.any { it is SaveCapable && it.canSave }

        override fun actionPerformed(e: ActionEvent?) {
            tabbedPane.components.forEach { document ->
                document.also { if (it is SaveCapable) it.save() }
            }
        }
    }

    private inner class UndoAction : DocumentAction("Undo", tabbedPane) {
        override fun calculateEnabled(currentDocument: DocumentView) =
            currentDocument.let { it is CopyCapable && it.canCopy }

        override fun actionPerformed(e: ActionEvent?) {
            currentDocument.also { if (it is UndoCapable) it.undo() }
        }
    }

    private inner class SelectAllAction : DocumentAction("Select All", tabbedPane) {
        override fun calculateEnabled(currentDocument: DocumentView) =
            currentDocument.let { it is SelectCapable && it.canSelectAll }

        override fun actionPerformed(e: ActionEvent?) {
            currentDocument.also { if (it is SelectCapable) it.selectAll() }
        }
    }

    private inner class SelectNoneAction : DocumentAction("Select None", tabbedPane) {
        override fun calculateEnabled(currentDocument: DocumentView) =
            currentDocument.let { it is SelectCapable && it.canSelectNone }

        override fun actionPerformed(e: ActionEvent?) {
            currentDocument.also { if (it is SelectCapable) it.selectNone() }
        }
    }
}

fun main() {
    EventQueue.invokeLater {
        Application().apply {
            isVisible = true
        }
    }
}
