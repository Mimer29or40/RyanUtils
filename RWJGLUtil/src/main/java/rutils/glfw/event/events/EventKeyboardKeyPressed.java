package rutils.glfw.event.events;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;
import rutils.glfw.event.EventProperty;

public interface EventKeyboardKeyPressed extends EventKeyboardKey
{
    @EventProperty
    boolean doublePressed();
    
    final class _EventKeyboardKeyPressed extends AbstractEventKeyboardKey implements EventKeyboardKeyPressed
    {
        private final boolean doublePressed;
    
        private _EventKeyboardKeyPressed(Window window, Keyboard.Key key, boolean doublePressed)
        {
            super(window, key);
    
            this.doublePressed = doublePressed;
        }
    
        @Override
        public boolean doublePressed()
        {
            return this.doublePressed;
        }
    }
    
    static EventKeyboardKeyPressed create(Window window, Keyboard.Key key, boolean doublePressed)
    {
        return new _EventKeyboardKeyPressed(window, key, doublePressed);
    }
}
