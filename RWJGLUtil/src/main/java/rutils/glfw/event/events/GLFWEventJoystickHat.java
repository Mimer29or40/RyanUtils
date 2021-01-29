package rutils.glfw.event.events;

import rutils.glfw.Joystick;

public class GLFWEventJoystickHat extends GLFWEventJoystick
{
    private final int          hat;
    private final Joystick.Hat state;
    
    public GLFWEventJoystickHat(Joystick joystick, int hat, Joystick.Hat state)
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
