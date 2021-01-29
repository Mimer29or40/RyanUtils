package rutils.glfw.event.events;

import rutils.glfw.Joystick;

public class GLFWEventJoystickButtonDown extends GLFWEventJoystickButton
{
    public GLFWEventJoystickButtonDown(Joystick joystick, int button)
    {
        super(joystick, button);
    }
}
