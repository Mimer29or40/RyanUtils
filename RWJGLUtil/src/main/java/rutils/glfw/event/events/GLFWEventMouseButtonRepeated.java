package rutils.glfw.event.events;

import org.joml.Vector2d;
import rutils.glfw.Mouse;
import rutils.glfw.Window;

public class GLFWEventMouseButtonRepeated extends GLFWEventMouseButton
{
    public GLFWEventMouseButtonRepeated(Window window, Mouse.Button button, Vector2d pos)
    {
        super(window, button, pos);
    }
}
