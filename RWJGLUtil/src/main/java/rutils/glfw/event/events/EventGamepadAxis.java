package rutils.glfw.event.events;

import rutils.glfw.Gamepad;
import rutils.glfw.event.EventProperty;

public interface EventGamepadAxis extends EventJoystickAxis, EventGamepad
{
    @EventProperty
    Gamepad.Axis gamepadAxis();
    
    @EventProperty
    double value();
    
    @EventProperty
    double delta();
    
    final class _EventGamepadAxis extends AbstractEventJoystick implements EventGamepadAxis
    {
        private final Gamepad.Axis axis;
        private final double       value;
        private final double       delta;
        
        private _EventGamepadAxis(Gamepad gamepad, Gamepad.Axis axis, double value, double delta)
        {
            super(gamepad);
            
            this.axis  = axis;
            this.value = value;
            this.delta = delta;
        }
    
        @Override
        public int axis()
        {
            return this.axis.id();
        }
        
        @Override
        public Gamepad.Axis gamepadAxis()
        {
            return this.axis;
        }
    
        @Override
        public double value()
        {
            return this.value;
        }
        
        @Override
        public double delta()
        {
            return this.delta;
        }
    }
    
    static EventGamepadAxis create(Gamepad joystick, Gamepad.Axis axis, double value, double delta)
    {
        return new _EventGamepadAxis(joystick, axis, value, delta);
    }
}
