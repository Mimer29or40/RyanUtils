package rutils.glfw.event;

import rutils.glfw.Gamepad;
import rutils.glfw.EventProperty;

public interface EventGamepadButton extends EventJoystickButton, EventGamepad
{
    Gamepad gamepad();
    
    @EventProperty
    Gamepad.Button gamepadButton();
    
    final class _EventGamepadButton extends AbstractEventGamepadButton implements EventGamepadButton
    {
        private _EventGamepadButton(Gamepad gamepad, Gamepad.Button button)
        {
            super(gamepad, button);
        }
    }
    
    static EventGamepadButton create(Gamepad gamepad, Gamepad.Button button)
    {
        return new _EventGamepadButton(gamepad, button);
    }
}
