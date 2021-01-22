package rutils.glfw.event;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

public class GLFWEventKeyboardKeyHeld extends GLFWEventKeyboardKey
{
    public GLFWEventKeyboardKeyHeld(Window window, Keyboard.Input key)
    {
        super(window, key);
    }
}
