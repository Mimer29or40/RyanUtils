package rutils.glfw.event;

import rutils.glfw.EventProperty;
import rutils.glfw.Window;

public interface EventWindowVsyncChanged extends EventWindow
{
    @EventProperty
    boolean vsync();
    
    final class _EventWindowVsyncChanged extends AbstractEventWindow implements EventWindowVsyncChanged
    {
        private final boolean vsync;
        
        private _EventWindowVsyncChanged(Window window, boolean vsync)
        {
            super(window);
            
            this.vsync = vsync;
        }
        
        @Override
        public boolean vsync()
        {
            return this.vsync;
        }
    }
    
    static EventWindowVsyncChanged create(Window window, boolean maximized)
    {
        return new _EventWindowVsyncChanged(window, maximized);
    }
}
