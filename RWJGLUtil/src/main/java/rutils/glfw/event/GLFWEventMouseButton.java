package rutils.glfw.event;

import org.joml.Vector2d;
import org.joml.Vector2dc;
import rutils.glfw.Mouse;
import rutils.glfw.Window;

@SuppressWarnings("unused")
public class GLFWEventMouseButton extends GLFWEventMouse
{
    private final Mouse.Button button;
    private final Vector2d     pos;
    
    public GLFWEventMouseButton(Window window, int mods, Mouse.Button button, Vector2d pos)
    {
        super(window, mods);
    
        this.button = button;
        this.pos    = pos;
    }
    
    @Property
    public Mouse.Button button()
    {
        return this.button;
    }
    
    @Property
    public Vector2dc pos()
    {
        return this.pos;
    }
    
    public double x()
    {
        return this.pos.x;
    }
    
    public double y()
    {
        return this.pos.y;
    }
}
