package rutils.glfw.event;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;
import rutils.glfw.EventProperty;

public interface EventKeyboardKey extends EventKeyboard
{
    @EventProperty(printName = false)
    Keyboard.Key key();
    
    final class _EventKeyboardKey extends AbstractEventKeyboardKey implements EventKeyboardKey
    {
        private _EventKeyboardKey(Window window, Keyboard.Key key)
        {
            super(window, key);
        }
    }
    
    static EventKeyboardKey create(Window window, Keyboard.Key key)
    {
        return new _EventKeyboardKey(window, key);
    }
}
