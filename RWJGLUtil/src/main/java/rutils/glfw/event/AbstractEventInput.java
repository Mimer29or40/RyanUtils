package rutils.glfw.event;

import rutils.glfw.Window;

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
