package rutils.glfw.event.events;

import rutils.glfw.Joystick;

public class GLFWEventJoystickButton extends GLFWEventJoystick
{
    private final int button;
    
    public GLFWEventJoystickButton(Joystick joystick, int button)
    {
        super(joystick);
    
        this.button = button;
    }
    
    @Property
    public int button()
    {
        return this.button;
    }
}
