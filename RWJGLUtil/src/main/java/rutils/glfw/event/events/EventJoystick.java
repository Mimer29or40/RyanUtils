package rutils.glfw.event.events;

import rutils.glfw.Joystick;
import rutils.glfw.event.EventProperty;

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
