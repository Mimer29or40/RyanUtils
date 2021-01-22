package rutils.glfw.event;

import org.joml.Vector2d;
import org.joml.Vector2dc;
import rutils.glfw.Mouse;
import rutils.glfw.Window;

@SuppressWarnings("unused")
public class GLFWEventMouseMoved extends GLFWEventMouse
{
    private final Vector2d pos;
    private final Vector2d rel;
    
    public GLFWEventMouseMoved(Window window, Vector2d pos, Vector2d rel)
    {
        super(window);
        
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
