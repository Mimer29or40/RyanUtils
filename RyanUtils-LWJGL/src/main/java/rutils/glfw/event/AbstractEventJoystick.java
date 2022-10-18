package rutils.glfw.event;

import rutils.glfw.Joystick;

abstract class AbstractEventJoystick extends AbstractEventInputDevice implements EventJoystick
{
    private final Joystick joystick;
    
    AbstractEventJoystick(Joystick joystick)
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
