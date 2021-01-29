package rutils.glfw.event.events;

import org.joml.Vector2i;
import org.joml.Vector2ic;
import rutils.glfw.Window;

public class EventWindowResized extends EventWindow
{
    private final Vector2i size;
    private final Vector2i rel;
    
    public EventWindowResized(Window window, Vector2i size, Vector2i rel)
    {
        super(window);
    
        this.size = size;
        this.rel  = rel;
    }
    
    @Property
    public Vector2ic size()
    {
        return this.size;
    }
    
    public int width()
    {
        return this.size.x;
    }
    
    public int height()
    {
        return this.size.y;
    }
    
    @Property
    public Vector2ic rel()
    {
        return this.rel;
    }
    
    public int dWidth()
    {
        return this.rel.x;
    }
    
    public int dHeight()
    {
        return this.rel.y;
    }
}
