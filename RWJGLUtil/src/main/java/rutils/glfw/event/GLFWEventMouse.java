package rutils.glfw.event;

import rutils.glfw.Window;

@SuppressWarnings("unused")
public class GLFWEventMouse extends GLFWEventInput
{
    public GLFWEventMouse(Window window, int mods)
    {
        super(window, mods);
    }
}
