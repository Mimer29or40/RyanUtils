package rutils.glfw.event.events;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

public class GLFWEventKeyboardKeyHeld extends GLFWEventKeyboardKey
{
    public GLFWEventKeyboardKeyHeld(Window window, Keyboard.Key key)
    {
        super(window, key);
    }
}
