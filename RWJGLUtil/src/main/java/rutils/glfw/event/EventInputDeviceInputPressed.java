package rutils.glfw.event;

import rutils.glfw.EventProperty;

public interface EventInputDeviceInputPressed extends EventInputDeviceInput
{
    @EventProperty
    boolean doublePressed();
}
