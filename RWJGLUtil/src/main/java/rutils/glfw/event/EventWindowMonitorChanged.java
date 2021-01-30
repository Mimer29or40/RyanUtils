package rutils.glfw.event;

import rutils.glfw.Monitor;
import rutils.glfw.Window;
import rutils.glfw.EventProperty;

public interface EventWindowMonitorChanged extends EventWindow
{
    @EventProperty
    Monitor from();
    
    @EventProperty
    Monitor to();
    
    final class _EventWindowMonitorChanged extends AbstractEventWindow implements EventWindowMonitorChanged
    {
        private final Monitor from;
        private final Monitor to;
    
        private _EventWindowMonitorChanged(Window window, Monitor from, Monitor to)
        {
            super(window);
        
            this.from = from;
            this.to = to;
        }
    
        @Override
        public Monitor from()
        {
            return this.from;
        }
    
        @Override
        public Monitor to()
        {
            return this.to;
        }
    }
    
    static EventWindowMonitorChanged create(Window window, Monitor from, Monitor to)
    {
        return new _EventWindowMonitorChanged(window, from, to);
    }
}
