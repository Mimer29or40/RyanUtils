package rutils.glfw.event;

import rutils.glfw.Window;
import rutils.glfw.EventProperty;

public interface EventWindow extends Event
{
    @EventProperty(printName = false)
    Window window();
    
    final class _EventWindow extends AbstractEventWindow implements EventWindow
    {
        private _EventWindow(Window window)
        {
            super(window);
        }
    }
    
    static EventWindow create(Window window)
    {
        return new _EventWindow(window);
    }
}
