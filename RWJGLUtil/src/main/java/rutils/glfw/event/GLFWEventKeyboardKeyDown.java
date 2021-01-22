package rutils.glfw.event;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

public class GLFWEventKeyboardKeyDown extends GLFWEventKeyboardKey
{
    public GLFWEventKeyboardKeyDown(Window window, Keyboard.Input key)
    {
        super(window, key);
    }
}
