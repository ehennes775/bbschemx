package views.library

import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import javax.swing.tree.TreeNode


class SymbolLibrary(
    private val model: LibraryModel
) : TreeNode {

    private val folders = mutableListOf<SymbolFolder>()

    init {
        addFolder(Paths.get("/Users/ehennes/Documents/GitHub/edalib/symbols"))
    }

    override fun getChildAt(childIndex: Int) = folders[childIndex]
    override fun getChildCount() = folders.size
    override fun getParent() = null
    override fun getIndex(node: TreeNode?) = folders.indexOf(node)
    override fun getAllowsChildren() = true
    override fun isLeaf() = false
    override fun children(): Enumeration<SymbolFolder> = Collections.enumeration(folders.toList())

    override fun toString() = "Symbol Library"

    private fun addFolder(path: Path) {
        SymbolFolder(model, this, path).also {
            folders.add(it)
            model.fireNodesAdded(this, intArrayOf(folders.indexOf(it)), arrayOf(it))
        }
    }
}