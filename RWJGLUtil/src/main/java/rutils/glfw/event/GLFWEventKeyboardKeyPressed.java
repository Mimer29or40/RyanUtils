package rutils.glfw.event;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

public class GLFWEventKeyboardKeyPressed extends GLFWEventKeyboardKey
{
    private final boolean doublePressed;
    
    public GLFWEventKeyboardKeyPressed(Window window, Keyboard.Key key, boolean doublePressed)
    {
        super(window, key);
    
        this.doublePressed = doublePressed;
    }
    
    @Property
    public boolean doublePressed()
    {
        return this.doublePressed;
    }
}
