package rutils.glfw.event;

import rutils.glfw.Window;

public interface EventInputDeviceInputRepeated extends EventInputDeviceInput
{
    final class _EventInputDeviceInputRepeated extends AbstractEventInputDevice implements EventInputDeviceInputRepeated
    {
        private _EventInputDeviceInputRepeated(Window window)
        {
            super(window);
        }
    }
    
    static EventInputDeviceInputRepeated create(Window window)
    {
        return new _EventInputDeviceInputRepeated(window);
    }
}
