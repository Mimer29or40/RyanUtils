package rutils.glfw.event;

import rutils.glfw.Window;

@SuppressWarnings("unused")
public class GLFWEventKeyboardTyped extends GLFWEventKeyboard
{
    private final String typed;
    
    public GLFWEventKeyboardTyped(Window window, int mods, String typed)
    {
        super(window, mods);
    
        this.typed = typed;
    }
    
    @Property
    public String typed()
    {
        return this.typed;
    }
}
