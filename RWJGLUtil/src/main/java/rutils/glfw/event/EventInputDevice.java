package rutils.glfw.event;

import rutils.glfw.Window;
import rutils.glfw.EventProperty;

public interface EventInputDevice extends Event
{
    @EventProperty(printName = false)
    Window window();
    
    final class _EventInput extends AbstractEventInputDevice implements EventInputDevice
    {
        private _EventInput(Window window)
        {
            super(window);
        }
    }
    
    static EventInputDevice create(Window window)
    {
        return new _EventInput(window);
    }
}
