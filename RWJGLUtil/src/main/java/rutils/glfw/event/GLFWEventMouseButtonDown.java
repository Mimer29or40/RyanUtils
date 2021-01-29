package rutils.glfw.event;

import org.joml.Vector2d;
import rutils.glfw.Mouse;
import rutils.glfw.Window;

public class GLFWEventMouseButtonDown extends GLFWEventMouseButton
{
    public GLFWEventMouseButtonDown(Window window, Mouse.Button button, Vector2d pos)
    {
        super(window, button, pos);
    }
}
