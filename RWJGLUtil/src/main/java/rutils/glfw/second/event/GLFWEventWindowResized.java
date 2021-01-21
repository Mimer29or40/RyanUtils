package rutils.glfw.second.event;

import org.joml.Vector2i;
import org.joml.Vector2ic;
import rutils.glfw.second.Window;

public class GLFWEventWindowResized extends GLFWEventWindow
{
    private final Vector2i size;
    
    public GLFWEventWindowResized(Window window, Vector2i size)
    {
        super(window);
        
        this.size = size;
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
}
