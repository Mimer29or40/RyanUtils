package rutils.glfw.event;

import org.joml.Vector2d;
import org.joml.Vector2dc;
import rutils.glfw.Window;

@SuppressWarnings("unused")
public class GLFWEventMouseScrolled extends GLFWEventMouse
{
    private final Vector2d scroll;
    
    public GLFWEventMouseScrolled(Window window, int mods, Vector2d scroll)
    {
        super(window, mods);
        
        this.scroll = scroll;
    }
    
    @Property
    public Vector2dc scroll()
    {
        return this.scroll;
    }
    
    public double dx()
    {
        return this.scroll.x;
    }
    
    public double dy()
    {
        return this.scroll.y;
    }
}
