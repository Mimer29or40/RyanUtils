package rutils.glfw.event;

import rutils.glfw.EventProperty;
import rutils.glfw.Window;

public interface EventInputDeviceInputPressed extends EventInputDeviceInput
{
    @EventProperty
    boolean doublePressed();
    
    final class _EventInputDeviceInputPressed extends AbstractEventInputDevice implements EventInputDeviceInputPressed
    {
        private final boolean doublePressed;
    
        private _EventInputDeviceInputPressed(Window window, boolean doublePressed)
        {
            super(window);
    
            this.doublePressed = doublePressed;
        }
    
        @Override
        public boolean doublePressed()
        {
            return this.doublePressed;
        }
    }
    
    static EventInputDeviceInputPressed create(Window window, boolean doublePressed)
    {
        return new _EventInputDeviceInputPressed(window, doublePressed);
    }
}
