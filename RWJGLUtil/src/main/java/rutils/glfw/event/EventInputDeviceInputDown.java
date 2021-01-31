package rutils.glfw.event;

import rutils.glfw.Window;

public interface EventInputDeviceInputDown extends EventInputDeviceInput
{
    final class _EventInputDeviceInputDown extends AbstractEventInputDevice implements EventInputDeviceInputDown
    {
        private _EventInputDeviceInputDown(Window window)
        {
            super(window);
        }
    }
    
    static EventInputDeviceInputDown create(Window window)
    {
        return new _EventInputDeviceInputDown(window);
    }
}
