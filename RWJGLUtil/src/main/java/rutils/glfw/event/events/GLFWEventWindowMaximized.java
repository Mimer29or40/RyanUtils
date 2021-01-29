package rutils.glfw.event.events;

import rutils.glfw.Window;

public class GLFWEventWindowMaximized extends GLFWEventWindow
{
    private final boolean maximized;
    
    public GLFWEventWindowMaximized(Window window, boolean maximized)
    {
        super(window);
    
        this.maximized = maximized;
    }
    
    @Property
    public boolean maximized()
    {
        return this.maximized;
    }
}
