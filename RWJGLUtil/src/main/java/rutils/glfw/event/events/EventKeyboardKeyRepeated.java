package rutils.glfw.event.events;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

public class EventKeyboardKeyRepeated extends EventKeyboardKey
{
    public EventKeyboardKeyRepeated(Window window, Keyboard.Key key)
    {
        super(window, key);
    }
}
