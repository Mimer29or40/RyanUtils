package rutils.glfw.second.event;

import rutils.glfw.second.Monitor;
import rutils.glfw.second.Window;

@SuppressWarnings("unused")
public class GLFWEventWindowMonitorChanged extends GLFWEventWindow
{
    private final Monitor from;
    private final Monitor to;
    
    public GLFWEventWindowMonitorChanged(Window window, Monitor from, Monitor to)
    {
        super(window);
        
        this.from = from;
        this.to = to;
    }
    
    @Property
    public Monitor from()
    {
        return this.from;
    }
    
    @Property
    public Monitor to()
    {
        return this.to;
    }
}
