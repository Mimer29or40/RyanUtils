package rutils.glfw.event.events;

import rutils.glfw.Gamepad;

public interface EventGamepadButtonUp extends EventJoystickButtonUp, EventGamepadButton
{
    final class _EventGamepadButtonUp extends AbstractEventGamepadButton implements EventGamepadButtonUp
    {
        private _EventGamepadButtonUp(Gamepad gamepad, Gamepad.Button button)
        {
            super(gamepad, button);
        }
    }
    
    static EventGamepadButtonUp create(Gamepad gamepad, Gamepad.Button button)
    {
        return new _EventGamepadButtonUp(gamepad, button);
    }
}
