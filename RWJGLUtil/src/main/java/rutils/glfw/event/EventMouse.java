package rutils.glfw.event;

import rutils.glfw.Window;

public interface EventMouse extends EventInputDevice
{
    final class _EventMouse extends AbstractEventInputDevice implements EventMouse
    {
        private _EventMouse(Window window)
        {
            super(window);
        }
    }
    
    static EventMouse create(Window window)
    {
        return new _EventMouse(window);
    }
}
