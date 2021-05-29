package tools.pin

import models.schematic.shapes.circle.Circle
import models.schematic.shapes.pin.Pin
import models.schematic.types.ColorIndex
import types.Drawer
import tools.ToolTarget
import types.RevealMode

class BasicPinItemGroup(
    private val prototypePin: Pin = Pin(),
    private val prototypeBubble: Circle = Circle(
        radius = 100,
        color = ColorIndex.LOGIC_BUBBLE
    )
): PinItemGroup {
    override val x0: Int get() = prototypePin.x0
    override val y0: Int get() = prototypePin.y0

    override fun withFirstPoint(newX: Int, newY: Int): PinItemGroup = BasicPinItemGroup(
        prototypePin.withPoint0(newX, newY),
        prototypeBubble
    )

    override fun withSecondPoint(newX: Int, newY: Int): PinItemGroup = BasicPinItemGroup(
        prototypePin.withPoint1(newX, newY),
        prototypeBubble.withCenter(newX, newY)
    )

    override fun draw(drawer: Drawer) {
        prototypePin.paint(drawer, RevealMode.SHOWN, false)
        prototypeBubble.paint(drawer, RevealMode.SHOWN, false)
    }

    override fun repaint(target: ToolTarget) {
        target.repaint(prototypePin)
        target.repaint(prototypeBubble)
    }
}