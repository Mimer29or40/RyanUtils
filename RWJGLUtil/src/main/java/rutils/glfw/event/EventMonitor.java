package rutils.glfw.event;

import rutils.glfw.EventProperty;
import rutils.glfw.Monitor;

public interface EventMonitor extends Event
{
    @EventProperty(printName = false)
    Monitor monitor();
}
