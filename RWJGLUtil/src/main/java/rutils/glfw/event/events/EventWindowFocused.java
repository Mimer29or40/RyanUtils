package rutils.glfw.event.events;

import rutils.glfw.Window;

public class EventWindowFocused extends EventWindow
{
    private final boolean focused;
    
    public EventWindowFocused(Window window, boolean focused)
    {
        super(window);
        
        this.focused = focused;
    }
    
    @Property
    public boolean focused()
    {
        return this.focused;
    }
}
