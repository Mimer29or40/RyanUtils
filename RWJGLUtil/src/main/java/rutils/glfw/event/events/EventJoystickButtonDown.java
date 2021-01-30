package rutils.glfw.event.events;

import rutils.glfw.Joystick;

public interface EventJoystickButtonDown extends EventJoystickButton
{
    final class _EventJoystickButtonDown extends AbstractEventJoystickButton implements EventJoystickButtonDown
    {
        private _EventJoystickButtonDown(Joystick joystick, int button)
        {
            super(joystick, button);
        }
    }
    
    static EventJoystickButtonDown create(Joystick joystick, int button)
    {
        return new _EventJoystickButtonDown(joystick, button);
    }
}
