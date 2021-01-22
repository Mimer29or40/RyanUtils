package rutils.glfw.event;

import org.joml.Vector2d;
import rutils.glfw.Mouse;

@SuppressWarnings("unused")
public class GLFWEventMouseButtonHeld extends GLFWEventMouseButton
{
    public GLFWEventMouseButtonHeld(Mouse mouse, Mouse.Button button, int mods, Vector2d pos)
    {
        super(mouse, button, mods, pos);
    }
}
