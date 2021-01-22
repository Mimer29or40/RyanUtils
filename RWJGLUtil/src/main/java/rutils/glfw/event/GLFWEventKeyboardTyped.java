package rutils.glfw.event;

import rutils.glfw.Window;

public class GLFWEventKeyboardTyped extends GLFWEventKeyboard
{
    private final String typed;
    
    public GLFWEventKeyboardTyped(Window window, String typed)
    {
        super(window);
    
        this.typed = typed;
    }
    
    @Property
    public String typed()
    {
        return this.typed;
    }
}
