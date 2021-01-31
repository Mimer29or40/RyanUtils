package rutils.glfw.event;

import rutils.glfw.Gamepad;
import rutils.glfw.EventProperty;

public interface EventGamepadButton extends EventInputDeviceInput, EventJoystickButton, EventGamepad
{
    Gamepad gamepad();
    
    @EventProperty
    Gamepad.Button gamepadButton();
}
