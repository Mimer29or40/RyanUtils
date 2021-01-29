package rutils.glfw.event;

import rutils.glfw.Joystick;

public class GLFWEventJoystickAxis extends GLFWEventJoystick
{
    private final int    axis;
    private final double value;
    private final double delta;
    
    public GLFWEventJoystickAxis(Joystick joystick, int axis, double value, double delta)
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
