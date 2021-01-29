package rutils.glfw.event.events;

import rutils.glfw.Window;

public class EventWindowVsyncChanged extends EventWindow
{
    private final boolean vsync;
    
    public EventWindowVsyncChanged(Window window, boolean vsync)
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
