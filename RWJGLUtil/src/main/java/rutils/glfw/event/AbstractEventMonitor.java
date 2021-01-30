package rutils.glfw.event;

import rutils.glfw.Monitor;

public abstract class AbstractEventMonitor extends AbstractEvent implements EventMonitor
{
    private final Monitor monitor;
    
    AbstractEventMonitor(Monitor monitor)
    {
        super();
    
        this.monitor = monitor;
    }
    
    @Override
    public Monitor monitor()
    {
        return this.monitor;
    }
}
