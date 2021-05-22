package views.library

import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import javax.swing.tree.TreeNode

class SymbolFile(private val parent: TreeNode, val path: Path): TreeNode {

    init {
        require(Files.isRegularFile(path))
    }

    fun exists(): Boolean = Files.exists(path)

    override fun children(): Enumeration<out TreeNode>? = Collections.emptyEnumeration()

    override fun getAllowsChildren() = false

    override fun getChildAt(childIndex: Int) = null

    override fun getChildCount() = 0

    override fun getIndex(node: TreeNode?) = 0

    override fun getParent() = parent

    override fun isLeaf() = true

    override fun toString() = path.fileName.toString()
}