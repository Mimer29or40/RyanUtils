package rutils.glfw.event.events;

import rutils.glfw.Window;

public class EventWindowIconified extends EventWindow
{
    private final boolean iconified;
    
    public EventWindowIconified(Window window, boolean iconified)
    {
        super(window);
    
        this.iconified = iconified;
    }
    
    @Property
    public boolean iconified()
    {
        return this.iconified;
    }
}
