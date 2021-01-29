package rutils.glfw.event.events;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

public class EventKeyboardKeyUp extends EventKeyboardKey
{
    public EventKeyboardKeyUp(Window window, Keyboard.Key key)
    {
        super(window, key);
    }
}
