package rutils.glfw.event;

import org.joml.Vector2d;
import rutils.glfw.Mouse;
import rutils.glfw.Window;

@SuppressWarnings("unused")
public class GLFWEventMouseButtonRepeat extends GLFWEventMouseButton
{
    public GLFWEventMouseButtonRepeat(Window window, Mouse.Button button, int mods, Vector2d pos)
    {
        super(window, button, mods, pos);
    }
}
