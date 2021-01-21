package rutils.glfw.second.event;

import rutils.glfw.GLFWWindow;
import rutils.glfw.second.Window;

public class GLFWEventWindowVsync extends GLFWEventWindow
{
    private final boolean vsync;
    
    public GLFWEventWindowVsync(Window window, boolean vsync)
    {
        super(window);
        
        this.vsync = vsync;
    }
    
    @Property
    public boolean vsync()
    {
        return this.vsync;
    }
}
