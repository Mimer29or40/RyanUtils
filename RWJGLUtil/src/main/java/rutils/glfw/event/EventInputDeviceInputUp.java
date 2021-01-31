package rutils.glfw.event;

import rutils.glfw.Window;

public interface EventInputDeviceInputUp extends EventInputDeviceInput
{
    final class _EventInputDeviceInputUp extends AbstractEventInputDevice implements EventInputDeviceInputUp
    {
        private _EventInputDeviceInputUp(Window window)
        {
            super(window);
        }
    }
    
    static EventInputDeviceInputUp create(Window window)
    {
        return new _EventInputDeviceInputUp(window);
    }
}
