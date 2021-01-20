package rutils.glfw.second.event;

import rutils.glfw.second.Window;

public class EventWindow extends Event
{
    public final Window window;
    
    public EventWindow(Window window)
    {
        this.window = window;
    }
}
