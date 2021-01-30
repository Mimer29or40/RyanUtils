package rutils.glfw.event.events;

import rutils.glfw.Joystick;

public abstract class AbstractEventJoystickButton extends AbstractEventJoystick implements EventJoystickButton
{
    private final int button;
    
    public AbstractEventJoystickButton(Joystick joystick, int button)
    {
        super(joystick);
        
        this.button = button;
    }
    
    @Override
    public int button()
    {
        return this.button;
    }
}
