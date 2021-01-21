package rutils.glfw.second.event;

import rutils.glfw.second.Monitor;
import rutils.glfw.second.Window;

@SuppressWarnings("unused")
public class GLFWEventWindowMonitorChanged extends GLFWEventWindow
{
    private final Monitor monitor;
    
    public GLFWEventWindowMonitorChanged(Window window, Monitor monitor)
    {
        super(window);
        
        this.monitor = monitor;
    }
    
    @Property(printName = false)
    public Monitor monitor()
    {
        return this.monitor;
    }
}
