package views.library

import models.schematic.Schematic
import views.schematic.SchematicView
import views.schematic.io.JavaBasedReader
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTree
import javax.swing.event.TreeSelectionListener

class LibraryPanel: JPanel() {
    private val libraryModel = LibraryModel()

    private val libraryPreview = SchematicView().apply {
        minimumSize = Dimension(600, 300)
        preferredSize = Dimension(600, 300)
        maximumSize = Dimension(600, 300)
    }

    private val treeSelectionListener: TreeSelectionListener = TreeSelectionListener {
        libraryTree.let { tree ->
            val item = tree.selectionPaths.single().lastPathComponent as SymbolFile
            libraryPreview.schematic = item?.let {
                val reader = JavaBasedReader(item.path.toFile().bufferedReader())
                Schematic.read(reader)
            } ?: Schematic()
        }
    }

    private val libraryTree = JTree().apply {
        model = libraryModel
        addTreeSelectionListener(treeSelectionListener)
    }


    init {
        border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
        layout = GridBagLayout()

        add(libraryPreview, GridBagConstraints().apply {
            gridx = 0
            gridy = 0
            fill = GridBagConstraints.BOTH
            weightx = 1.0
            weighty = 0.0
        })

        add(JScrollPane(libraryTree), GridBagConstraints().apply {
            gridx = 0
            gridy = 1
            fill = GridBagConstraints.BOTH
            weightx = 1.0
            weighty = 1.0
        })
    }
}