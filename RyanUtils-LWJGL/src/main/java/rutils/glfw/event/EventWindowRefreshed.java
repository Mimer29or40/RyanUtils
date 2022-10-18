package rutils.glfw.event;

import rutils.glfw.Window;

public interface EventWindowRefreshed extends EventWindow
{
    final class _EventWindowRefreshed extends AbstractEventWindow implements EventWindowRefreshed
    {
        private _EventWindowRefreshed(Window window)
        {
            super(window);
        }
    }
    
    static EventWindowRefreshed create(Window window)
    {
        return new _EventWindowRefreshed(window);
    }
}
