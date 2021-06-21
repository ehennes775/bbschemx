package models.schematic

import models.schematic.io.Reader
import models.schematic.listeners.InvalidateListener
import models.schematic.listeners.PropertyListener
import models.schematic.listeners.SelectionListener
import models.schematic.shapes.pin.Pin
import models.schematic.shapes.pin.PinType
import models.schematic.shapes.text.Alignment
import models.schematic.shapes.text.Text
import models.schematic.types.*
import types.Drawer
import types.RevealMode

class SchematicModel(schematic: Schematic) {

    private var currentState: State = State(schematic, setOf())
    private val redoStack = mutableListOf<State>()
    private val undoStack = mutableListOf<State>()

    val items: List<Item> get() = currentState.schematic.items

    private val invalidateListeners = mutableListOf<InvalidateListener>()

    fun addInvalidateListener(listener: InvalidateListener) {
        invalidateListeners.add(listener)
    }

    fun removeInvalidateListener(listener: InvalidateListener) {
        invalidateListeners.remove(listener)
    }

    private fun fireInvalidateAll() {
        invalidateListeners.forEach { it.invalidateAll() }
    }

    fun fireInvalidateItem(item: Item) {
        invalidateListeners.forEach { it.invalidateItem(item) }
    }


    private val propertyListeners = mutableListOf<PropertyListener>()

    fun addPropertyListener(listener: PropertyListener) {
        propertyListeners.add(listener)
    }

    fun removePropertyListener(listener: PropertyListener) {
        propertyListeners.remove(listener)
    }

    fun firePropertyListener() {
        propertyListeners.forEach { it.propertyChange() }
    }


    private val selectionListeners = mutableListOf<SelectionListener>()

    fun addSelectionListener(listener: SelectionListener) {
        selectionListeners.add(listener)
    }

    fun removeSelectionListener(listener: SelectionListener) {
        selectionListeners.remove(listener)
    }

    fun fireSelectionChanged() {
        selectionListeners.forEach { it.selectionChanged() }
    }


    fun queryCapType() = items
        .mapNotNull { it as? LineItem }
        .map { it.lineStyle.capType }
        .toQueryResult()

    fun queryDashLength() = items
        .mapNotNull { it as? LineItem }
        .filter { it.lineStyle.dashType.usesLength }
        .map { it.lineStyle.dashLength }
        .toQueryResult()

    fun queryDashSpace() = items
        .mapNotNull { it as? LineItem }
        .filter { it.lineStyle.dashType.usesSpace }
        .map { it.lineStyle.dashSpace }
        .toQueryResult()

    fun queryDashType() = items
        .mapNotNull { it as? LineItem }
        .map { it.lineStyle.dashType }
        .toQueryResult()

    fun queryFillAngle1() = items
        .mapNotNull { it as? FillItem }
        .filter { it.fillStyle.fillType.usesFirstSet }
        .map { it.fillStyle.fillAngle1 }
        .toQueryResult()

    fun queryFillAngle2() = items
        .mapNotNull { it as? FillItem }
        .filter { it.fillStyle.fillType.usesSecondSet }
        .map { it.fillStyle.fillAngle2 }
        .toQueryResult()

    fun queryFillPitch1() = items
        .mapNotNull { it as? FillItem }
        .filter { it.fillStyle.fillType.usesFirstSet }
        .map { it.fillStyle.fillPitch1 }
        .toQueryResult()

    fun queryFillPitch2() = items
        .mapNotNull { it as? FillItem }
        .filter { it.fillStyle.fillType.usesSecondSet }
        .map { it.fillStyle.fillPitch2 }
        .toQueryResult()

    fun queryFillType() = items
        .mapNotNull { it as? FillItem }
        .map { it.fillStyle.fillType }
        .toQueryResult()

    fun queryFillWidth() = items
        .mapNotNull { it as? FillItem }
        .filter { it.fillStyle.fillType.usesFillWidth }
        .map { it.fillStyle.fillWidth }
        .toQueryResult()

