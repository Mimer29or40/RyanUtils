package rutils.glfw.event.events;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

public class EventKeyboardKeyHeld extends EventKeyboardKey
{
    public EventKeyboardKeyHeld(Window window, Keyboard.Key key)
    {
        super(window, key);
    }
}
