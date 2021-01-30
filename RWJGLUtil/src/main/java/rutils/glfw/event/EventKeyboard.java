package rutils.glfw.event;

import rutils.glfw.Window;

public interface EventKeyboard extends EventInput
{
    final class _EventKeyboard extends AbstractEventInput implements EventKeyboard
    {
        private _EventKeyboard(Window window)
        {
            super(window);
        }
    }
    
    static EventKeyboard create(Window window)
    {
        return new _EventKeyboard(window);
    }
}
