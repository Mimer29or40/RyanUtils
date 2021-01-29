package rutils.glfw.event.events;

import rutils.glfw.Joystick;

public class EventJoystickButtonPressed extends EventJoystickButton
{
    private final boolean doublePressed;
    
    public EventJoystickButtonPressed(Joystick joystick, int button, boolean doublePressed)
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
