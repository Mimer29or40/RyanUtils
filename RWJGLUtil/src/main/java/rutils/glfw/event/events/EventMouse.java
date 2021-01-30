package rutils.glfw.event.events;

import rutils.glfw.Window;

public interface EventMouse extends EventInput
{
    final class _EventMouse extends AbstractEventInput implements EventMouse
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
