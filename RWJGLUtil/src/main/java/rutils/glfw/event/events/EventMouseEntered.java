package rutils.glfw.event.events;

import rutils.glfw.Window;
import rutils.glfw.event.EventProperty;

public interface EventMouseEntered extends EventMouse
{
    @EventProperty
    boolean entered();
    
    final class _EventMouseEntered extends AbstractEventInput implements EventMouseEntered
    {
        private final boolean entered;
        
        private _EventMouseEntered(Window window, boolean entered)
        {
            super(window);
            
            this.entered = entered;
        }
        
        @Override
        public boolean entered()
        {
            return this.entered;
        }
    }
    
    static EventMouseEntered create(Window window, boolean entered)
    {
        return new _EventMouseEntered(window, entered);
    }
}
