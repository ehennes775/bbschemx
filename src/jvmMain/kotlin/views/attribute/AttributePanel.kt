package views.attribute

import views.attribute.name.AttributeName
import views.attribute.name.NameEditor
import views.attribute.name.NameRenderer
import views.attribute.value.AttributeValue
import views.attribute.value.ValueEditor
import views.attribute.value.ValueRenderer
import views.schematic.SchematicView
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.*
import javax.swing.event.ChangeListener

class AttributePanel(private val tabbedPane: JTabbedPane): JPanel() {

    private val attributeTable = JTable().apply {
        intercellSpacing = INTERCELL_SPACING
        rowHeight += 10

        setDefaultEditor(AttributeName::class.java, NameEditor())
        setDefaultRenderer(AttributeName::class.java, NameRenderer())
        setDefaultEditor(AttributeValue::class.java, ValueEditor())
        setDefaultRenderer(AttributeValue::class.java, ValueRenderer())
    }

    init {
        border = BORDER
        layout = BorderLayout()

        add(JScrollPane(attributeTable), BorderLayout.CENTER)
    }

    private val changeListener = ChangeListener {
        attributeTable.model = (tabbedPane.selectedComponent as? SchematicView)?.let {
            AttributeModel(it.schematicModel)
        }
    }

    init {
        tabbedPane.addChangeListener(changeListener)
    }

    companion object {

        private val INTERCELL_SPACING = Dimension(5, 5)

        private val BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5)
    }
}
