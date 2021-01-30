package rutils.glfw.event;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

public interface EventKeyboardKeyHeld extends EventKeyboardKey
{
    final class _EventKeyboardKeyHeld extends AbstractEventKeyboardKey implements EventKeyboardKeyHeld
    {
        private _EventKeyboardKeyHeld(Window window, Keyboard.Key key)
        {
            super(window, key);
        }
    }
    
    static EventKeyboardKeyHeld create(Window window, Keyboard.Key key)
    {
        return new _EventKeyboardKeyHeld(window, key);
    }
}
