package rutils.glfw.second.event;

import rutils.glfw.second.Window;

@SuppressWarnings("unused")
public class GLFWEventWindowVsyncChanged extends GLFWEventWindow
{
    private final boolean vsync;
    
    public GLFWEventWindowVsyncChanged(Window window, boolean vsync)
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
