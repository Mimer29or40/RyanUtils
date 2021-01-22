package rutils.glfw.event;

import org.joml.Vector2d;
import org.joml.Vector2dc;
import rutils.glfw.Mouse;
import rutils.glfw.Window;

public class GLFWEventMouseButton extends GLFWEventMouse
{
    private final Mouse.Input button;
    private final Vector2d    pos;
    
    public GLFWEventMouseButton(Window window, Mouse.Input button, Vector2d pos)
    {
        super(window);
    
        this.button = button;
        this.pos    = pos;
    }
    
    @Property(printName = false)
    public Mouse.Input button()
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
