package rutils.glfw.event.events;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

public class GLFWEventKeyboardKey extends GLFWEventKeyboard
{
    private final Keyboard.Key key;
    
    public GLFWEventKeyboardKey(Window window, Keyboard.Key key)
    {
        super(window);
        
        this.key = key;
    }
    
    @Property(printName = false)
    public Keyboard.Key key()
    {
        return this.key;
    }
}
