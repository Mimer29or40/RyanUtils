package rutils.glfw.event.events;

import rutils.glfw.Window;

public class EventWindow extends Event
{
    private final Window window;
    
    public EventWindow(Window window)
    {
        this.window = window;
    }
    
    @Property(printName = false)
    public Window window()
    {
        return this.window;
    }
}
