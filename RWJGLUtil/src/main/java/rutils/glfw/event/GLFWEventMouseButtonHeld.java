package rutils.glfw.event;

import org.joml.Vector2d;
import rutils.glfw.Mouse;
import rutils.glfw.Window;

public class GLFWEventMouseButtonHeld extends GLFWEventMouseButton
{
    public GLFWEventMouseButtonHeld(Window window, Mouse.Input button, Vector2d pos)
    {
        super(window, button, pos);
    }
}
