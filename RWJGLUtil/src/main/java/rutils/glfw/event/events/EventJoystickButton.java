package rutils.glfw.event.events;

import rutils.glfw.Joystick;
import rutils.glfw.event.EventProperty;

public interface EventJoystickButton extends EventJoystick
{
    @EventProperty
    int button();
    
    final class _EventJoystickButton extends AbstractEventJoystickButton implements EventJoystickButton
    {
        private _EventJoystickButton(Joystick joystick, int button)
        {
            super(joystick, button);
        }
    }
    
    static EventJoystickButton create(Joystick joystick, int button)
    {
        return new _EventJoystickButton(joystick, button);
    }
}
