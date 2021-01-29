package rutils.glfw.event.events;

import rutils.glfw.Joystick;

public class GLFWEventJoystickButtonHeld extends GLFWEventJoystickButton
{
    public GLFWEventJoystickButtonHeld(Joystick joystick, int button)
    {
        super(joystick, button);
    }
}
