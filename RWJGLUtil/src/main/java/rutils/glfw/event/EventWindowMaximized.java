package rutils.glfw.event;

import rutils.glfw.Window;
import rutils.glfw.EventProperty;

public interface EventWindowMaximized extends EventWindow
{
    @EventProperty
    boolean maximized();
    
    final class _EventWindowMaximized extends AbstractEventWindow implements EventWindowMaximized
    {
        private final boolean maximized;
        
        private _EventWindowMaximized(Window window, boolean maximized)
        {
            super(window);
            
            this.maximized = maximized;
        }
        
        @Override
        public boolean maximized()
        {
            return this.maximized;
        }
    }
    
    static EventWindowMaximized create(Window window, boolean maximized)
    {
        return new _EventWindowMaximized(window, maximized);
    }
}
