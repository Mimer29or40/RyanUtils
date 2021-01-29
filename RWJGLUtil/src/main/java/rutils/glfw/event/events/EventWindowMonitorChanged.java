package rutils.glfw.event.events;

import rutils.glfw.Monitor;
import rutils.glfw.Window;

public class EventWindowMonitorChanged extends EventWindow
{
    private final Monitor from;
    private final Monitor to;
    
    public EventWindowMonitorChanged(Window window, Monitor from, Monitor to)
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
