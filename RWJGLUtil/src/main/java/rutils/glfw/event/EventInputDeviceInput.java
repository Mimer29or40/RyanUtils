package rutils.glfw.event;

import rutils.glfw.Window;

public interface EventInputDeviceInput extends EventInputDevice
{
    final class _EventInputDeviceInput extends AbstractEventInputDevice implements EventInputDeviceInput
    {
        private _EventInputDeviceInput(Window window)
        {
            super(window);
        }
    }
    
    static EventInputDeviceInput create(Window window)
    {
        return new _EventInputDeviceInput(window);
    }
}
