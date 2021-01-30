package rutils.glfw.event.events;

import rutils.glfw.Joystick;
import rutils.glfw.event.EventProperty;

public interface EventJoystickButtonPressed extends EventJoystickButton
{
    @EventProperty
    boolean doublePressed();
    
    final class _EventJoystickButtonPressed extends AbstractEventJoystickButton implements EventJoystickButtonPressed
    {
        private final boolean doublePressed;
    
        private _EventJoystickButtonPressed(Joystick joystick, int button, boolean doublePressed)
        {
            super(joystick, button);
            
            this.doublePressed = doublePressed;
        }
        
        @Override
        public boolean doublePressed()
        {
            return this.doublePressed;
        }
    }
    
    static EventJoystickButtonPressed create(Joystick joystick, int button, boolean doublePressed)
    {
        return new _EventJoystickButtonPressed(joystick, button, doublePressed);
    }
}
