package rutils.glfw.event;

import rutils.glfw.EventProperty;
import rutils.glfw.Gamepad;

public interface EventGamepadButton extends EventInputDeviceInput, EventJoystickButton, EventGamepad
{
    Gamepad gamepad();
    
    @EventProperty
    Gamepad.Button gamepadButton();
}
