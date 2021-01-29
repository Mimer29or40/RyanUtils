package rutils.glfw.event.events;

import rutils.glfw.Monitor;

public class EventMonitor extends Event
{
    private final Monitor monitor;
    
    public EventMonitor(Monitor monitor)
    {
        this.monitor = monitor;
    }
    
    @Property(printName = false)
    public Monitor monitor()
    {
        return this.monitor;
    }
}
