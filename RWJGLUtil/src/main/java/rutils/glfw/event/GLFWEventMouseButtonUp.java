package rutils.glfw.event;

import org.joml.Vector2d;
import rutils.glfw.Mouse;
import rutils.glfw.Window;

public class GLFWEventMouseButtonUp extends GLFWEventMouseButton
{
    public GLFWEventMouseButtonUp(Window window, Mouse.Input button, Vector2d pos)
    {
        super(window, button, pos);
    }
}
