package rutils.glfw.event.events;

import rutils.glfw.Joystick;

public class EventJoystickAxis extends EventJoystick
{
    private final int    axis;
    private final double value;
    private final double delta;
    
    public EventJoystickAxis(Joystick joystick, int axis, double value, double delta)
    {
        super(joystick);
        
        this.axis  = axis;
        this.value = value;
        this.delta = delta;
    }
    
    @Property
    public int axis()
    {
        return this.axis;
    }
    
    @Property
    public double value()
    {
        return this.value;
    }
    
    @Property
    public double delta()
    {
        return this.delta;
    }
}
