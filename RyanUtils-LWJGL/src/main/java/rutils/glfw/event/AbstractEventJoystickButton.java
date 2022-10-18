package rutils.glfw.event;

import rutils.glfw.Joystick;

abstract class AbstractEventJoystickButton extends AbstractEventJoystick implements EventJoystickButton
{
    private final int button;
    
    AbstractEventJoystickButton(Joystick joystick, int button)
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
