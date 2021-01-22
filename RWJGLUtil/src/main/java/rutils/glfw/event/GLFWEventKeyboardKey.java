package rutils.glfw.event;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

public class GLFWEventKeyboardKey extends GLFWEventKeyboard
{
    private final Keyboard.Input key;
    
    public GLFWEventKeyboardKey(Window window, Keyboard.Input key)
    {
        super(window);
        
        this.key = key;
    }
    
    @Property(printName = false)
    public Keyboard.Input key()
    {
        return this.key;
    }
}
