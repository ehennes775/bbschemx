package combos.color

import views.ColorComboEntry
import java.awt.*
import javax.swing.*

class ColorComboRenderer: ListCellRenderer<ColorComboEntry> {

    private val colorLabel = JLabel("ColorName")

    private val colorSwatch = object: JPanel() {
        var swatchColor: Color = Color.ORANGE

        override fun paintComponent(graphics: Graphics?) {
            graphics!!.apply {
                super.paintComponent(this)
                color = swatchColor
                fillRect(0, 0, width, height)
            }
        }
    }.apply {
        border = BorderFactory.createLineBorder(Color.BLACK, 3)
        preferredSize = Dimension(30, 20)
    }

    private val colorEntry = JPanel().apply {
        border = BorderFactory.createEmptyBorder(2, 2, 2, 2)
        layout = BorderLayout()
        add(colorSwatch, BorderLayout.WEST)
        add(colorLabel, BorderLayout.CENTER)
    }

    private val emptyEntry = JLabel("- Multiple Colors -").apply {
        this.foreground = Color.GRAY
    }

    private val tingLayout = CardLayout()

    private val ting = JPanel().apply {
        layout = tingLayout
        add("goodbye", colorEntry)
        add("hello", emptyEntry)
    }

    override fun getListCellRendererComponent(
        list: JList<out ColorComboEntry>?,
        value: ColorComboEntry?,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        tingLayout.show(
            ting, if (index < 0)
                "goodbye" //""hello"
            else
                "goodbye"
        )

        value?.also {
            colorLabel.text = it.colorName
            colorSwatch.swatchColor = it.color
        }

        colorEntry.background = list!!.let {
            if (isSelected) {
                it.selectionBackground
            } else {
                it.background
            }
        }

        return ting
    }

}