package views.library

import javax.swing.event.TreeModelEvent
import javax.swing.event.TreeModelListener
import javax.swing.tree.TreeModel
import javax.swing.tree.TreeNode
import javax.swing.tree.TreePath

class LibraryModel: TreeModel {

    private val listeners = mutableListOf<TreeModelListener>()

    override fun addTreeModelListener(l: TreeModelListener?) {
        l?.let { listeners.add(it) }
    }

    override fun removeTreeModelListener(l: TreeModelListener?) {
        l?.let { listeners.remove(it) }
    }

    fun fireNodesAdded(source: TreeNode, indices: IntArray, nodes: Array<TreeNode>) {
        val event = TreeModelEvent(source, TreePath(source), indices, nodes)
        listeners.forEach { it.treeNodesInserted(event) }
    }

    fun fireNodesRemoved(source: TreeNode, indices: IntArray, nodes: Array<TreeNode>) {
        val event = TreeModelEvent(source, TreePath(source), indices, nodes)
        listeners.forEach { it.treeNodesRemoved(event) }
    }


    override fun getRoot() = library

    override fun getChild(parent: Any?, index: Int): Any = (parent as TreeNode).getChildAt(index)

    override fun getChildCount(parent: Any?) = (parent as TreeNode).childCount

    override fun isLeaf(node: Any?): Boolean = (node as TreeNode).isLeaf

    override fun valueForPathChanged(path: TreePath?, newValue: Any?) {
    }


    override fun getIndexOfChild(parent: Any?, child: Any?) = (parent as TreeNode).getIndex(child as TreeNode)

    private val library = SymbolLibrary(this)
}
