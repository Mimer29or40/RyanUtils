package rutils.glfw.event.events;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

public class EventKeyboardKeyDown extends EventKeyboardKey
{
    public EventKeyboardKeyDown(Window window, Keyboard.Key key)
    {
        super(window, key);
    }
}
