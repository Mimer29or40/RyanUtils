package rutils.glfw.event;

import rutils.glfw.EventProperty;

public interface EventJoystickButton extends EventInputDeviceInput, EventJoystick
{
    @EventProperty
    int button();
}
