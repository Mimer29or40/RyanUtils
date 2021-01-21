package rutils.glfw.second.event;

import org.joml.Vector2d;
import org.joml.Vector2dc;
import rutils.glfw.second.Mouse;

@SuppressWarnings("unused")
public class GLFWEventMouseMoved extends GLFWEventMouse
{
    private final Vector2d pos;
    private final Vector2d rel;
    
    public GLFWEventMouseMoved(Mouse mouse, Vector2d pos, Vector2d rel)
    {
        super(mouse);
        
        this.pos = pos;
        this.rel = rel;
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
}
