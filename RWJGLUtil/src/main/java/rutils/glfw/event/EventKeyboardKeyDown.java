package rutils.glfw.event;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

public interface EventKeyboardKeyDown extends EventKeyboardKey
{
    final class _EventKeyboardKeyDown extends AbstractEventKeyboardKey implements EventKeyboardKeyDown
    {
        private _EventKeyboardKeyDown(Window window, Keyboard.Key key)
        {
            super(window, key);
        }
    }
    
    static EventKeyboardKeyDown create(Window window, Keyboard.Key key)
    {
        return new _EventKeyboardKeyDown(window, key);
    }
}
