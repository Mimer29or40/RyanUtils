package rutils.glfw.event;

import rutils.glfw.EventProperty;
import rutils.glfw.Window;

public interface EventInputDevice extends Event
{
    @EventProperty(printName = false)
    Window window();
}
