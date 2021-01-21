package rutils.glfw.second.event;

import rutils.glfw.second.Window;

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
