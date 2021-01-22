package rutils.glfw.event;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

public class GLFWEventKeyboardKeyUp extends GLFWEventKeyboardKey
{
    public GLFWEventKeyboardKeyUp(Window window, Keyboard.Input key)
    {
        super(window, key);
    }
}
