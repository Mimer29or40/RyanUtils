package rutils.glfw.event.events;

import rutils.glfw.Window;

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
