package rutils.glfw.event.events;

import rutils.glfw.Window;

public interface EventWindowClosed extends EventWindow
{
    final class _EventWindowClosed extends AbstractEventWindow implements EventWindowClosed
    {
        private _EventWindowClosed(Window window)
        {
            super(window);
        }
    }
    
    static EventWindowClosed create(Window window)
    {
        return new _EventWindowClosed(window);
    }
}
