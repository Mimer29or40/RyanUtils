package rutils.glfw.second.event;

import org.joml.Vector2d;
import org.joml.Vector2dc;
import rutils.glfw.second.Window;

public class GLFWEventWindowContentScaleChanged extends GLFWEventWindow
{
    private final Vector2d scale;
    
    public GLFWEventWindowContentScaleChanged(Window window, Vector2d scale)
    {
        super(window);
        
        this.scale = scale;
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
}
