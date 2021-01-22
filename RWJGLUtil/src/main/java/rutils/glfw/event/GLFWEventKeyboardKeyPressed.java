package rutils.glfw.event;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

@SuppressWarnings("unused")
public class GLFWEventKeyboardKeyPressed extends GLFWEventKeyboardKey
{
    private final boolean doublePressed;
    
    public GLFWEventKeyboardKeyPressed(Window window, int mods, Keyboard.Key key, boolean doublePressed)
    {
        super(window, mods, key);
    
        this.doublePressed = doublePressed;
    }
    
    @Property
    public boolean doublePressed()
    {
        return this.doublePressed;
    }
}
