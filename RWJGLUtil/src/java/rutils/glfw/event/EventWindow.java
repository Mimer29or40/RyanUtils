package rutils.glfw.event;

import rutils.glfw.EventProperty;
import rutils.glfw.Window;

public interface EventWindow extends Event
{
    @EventProperty(printName = false)
    Window window();
}
