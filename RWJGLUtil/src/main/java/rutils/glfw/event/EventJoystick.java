package rutils.glfw.event;

import rutils.glfw.EventProperty;
import rutils.glfw.Joystick;

public interface EventJoystick extends EventInputDevice
{
    @EventProperty(printName = false)
    Joystick joystick();
}
