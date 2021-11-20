package rutils.glfw.event;

import rutils.glfw.Window;

abstract class AbstractEventInputDevice extends AbstractEvent implements EventInputDevice
{
    private final Window window;
    
    AbstractEventInputDevice(Window window)
    {
        this.window = window;
    }
    
    @Override
    public Window window()
    {
        return this.window;
    }
}
