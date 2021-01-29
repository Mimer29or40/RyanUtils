package rutils.glfw.event.events;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

public class GLFWEventKeyboardKeyRepeated extends GLFWEventKeyboardKey
{
    public GLFWEventKeyboardKeyRepeated(Window window, Keyboard.Key key)
    {
        super(window, key);
    }
}
