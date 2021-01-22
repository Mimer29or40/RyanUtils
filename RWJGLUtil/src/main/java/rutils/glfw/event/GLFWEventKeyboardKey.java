package rutils.glfw.event;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

@SuppressWarnings("unused")
public class GLFWEventKeyboardKey extends GLFWEventKeyboard
{
    private final Keyboard.Key key;
    
    public GLFWEventKeyboardKey(Window window, int mods, Keyboard.Key key)
    {
        super(window, mods);
        
        this.key = key;
    }
    
    @Property
    public Keyboard.Key key()
    {
        return this.key;
    }
}
