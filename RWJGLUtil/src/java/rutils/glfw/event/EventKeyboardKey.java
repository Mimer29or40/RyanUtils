package rutils.glfw.event;

import rutils.glfw.EventProperty;
import rutils.glfw.Keyboard;

public interface EventKeyboardKey extends EventInputDeviceInput, EventKeyboard
{
    @EventProperty(printName = false)
    Keyboard.Key key();
}
