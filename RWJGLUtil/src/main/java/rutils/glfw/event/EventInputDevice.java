package rutils.glfw.event;

import rutils.glfw.Window;
import rutils.glfw.EventProperty;

public interface EventInputDevice extends Event
{
    @EventProperty(printName = false)
    Window window();
}
