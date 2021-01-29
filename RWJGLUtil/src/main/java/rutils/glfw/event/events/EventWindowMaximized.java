package rutils.glfw.event.events;

import rutils.glfw.Window;

public class EventWindowMaximized extends EventWindow
{
    private final boolean maximized;
    
    public EventWindowMaximized(Window window, boolean maximized)
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
