package rutils.glfw.event;

import rutils.glfw.Gamepad;

public interface EventGamepad extends EventJoystick
{
    default Gamepad gamepad()
    {
        return (Gamepad) joystick();
    }
}
