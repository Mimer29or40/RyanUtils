package rutils.glfw.event.events;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

public class GLFWEventKeyboardKeyUp extends GLFWEventKeyboardKey
{
    public GLFWEventKeyboardKeyUp(Window window, Keyboard.Key key)
    {
        super(window, key);
    }
}