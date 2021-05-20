package models.schematic

import models.schematic.listeners.InvalidateListener
import models.schematic.listeners.PropertyListener
import models.schematic.shapes.pin.Pin
import models.schematic.shapes.pin.PinType
import models.schematic.shapes.text.Text
import models.schematic.types.*

class SchematicModel {

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

    fun getItemColor() = queryProperty<ColorItem, Int> { it.color }

    fun getLineWidth() = queryProperty<LineItem, Int> { it.lineStyle.lineWidth }

    fun getPinType() = queryProperty<Pin, PinType> { it.pinType }

    fun getTextColor() = queryProperty<Text, Int> { it.color }


    private fun <T> applyProperty(apply: (T) -> Item) {
        firePropertyListener()
    }

    fun setCapType(newCapType: CapType) = applyProperty<LineItem> { item ->
        item.applyLineStyle { it.withCapType(newCapType) }
    }

    fun setDashLength(newDashLength: Int) = applyProperty<LineItem> { item ->
        item.applyLineStyle { it.withDashLength(newDashLength) }
    }

    fun setDashSpace(newDashSpace: Int) = applyProperty<LineItem> { item ->
        item.applyLineStyle { it.withDashSpace(newDashSpace) }
    }

    fun setDashType(newDashType: DashType) = applyProperty<LineItem> { item ->
        item.applyLineStyle { it.withDashType(newDashType) }
    }

    fun setFillAngle1(newFillAngle: Int) = applyProperty<FillItem> { item ->
        item.applyFillStyle { it.withFillAngle1(newFillAngle) }
    }

    fun setFillAngle2(newFillAngle: Int) = applyProperty<FillItem> { item ->
        item.applyFillStyle { it.withFillAngle2(newFillAngle) }
    }

    fun setFillPitch1(newFillPitch: Int) = applyProperty<FillItem> { item ->
        item.applyFillStyle { it.withFillPitch1(newFillPitch) }
    }

    fun setFillPitch2(newFillPitch: Int) = applyProperty<FillItem> { item ->
        item.applyFillStyle { it.withFillPitch2(newFillPitch) }
    }

    fun setFillType(newFillType: FillType) = applyProperty<FillItem> { item ->
        item.applyFillStyle { it.withFillType(newFillType) }
    }

    fun setFillWidth(newFillWidth: Int) = applyProperty<FillItem> { item ->
        item.applyFillStyle { it.withFillWidth(newFillWidth) }
    }

    fun setItemColor(newItemColor: Int) = applyProperty<ColorItem> {
        it.withItemColor(newItemColor)
    }

    fun setLineWidth(newLineWidth: Int) = applyProperty<LineItem> { item ->
        item.applyLineStyle { it.withLineWidth(newLineWidth) }
    }

    fun setPinType(newPinType: PinType) = applyProperty<Pin> {
        it.withPinType(newPinType)
    }

    fun setTextColor(newTextColor: Int) = applyProperty<Text> {
        it.withItemColor(newTextColor)
    }
}
