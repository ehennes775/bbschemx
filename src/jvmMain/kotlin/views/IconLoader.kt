package views

import javax.swing.ImageIcon

interface IconLoader {
    fun loadIcon(name: String): ImageIcon
}