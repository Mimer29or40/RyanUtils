package rutils.glfw.event;

import rutils.glfw.Gamepad;
import rutils.glfw.EventProperty;

public interface EventGamepadButtonPressed extends EventJoystickButtonPressed, EventGamepadButton
{
    @EventProperty
    boolean doublePressed();
    
    final class _EventJoystickButtonPressed extends AbstractEventGamepadButton implements EventGamepadButtonPressed
    {
        private final boolean doublePressed;
    
        private _EventJoystickButtonPressed(Gamepad gamepad, Gamepad.Button button, boolean doublePressed)
        {
            super(gamepad, button);
            
            this.doublePressed = doublePressed;
        }
        
        @Override
        public boolean doublePressed()
        {
            return this.doublePressed;
        }
    }
    
    static EventGamepadButtonPressed create(Gamepad gamepad, Gamepad.Button button, boolean doublePressed)
    {
        return new _EventJoystickButtonPressed(gamepad, button, doublePressed);
    }
}
