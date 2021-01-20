package rutils.glfw.second.event;

import rutils.glfw.second.Window;

public class EventWindowFocused extends EventWindow
{
    public final boolean focused;
    
    public EventWindowFocused(Window window, boolean focused)
    {
        super(window);
        
        this.focused = focused;
    }
}
