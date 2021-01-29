package rutils.glfw.event;

import rutils.glfw.Joystick;

public class GLFWEventJoystickButtonDown extends GLFWEventJoystickButton
{
    public GLFWEventJoystickButtonDown(Joystick joystick, int button)
    {
        super(joystick, button);
    }
}
