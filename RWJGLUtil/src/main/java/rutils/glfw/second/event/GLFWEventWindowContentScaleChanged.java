package rutils.glfw.second.event;

import org.joml.Vector2d;
import org.joml.Vector2dc;
import rutils.glfw.second.Window;

@SuppressWarnings("unused")
public class GLFWEventWindowContentScaleChanged extends GLFWEventWindow
{
    private final Vector2d scale;
    private final Vector2d rel;
    
    public GLFWEventWindowContentScaleChanged(Window window, Vector2d scale, Vector2d rel)
    {
        super(window);
        
        this.scale = scale;
        this.rel   = rel;
    }
    
    @Property
    public Vector2dc contentScale()
    {
        return this.scale;
    }
    
    public double contentScaleX()
    {
        return this.scale.x;
    }
    
    public double contentScaleY()
    {
        return this.scale.y;
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
