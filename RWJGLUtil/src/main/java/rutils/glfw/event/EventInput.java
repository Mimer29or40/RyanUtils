package rutils.glfw.event;

import rutils.glfw.Window;
import rutils.glfw.EventProperty;

public interface EventInput extends Event
{
    @EventProperty(printName = false)
    Window window();
    
    final class _EventInput extends AbstractEventInput implements EventInput
    {
        private _EventInput(Window window)
        {
            super(window);
        }
    }
    
    static EventInput create(Window window)
    {
        return new _EventInput(window);
    }
}
