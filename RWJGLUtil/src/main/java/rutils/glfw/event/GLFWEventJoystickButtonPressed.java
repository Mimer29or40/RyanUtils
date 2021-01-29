package rutils.glfw.event;

import rutils.glfw.Joystick;

public class GLFWEventJoystickButtonPressed extends GLFWEventJoystickButton
{
    private final boolean doublePressed;
    
    public GLFWEventJoystickButtonPressed(Joystick joystick, int button, boolean doublePressed)
    {
        super(joystick, button);
        
        this.doublePressed = doublePressed;
    }
    
    @Property
    public boolean doublePressed()
    {
        return this.doublePressed;
    }
}
