package rutils.glfw.event.events;

import rutils.glfw.Window;
import rutils.glfw.event.EventProperty;

public interface EventKeyboardTyped extends EventKeyboard
{
    @EventProperty
    String typed();
    
    final class _EventKeyboardTyped extends AbstractEventInput implements EventKeyboardTyped
    {
        private final String typed;
    
        private _EventKeyboardTyped(Window window, String typed)
        {
            super(window);
        
            this.typed = typed;
        }
    
        @Override
        public String typed()
        {
            return this.typed;
        }
    }
    
    static EventKeyboardTyped create(Window window, String typed)
    {
        return new _EventKeyboardTyped(window, typed);
    }
}
