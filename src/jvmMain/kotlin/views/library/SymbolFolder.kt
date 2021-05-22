package views.library

import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.stream.Collectors
import javax.swing.event.TreeModelEvent
import javax.swing.tree.TreeNode

class SymbolFolder(
    private val model: LibraryModel,
    private val parent: TreeNode,
    private val path: Path
    ): TreeNode {

    init {
        require(Files.isDirectory(path))
    }

    private var files = Files.walk(path, 1)
        .filter { Files.isRegularFile(it) }
        .map { SymbolFile(this, it) }
        .collect(Collectors.toList())

    override fun getChildAt(childIndex: Int): SymbolFile = files[childIndex]
    override fun getChildCount() = files.size
    override fun getParent() = parent
    override fun getIndex(node: TreeNode?) = files.indexOf(node)
    override fun getAllowsChildren() = true
    override fun isLeaf() = false
    override fun children(): Enumeration<SymbolFile> = Collections.enumeration(files)

    override fun toString() = path.fileName.toString()

    data class RemoveStatus(val index: Int, val file: SymbolFile) {
        val exists = file.exists()
    }

    private fun removeMissing() {
        val removeStatus = files.mapIndexed { index, file -> RemoveStatus(index, file) }
        files = removeStatus.filter { it.exists }.map { it.file }.toList()
        removeStatus.filter { !it.exists }.let { removeStatus ->
            model.fireNodesRemoved(
                this,
                removeStatus.map { it.index }.toIntArray(),
                removeStatus.map { it.file }.toTypedArray()
                )
        }
    }

}