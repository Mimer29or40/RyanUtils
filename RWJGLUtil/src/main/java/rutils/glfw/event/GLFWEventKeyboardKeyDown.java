package rutils.glfw.event;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

@SuppressWarnings("unused")
public class GLFWEventKeyboardKeyDown extends GLFWEventKeyboardKey
{
    public GLFWEventKeyboardKeyDown(Window window, int mods, Keyboard.Key key)
    {
        super(window, mods, key);
    }
}
