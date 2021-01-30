package rutils.glfw.event.events;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

public interface EventKeyboardKeyRepeated extends EventKeyboardKey
{
    final class _EventKeyboardKeyRepeated extends AbstractEventKeyboardKey implements EventKeyboardKeyRepeated
    {
        private _EventKeyboardKeyRepeated(Window window, Keyboard.Key key)
        {
            super(window, key);
        }
    }
    
    static EventKeyboardKeyRepeated create(Window window, Keyboard.Key key)
    {
        return new _EventKeyboardKeyRepeated(window, key);
    }
}
