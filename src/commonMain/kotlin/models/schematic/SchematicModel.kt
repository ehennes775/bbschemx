package models.schematic

import State
import models.schematic.io.Reader
import models.schematic.listeners.InvalidateListener
import models.schematic.listeners.PropertyListener
import models.schematic.listeners.SelectionListener
import models.schematic.shapes.pin.Pin
import models.schematic.shapes.pin.PinType
import models.schematic.shapes.text.Text
import models.schematic.types.*
import types.Drawer
import types.RevealMode

class SchematicModel(private var schematic: Schematic) {

    private val invalidateListeners = mutableListOf<InvalidateListener>()

    fun addInvalidateListener(listener: InvalidateListener) {
        invalidateListeners.add(listener)
    }

    fun removeInvalidateListener(listener: InvalidateListener) {
        invalidateListeners.remove(listener)
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




    private fun <T,U> queryProperty(query: (T) -> U): SelectedValue<U> {
        return setOf<U>().let {
            when (it.count()) {
                0 -> SelectedValue.None()
                1 -> SelectedValue.Single(it.single())
                else -> SelectedValue.Multiple()
            }
        }
    }

    fun getCapType() = queryProperty<LineItem, CapType> { it.lineStyle.capType }

    fun getDashLength() = queryProperty<LineItem, Int> { it.lineStyle.dashLength }

    fun getDashSpace() = queryProperty<LineItem, Int> { it.lineStyle.dashSpace }

    fun getDashType() = queryProperty<LineItem, DashType> { it.lineStyle.dashType }

    fun getFillAngle1() = queryProperty<FillItem, Int> { it.fillStyle.fillAngle1 }

    fun getFillAngle2() = queryProperty<FillItem, Int> { it.fillStyle.fillAngle2 }

    fun getFillPitch1() = queryProperty<FillItem, Int> { it.fillStyle.fillPitch1 }

    fun getFillPitch2() = queryProperty<FillItem, Int> { it.fillStyle.fillPitch2 }

    fun getFillType() = queryProperty<FillItem, FillType> { it.fillStyle.fillType }

    fun getFillWidth() = queryProperty<FillItem, Int> { it.fillStyle.fillWidth }

    fun getItemColor() = queryProperty<ColorItem, ColorIndex> { it.color }

    fun getLineWidth() = queryProperty<LineItem, Int> { it.lineStyle.lineWidth }

    fun getPinType() = queryProperty<Pin, PinType> { it.pinType }

    fun getTextColor() = queryProperty<Text, ColorIndex> { it.color }


    private inline fun <reified T> applyProperty(crossinline apply: (T) -> Item) {
        schematic = schematic.map { if (it is T) apply(it) else it }
        firePropertyListener()
        fireSelectionChanged()
    }

    fun setCapType(newCapType: CapType) = applyProperty<LineItem> { item ->
        item.applyLineStyle { it.withCapType(newCapType) }
    }

    fun setDashLength(newDashLength: Int) = applyProperty<LineItem> { item ->
        item.applyLineStyle { it.withDashLength(newDashLength) }
    }

    fun setDashLength(newDashLength: String) = setDashLength(newDashLength.toInt())

    fun setDashSpace(newDashSpace: Int) = applyProperty<LineItem> { item ->
        item.applyLineStyle { it.withDashSpace(newDashSpace) }
    }

    fun setDashSpace(newDashSpace: String) = setDashSpace(newDashSpace.toInt())

    fun setDashType(newDashType: DashType) = applyProperty<LineItem> { item ->
        item.applyLineStyle { it.withDashType(newDashType) }
    }

    fun setFillAngle1(newFillAngle: Int) = applyProperty<FillItem> { item ->
        item.applyFillStyle { it.withFillAngle1(newFillAngle) }
    }

    fun setFillAngle1(newFillAngle: String) = setFillAngle1(newFillAngle.toInt())

    fun setFillAngle2(newFillAngle: Int) = applyProperty<FillItem> { item ->
        item.applyFillStyle { it.withFillAngle2(newFillAngle) }
    }

    fun setFillAngle2(newFillAngle: String) = setFillAngle2(newFillAngle.toInt())

    fun setFillPitch1(newFillPitch: Int) = applyProperty<FillItem> { item ->
        item.applyFillStyle { it.withFillPitch1(newFillPitch) }
    }

    fun setFillPitch1(newFillPitch: String) = setFillPitch1(newFillPitch.toInt())

    fun setFillPitch2(newFillPitch: Int) = applyProperty<FillItem> { item ->
        item.applyFillStyle { it.withFillPitch2(newFillPitch) }
    }

    fun setFillPitch2(newFillPitch: String) = setFillPitch2(newFillPitch.toInt())

    fun setFillType(newFillType: FillType) = applyProperty<FillItem> { item ->
        item.applyFillStyle { it.withFillType(newFillType) }
    }

    fun setFillWidth(newFillWidth: Int) = applyProperty<FillItem> { item ->
        item.applyFillStyle { it.withFillWidth(newFillWidth) }
    }

    fun setItemColor(newItemColor: ColorIndex) = applyProperty<ColorItem> {
        it.withItemColor(newItemColor)
    }

    fun setLineWidth(newLineWidth: Int) = applyProperty<LineItem> { item ->
        item.applyLineStyle { it.withLineWidth(newLineWidth) }
    }

    fun setPinType(newPinType: PinType) = applyProperty<Pin> {
        it.withPinType(newPinType)
    }

    fun setTextColor(newTextColor: ColorIndex) = applyProperty<Text> {
        it.withItemColor(newTextColor)
    }

    fun add(items: List<Item>) {
        schematic = Schematic(
            schematic.version,
            schematic.items + items
        )
    }


    fun calculateBounds() = schematic.calculateBounds()

    fun paint(drawer: Drawer, revealMode: RevealMode) {
        schematic.paint(drawer, revealMode)
    }


    private var currentState: State = State(Schematic(), setOf())
    private val redoStack = mutableListOf<State>()
    private val undoStack = mutableListOf<State>()

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

    fun selectAll() { selectItems { true } }

    fun selectNone() { selectItems { false } }


    companion object {

        fun read(reader: Reader): SchematicModel = SchematicModel(Schematic.read(reader))

    }
}
