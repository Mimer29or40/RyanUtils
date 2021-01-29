package rutils.glfw.event.events;

import rutils.glfw.Joystick;

public class EventJoystick extends EventInput
{
    private final Joystick joystick;
    
    public EventJoystick(Joystick joystick)
    {
        super(null);
        
        this.joystick = joystick;
    }
    
    @Property(printName = false)
    public Joystick joystick()
    {
        return this.joystick;
    }
}
