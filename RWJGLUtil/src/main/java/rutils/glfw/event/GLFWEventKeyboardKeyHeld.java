package rutils.glfw.event;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

@SuppressWarnings("unused")
public class GLFWEventKeyboardKeyHeld extends GLFWEventKeyboardKey
{
    public GLFWEventKeyboardKeyHeld(Window window, int mods, Keyboard.Key key)
    {
        super(window, mods, key);
    }
}
