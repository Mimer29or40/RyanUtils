package rutils.glfw.event;

import rutils.glfw.Window;

abstract class AbstractEventWindow extends AbstractEvent implements EventWindow
{
    private final Window window;
    
    AbstractEventWindow(Window window)
    {
        super();
        
        this.window = window;
    }
    
    @Override
    public Window window()
    {
        return this.window;
    }
}
