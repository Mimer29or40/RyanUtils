package rutils.glfw.event;

import rutils.glfw.Window;
import rutils.glfw.EventProperty;

public interface EventWindowIconified extends EventWindow
{
    @EventProperty
    boolean iconified();
    
    final class _EventWindowIconified extends AbstractEventWindow implements EventWindowIconified
    {
        private final boolean iconified;
        
        private _EventWindowIconified(Window window, boolean iconified)
        {
            super(window);
    
            this.iconified = iconified;
        }
        
        @Override
        public boolean iconified()
        {
            return this.iconified;
        }
    }
    
    static EventWindowIconified create(Window window, boolean iconified)
    {
        return new _EventWindowIconified(window, iconified);
    }
}
