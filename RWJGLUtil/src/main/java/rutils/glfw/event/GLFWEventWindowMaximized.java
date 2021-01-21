package rutils.glfw.event;

import rutils.glfw.Window;

@SuppressWarnings("unused")
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
