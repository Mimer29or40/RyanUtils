package rutils.glfw.second.event;

import org.joml.Vector2i;
import org.joml.Vector2ic;
import rutils.glfw.second.Window;

public class GLFWEventWindowMoved extends GLFWEventWindow
{
    private final Vector2i pos;
    
    public GLFWEventWindowMoved(Window window, Vector2i pos)
    {
        super(window);
        
        this.pos = pos;
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
}
