package rutils.glfw.event.events;

import rutils.glfw.Window;
import rutils.glfw.event.EventProperty;

public abstract class AbstractEventInput extends AbstractEvent implements EventInput
{
    private final Window window;
    
    public AbstractEventInput(Window window)
    {
        this.window = window;
    }
    
    @Override
    public Window window()
    {
        return this.window;
    }
}
