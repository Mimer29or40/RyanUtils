package rutils.glfw.event;

import rutils.glfw.Window;

@SuppressWarnings("unused")
public class GLFWEventWindowFocused extends GLFWEventWindow
{
    private final boolean focused;
    
    public GLFWEventWindowFocused(Window window, boolean focused)
    {
        super(window);
        
        this.focused = focused;
    }
    
    @Property
    public boolean focused()
    {
        return this.focused;
    }
}