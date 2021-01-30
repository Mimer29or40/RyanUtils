package rutils.glfw.event.events;

import rutils.glfw.Gamepad;

public interface EventGamepad extends EventJoystick
{
    default Gamepad gamepad()
    {
        return (Gamepad) joystick();
    }
    
    final class _EventGamepad extends AbstractEventJoystick implements EventGamepad
    {
        private _EventGamepad(Gamepad gamepad)
        {
            super(gamepad);
        }
    }
    
    static EventGamepad create(Gamepad gamepad)
    {
        return new _EventGamepad(gamepad);
    }
}
