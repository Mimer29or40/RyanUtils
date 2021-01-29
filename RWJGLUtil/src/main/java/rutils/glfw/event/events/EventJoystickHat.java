package rutils.glfw.event.events;

import rutils.glfw.Joystick;

public class EventJoystickHat extends EventJoystick
{
    private final int          hat;
    private final Joystick.Hat state;
    
    public EventJoystickHat(Joystick joystick, int hat, Joystick.Hat state)
    {
        super(joystick);
    
        this.hat   = hat;
        this.state = state;
    }
    
    @Property
    public int hat()
    {
        return this.hat;
    }
    
    @Property
    public Joystick.Hat state()
    {
        return this.state;
    }
}
