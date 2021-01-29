package rutils.glfw.event.events;

import rutils.glfw.Window;

public class EventInput extends Event
{
    private final Window window;
    
    public EventInput(Window window)
    {
        this.window = window;
    }
    
    @Property(printName = false)
    public Window window()
    {
        return this.window;
    }
}
