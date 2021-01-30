package rutils.glfw.event;

import rutils.glfw.Joystick;

public interface EventJoystickButtonRepeated extends EventJoystickButton
{
    final class _EventJoystickButtonRepeated extends AbstractEventJoystickButton implements EventJoystickButtonRepeated
    {
        private _EventJoystickButtonRepeated(Joystick joystick, int button)
        {
            super(joystick, button);
        }
    }
    
    static EventJoystickButtonRepeated create(Joystick joystick, int button)
    {
        return new _EventJoystickButtonRepeated(joystick, button);
    }
}
