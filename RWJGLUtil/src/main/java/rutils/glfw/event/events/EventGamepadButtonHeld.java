package rutils.glfw.event.events;

import rutils.glfw.Gamepad;

public interface EventGamepadButtonHeld extends EventJoystickButtonHeld, EventGamepadButton
{
    final class _EventGamepadButtonHeld extends AbstractEventGamepadButton implements EventGamepadButtonHeld
    {
        private _EventGamepadButtonHeld(Gamepad gamepad, Gamepad.Button button)
        {
            super(gamepad, button);
        }
    }
    
    static EventGamepadButtonHeld create(Gamepad gamepad, Gamepad.Button button)
    {
        return new _EventGamepadButtonHeld(gamepad, button);
    }
}
