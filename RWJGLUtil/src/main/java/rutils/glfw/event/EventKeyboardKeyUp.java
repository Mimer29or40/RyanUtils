package rutils.glfw.event;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

public interface EventKeyboardKeyUp extends EventKeyboardKey
{
    final class _EventKeyboardKeyUp extends AbstractEventKeyboardKey implements EventKeyboardKeyUp
    {
        private _EventKeyboardKeyUp(Window window, Keyboard.Key key)
        {
            super(window, key);
        }
    }
    
    static EventKeyboardKeyUp create(Window window, Keyboard.Key key)
    {
        return new _EventKeyboardKeyUp(window, key);
    }
}
