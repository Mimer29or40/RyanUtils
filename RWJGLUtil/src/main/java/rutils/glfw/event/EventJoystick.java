package rutils.glfw.event;

import rutils.glfw.Joystick;
import rutils.glfw.EventProperty;

public interface EventJoystick extends EventInput
{
    @EventProperty(printName = false)
    Joystick joystick();
    
    final class _EventJoystick extends AbstractEventJoystick implements EventJoystick
    {
        private _EventJoystick(Joystick joystick)
        {
            super(joystick);
        }
    }
    
    static EventJoystick create(Joystick joystick)
    {
        return new _EventJoystick(joystick);
    }
}
