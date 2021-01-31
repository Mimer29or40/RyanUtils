package rutils.glfw.event;

import rutils.glfw.Window;

public interface EventInputDeviceInputHeld extends EventInputDeviceInput
{
    final class _EventInputDeviceInputHeld extends AbstractEventInputDevice implements EventInputDeviceInputHeld
    {
        private _EventInputDeviceInputHeld(Window window)
        {
            super(window);
        }
    }
    
    static EventInputDeviceInputHeld create(Window window)
    {
        return new _EventInputDeviceInputHeld(window);
    }
}
