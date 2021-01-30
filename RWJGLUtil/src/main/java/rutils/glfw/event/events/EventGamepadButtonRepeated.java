package rutils.glfw.event.events;

import rutils.glfw.Gamepad;

public interface EventGamepadButtonRepeated extends EventJoystickButtonRepeated, EventGamepadButton
{
    final class _EventGamepadButtonRepeated extends AbstractEventGamepadButton implements EventGamepadButtonRepeated
    {
        private _EventGamepadButtonRepeated(Gamepad gamepad, Gamepad.Button button)
        {
            super(gamepad, button);
        }
    }
    
    static EventGamepadButtonRepeated create(Gamepad gamepad, Gamepad.Button button)
    {
        return new _EventGamepadButtonRepeated(gamepad, button);
    }
}
