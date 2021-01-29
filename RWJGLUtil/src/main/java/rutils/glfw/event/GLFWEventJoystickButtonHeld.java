package rutils.glfw.event;

import rutils.glfw.Joystick;

public class GLFWEventJoystickButtonHeld extends GLFWEventJoystickButton
{
    public GLFWEventJoystickButtonHeld(Joystick joystick, int button)
    {
        super(joystick, button);
    }
}
