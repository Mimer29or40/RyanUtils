package rutils.glfw.event.events;

import rutils.glfw.Joystick;

public class GLFWEventJoystick extends GLFWEventInput
{
    private final Joystick joystick;
    
    public GLFWEventJoystick(Joystick joystick)
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