    fun queryItemColor() = items
        .mapNotNull { it as? ColorItem }
        .map { it.color }
        .toQueryResult()

    fun queryLineWidth() = items
        .mapNotNull { it as? LineItem }
        .map { it.lineStyle.lineWidth }
        .toQueryResult()

    fun queryPinType() = items
        .mapNotNull { it as? Pin }
        .map { it.pinType }
        .toQueryResult()

    fun queryTextAlignment() = items
        .mapNotNull { it as? Text }
        .map { it.alignment }
        .toQueryResult()

    fun queryTextColor() = items
        .mapNotNull { it as? Text }
        .map { it.color }
        .toQueryResult()

    fun queryTextRotation() = items
        .mapNotNull { it as? Text }
        .map { it.rotation }
        .toQueryResult()

    fun queryTextSize() = items
        .mapNotNull { it as? Text }
        .map { it.size }
        .toQueryResult()

    fun queryTextVisibility() = items
        .mapNotNull { it as? Text }
        .map { it.visibility }
        .toQueryResult()

    private inline fun <reified T> applyProperty(crossinline apply: (T) -> Item) {
        currentState = State(
            currentState.schematic.map { if (it is T) apply(it) else it },
            currentState.selection
        )
        firePropertyListener()
        fireSelectionChanged()
        fireInvalidateAll()
    }


    fun setCapType(newCapType: CapType) {
        if (SelectedValue.Single(newCapType) != queryCapType()) {
            applyProperty<LineItem> { item ->
                item.applyLineStyle { it.withCapType(newCapType) }
            }
        }
    }

    fun setDashLength(newDashLength: Int) {
        if (SelectedValue.Single(newDashLength) != queryDashLength()) {
            applyProperty<LineItem> { item ->
                item.applyLineStyle { it.withDashLength(newDashLength) }
            }
        }
    }

    fun setDashSpace(newDashSpace: Int) {
        if (SelectedValue.Single(newDashSpace) != queryDashSpace()) {
            applyProperty<LineItem> { item ->
                item.applyLineStyle { it.withDashSpace(newDashSpace) }
            }
        }
    }

    fun setDashType(newDashType: DashType) {
        if (SelectedValue.Single(newDashType) != queryDashType()) {
            applyProperty<LineItem> { item ->
                item.applyLineStyle { it.withDashType(newDashType) }
            }
        }
    }

    fun setFillAngle1(newFillAngle: Int) {
        if (SelectedValue.Single(newFillAngle) != queryFillAngle1()) {
            applyProperty<FillItem> { item ->
                item.applyFillStyle { it.withFillAngle1(newFillAngle) }
            }
        }
    }

    fun setFillAngle2(newFillAngle: Int) {
        if (SelectedValue.Single(newFillAngle) != queryFillAngle2()) {
            applyProperty<FillItem> { item ->
                item.applyFillStyle { it.withFillAngle2(newFillAngle) }
            }
        }
    }

    fun setFillPitch1(newFillPitch: Int) {
        if (SelectedValue.Single(newFillPitch) != queryFillPitch1()) {
            applyProperty<FillItem> { item ->
                item.applyFillStyle { it.withFillPitch1(newFillPitch) }
            }
        }
    }

    fun setFillPitch2(newFillPitch: Int) {
        if (SelectedValue.Single(newFillPitch) != queryFillPitch2()) {
            applyProperty<FillItem> { item ->
                item.applyFillStyle { it.withFillPitch2(newFillPitch) }
            }
        }
    }

    fun setFillType(newFillType: FillType) {
        if (SelectedValue.Single(newFillType) != queryFillType()) {
            applyProperty<FillItem> { item ->
                item.applyFillStyle { it.withFillType(newFillType) }
            }
        }
    }

    fun setFillWidth(newFillWidth: Int) {
        if (SelectedValue.Single(newFillWidth) != queryFillWidth()) {
            applyProperty<FillItem> { item ->
                item.applyFillStyle { it.withFillWidth(newFillWidth) }
            }
        }
    }

