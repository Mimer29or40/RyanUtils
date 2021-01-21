package rutils.glfw.event;

import rutils.glfw.Monitor;

public class GLFWEventMonitor extends GLFWEvent
{
    private final Monitor monitor;
    
    public GLFWEventMonitor(Monitor monitor)
    {
        this.monitor = monitor;
    }
    
    @Property(printName = false)
    public Monitor monitor()
    {
        return this.monitor;
    }
}
