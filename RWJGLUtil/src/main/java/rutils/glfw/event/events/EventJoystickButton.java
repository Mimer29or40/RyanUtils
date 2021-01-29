package rutils.glfw.event.events;

import rutils.glfw.Joystick;

public class EventJoystickButton extends EventJoystick
{
    private final int button;
    
    public EventJoystickButton(Joystick joystick, int button)
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
