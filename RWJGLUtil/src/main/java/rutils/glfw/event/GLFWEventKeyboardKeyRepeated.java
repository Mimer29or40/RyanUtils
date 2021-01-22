package rutils.glfw.event;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

@SuppressWarnings("unused")
public class GLFWEventKeyboardKeyRepeated extends GLFWEventKeyboardKey
{
    public GLFWEventKeyboardKeyRepeated(Window window, int mods, Keyboard.Key key)
    {
        super(window, mods, key);
    }
}
