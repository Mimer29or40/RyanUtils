package rutils.glfw.event;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

@SuppressWarnings("unused")
public class GLFWEventKeyboardKeyUp extends GLFWEventKeyboardKey
{
    public GLFWEventKeyboardKeyUp(Window window, int mods, Keyboard.Key key)
    {
        super(window, mods, key);
    }
}
