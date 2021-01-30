package rutils.glfw.event.events;

import rutils.glfw.Monitor;
import rutils.glfw.Monitor;
import rutils.glfw.event.EventProperty;

public interface EventMonitor extends Event
{
    @EventProperty(printName = false)
    Monitor monitor();
    
    final class _EventMonitor extends AbstractEventMonitor implements EventMonitor
    {
        private _EventMonitor(Monitor monitor)
        {
            super(monitor);
        }
    }
    
    static EventMonitor create(Monitor monitor)
    {
        return new _EventMonitor(monitor);
    }
}
