package rutils.glfw.event;

import rutils.glfw.Joystick;

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
