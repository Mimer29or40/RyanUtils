package rutils.glfw.event.events;

import rutils.glfw.Joystick;
import rutils.glfw.event.EventProperty;

public abstract class AbstractEventJoystick extends AbstractEventInput implements EventJoystick
{
    final Joystick joystick;
    
    public AbstractEventJoystick(Joystick joystick)
    {
        super(null);
        
        this.joystick = joystick;
    }
    
    @Override
    public Joystick joystick()
    {
        return this.joystick;
    }
}
