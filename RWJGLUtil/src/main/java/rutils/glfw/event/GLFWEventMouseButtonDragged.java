package rutils.glfw.event;

import org.joml.Vector2d;
import org.joml.Vector2dc;
import rutils.glfw.Mouse;
import rutils.glfw.Window;

public class GLFWEventMouseButtonDragged extends GLFWEventMouseButton
{
    private final Vector2d rel;
    private final Vector2d dragStart;
    
    public GLFWEventMouseButtonDragged(Window window, Mouse.Input button, Vector2d pos, Vector2d rel, Vector2d dragStart)
    {
        super(window, button, pos);
    
        this.rel = rel;
        this.dragStart = dragStart;
    }
    
    @Property
    public Vector2dc rel()
    {
        return this.rel;
    }
    
    public double dx()
    {
        return this.rel.x;
    }
    
    public double dy()
    {
        return this.rel.y;
    }
    
    @Property
    public Vector2dc dragStart()
    {
        return this.dragStart;
    }
    
    public double dragStartX()
    {
        return this.dragStart.x;
    }
    
    public double dragStartY()
    {
        return this.dragStart.y;
    }
}
