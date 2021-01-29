package rutils.glfw.event.events;

import org.joml.Vector2i;
import org.joml.Vector2ic;
import rutils.glfw.Window;

public class GLFWEventWindowMoved extends GLFWEventWindow
{
    private final Vector2i pos;
    private final Vector2i rel;
    
    public GLFWEventWindowMoved(Window window, Vector2i pos, Vector2i rel)
    {
        super(window);
        
        this.pos = pos;
        this.rel = rel;
    }
    
    @Property
    public Vector2ic pos()
    {
        return this.pos;
    }
    
    public int x()
    {
        return this.pos.x;
    }
    
    public int y()
    {
        return this.pos.y;
    }
    
    @Property
    public Vector2ic rel()
    {
        return this.rel;
    }
    
    public int dx()
    {
        return this.rel.x;
    }
    
    public int dy()
    {
        return this.rel.y;
    }
}