    fun setItemColor(newItemColor: ColorIndex) {
        if (SelectedValue.Single(newItemColor) != queryItemColor()) {
            applyProperty<ColorItem> {
                it.withItemColor(newItemColor)
            }
        }
    }

    fun setLineWidth(newLineWidth: Int) {
        if (SelectedValue.Single(newLineWidth) != queryLineWidth()) {
            applyProperty<LineItem> { item ->
                item.applyLineStyle { it.withLineWidth(newLineWidth) }
            }
        }
    }

    fun setPinType(newPinType: PinType) {
        if (SelectedValue.Single(newPinType) != queryPinType()) {
            applyProperty<Pin> {
                it.withValues(newPinType = newPinType)
            }
        }
    }

    fun setTextAlignment(newAlignment: Alignment) {
        if (SelectedValue.Single(newAlignment) != queryTextAlignment()) {
            applyProperty<Text> {
                it.withAlignment(newAlignment)
            }
        }
    }

    fun setTextColor(newTextColor: ColorIndex) {
        if (SelectedValue.Single(newTextColor) != queryTextColor()) {
            applyProperty<Text> {
                it.withItemColor(newTextColor)
            }
        }
    }

    fun setTextRotation(newRotation: Int) {
        if (SelectedValue.Single(newRotation) != queryTextSize()) {
            applyProperty<Text> {
                it.withRotation(newRotation)
            }
        }
    }

    fun setTextSize(newSize: Int) {
        if (SelectedValue.Single(newSize) != queryTextSize()) {
            applyProperty<Text> {
                it.withSize(newSize)
            }
        }
    }

    fun calculateBounds(revealMode: RevealMode) = currentState.schematic.calculateBounds(revealMode)

    fun paint(drawer: Drawer, revealMode: RevealMode) = currentState.paint(drawer, revealMode)

    private fun deleteItems(predicate: (Item) -> Boolean) {
        val nextState = currentState.deleteItems(predicate)
        undoStack.add(currentState)
        currentState = nextState
        redoStack.clear()
    }

    private fun selectItems(predicate: (Item) -> Boolean) {
        val nextState = currentState.selectItems(predicate)
        undoStack.add(currentState)
        currentState = nextState
        redoStack.clear()
    }

    val canSave: Boolean get() = false

    fun save() {
    }

    val canRedo: Boolean get() = true

    fun redo() {
        if (redoStack.isNotEmpty()) {
            undoStack.add(0, currentState)
            currentState = redoStack.removeFirst()
        }
    }

    val canUndo: Boolean get() = undoStack.isNotEmpty()

    fun undo() {
        if (undoStack.isNotEmpty()) {
            redoStack.add(0, currentState)
            currentState = undoStack.removeFirst()
        }
    }

    fun addItems(items: List<Item>) {
        val nextState = currentState.addItems(items)
        undoStack.add(currentState)
        currentState = nextState
        redoStack.clear()
    }

    fun deleteSelectedItems() { deleteItems { currentState.isSelected(it) }
    }

    val canSelectAll: Boolean get() = true

    val canSelectNone: Boolean get() = true

    fun selectAll() = selectItems { true }

    fun selectNone() = selectItems { false }

    fun selectItems(bounds: Bounds) = selectItems { it.inside(bounds) }

    fun changeAttributeName(name: String, newValue: String) {
        println("Update: name=$name newValue=$newValue")
    }

    fun changeAttributeValue(name: String, newValue: String) {
        println("Update: name=$name newValue=$newValue")
    }

    companion object {

        fun read(reader: Reader): SchematicModel = SchematicModel(Schematic.read(reader))

        private fun <T> Iterable<T>.toQueryResult(): SelectedValue<T> = this
            .toSet()
            .let {
                when (it.count()) {
                    0 -> SelectedValue.None()
                    1 -> SelectedValue.Single(it.single())
                    else -> SelectedValue.Multiple()
                }
            }
    }
}
