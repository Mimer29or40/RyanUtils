package rutils.glfw.event;

import rutils.glfw.Window;
import rutils.glfw.EventProperty;

public interface EventWindowFocused extends EventWindow
{
    @EventProperty
    boolean focused();
    
    final class _EventWindowFocused extends AbstractEventWindow implements EventWindowFocused
    {
        private final boolean focused;
    
        private _EventWindowFocused(Window window, boolean focused)
        {
            super(window);
        
            this.focused = focused;
        }
    
        @Override
        public boolean focused()
        {
            return this.focused;
        }
    }
    
    static EventWindowFocused create(Window window, boolean focused)
    {
        return new _EventWindowFocused(window, focused);
    }
}
