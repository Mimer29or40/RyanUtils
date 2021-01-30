package rutils.glfw.event.events;

import rutils.glfw.Joystick;
import rutils.glfw.event.EventProperty;

public interface EventJoystickHat extends EventJoystick
{
    @EventProperty
    int hat();
    
    @EventProperty
    Joystick.Hat state();
    
    final class _EventJoystickHat extends AbstractEventJoystick implements EventJoystickHat
    {
        private final int          hat;
        private final Joystick.Hat state;
    
        private _EventJoystickHat(Joystick joystick, int hat, Joystick.Hat state)
        {
            super(joystick);
        
            this.hat   = hat;
            this.state = state;
        }
    
        @Override
        public int hat()
        {
            return this.hat;
        }
    
        @Override
        public Joystick.Hat state()
        {
            return this.state;
        }
    }
    
    static EventJoystickHat create(Joystick joystick, int hat, Joystick.Hat state)
    {
        return new _EventJoystickHat(joystick, hat, state);
    }
}
