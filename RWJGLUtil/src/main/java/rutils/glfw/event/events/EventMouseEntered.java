package rutils.glfw.event.events;

import rutils.glfw.Window;

public class EventMouseEntered extends EventMouse
{
    private final boolean entered;
    
    public EventMouseEntered(Window window, boolean entered)
    {
        super(window);
        
        this.entered = entered;
    }
    
    @Property
    public boolean entered()
    {
        return this.entered;
    }
}
