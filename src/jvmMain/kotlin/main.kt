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

        title = "bbschemx"
        setSize(1000, 500)
        defaultCloseOperation = EXIT_ON_CLOSE
        jMenuBar = createMenu()
        contentPane = createContent()
    }

    private fun createContent(): Container {
        return JPanel().apply {
            layout = BorderLayout()
            add(JPanel().apply {
                layout = BoxLayout(this, BoxLayout.Y_AXIS)
                add(ColorEditor(SchematicView()))
                Box.createVerticalGlue()
                add(LineEditor(SchematicView()))
                Box.createVerticalGlue()
                add(FillEditor(SchematicView()))
            }, BorderLayout.WEST)
            add(TabbedPane.apply {
                addTab("Thing 1", SchematicView())
                addTab("Thing 2", SchematicView())
            }, BorderLayout.CENTER)
        }
    }

    private fun createMenu(): JMenuBar {
        return JMenuBar().apply {
            add(JMenu("File").apply {
                add(JMenuItem(NewAction))
                add(JMenuItem(OpenAction()))
                addSeparator()
                add(JMenuItem(SaveAction))
                add(JMenuItem(SaveAllAction))
            })
            add(JMenu("Edit").apply {
                add(JMenuItem(UndoAction))
                add(JMenuItem(RedoAction))
                addSeparator()
                add(JMenuItem(CutAction))
                add(JMenuItem(CopyAction))
                add(JMenuItem(PasteAction))
            })
        }
    }

    private object TabbedPane : JTabbedPane()

    private object CopyAction : AbstractAction("Copy") {
        override fun actionPerformed(e: ActionEvent?) {
            TabbedPane.selectedComponent.apply {
                if (this is CopyCapable) {
                    this.copy()
                }
            }
        }
    }

    private object CutAction : AbstractAction("Cut") {
        override fun actionPerformed(e: ActionEvent?) {
            TabbedPane.selectedComponent.apply {
                if (this is CutCapable) {
                    this.cut()
                }
            }
        }
    }

    private object NewAction : AbstractAction("New") {
        override fun actionPerformed(e: ActionEvent?) {
            TabbedPane.addTab("Untitled", SchematicView())
        }
    }

    private inner class OpenAction : AbstractAction("Open...") {
        var dialog = FileDialog(
            this@Application,
            "Open...",
            FileDialog.LOAD
        ).apply {
            isMultipleMode = true
        }

        override fun actionPerformed(e: ActionEvent?) {
            dialog.isVisible = true
            dialog.files.forEach {
                TabbedPane.addTab(it.name, SchematicView.load(it))
            }
        }
    }

    private object PasteAction : AbstractAction("Paste") {
        override fun actionPerformed(e: ActionEvent?) {
            TabbedPane.selectedComponent.apply {
                if (this is PasteCapable) {
                    this.paste()
                }
            }
        }
    }

    private object RedoAction : AbstractAction("Redo") {
        override fun actionPerformed(e: ActionEvent?) {
            TabbedPane.selectedComponent.apply {
                if (this is RedoCapable) {
                    this.redo()
                }
            }
        }
    }

    private object SaveAction : AbstractAction("Save") {
        override fun actionPerformed(e: ActionEvent?) {
            TabbedPane.selectedComponent.apply {
                if (this is SaveCapable) {
                    this.save()
                }
            }
        }
    }

    private object SaveAllAction : AbstractAction("Save All") {
        override fun actionPerformed(e: ActionEvent?) {
            for (index in 0..TabbedPane.tabCount) {
                TabbedPane.getComponentAt(index).apply {
                    if (this is SaveCapable) {
                        this.save()
                    }
                }
            }
        }
    }

    private object UndoAction : AbstractAction("Undo") {
        override fun actionPerformed(e: ActionEvent?) {
            TabbedPane.selectedComponent.apply {
                if (this is UndoCapable) {
                    this.undo()
                }
            }
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
