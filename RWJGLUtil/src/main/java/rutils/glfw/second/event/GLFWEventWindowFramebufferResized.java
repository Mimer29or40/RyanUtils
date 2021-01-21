package rutils.glfw.second.event;

import org.joml.Vector2i;
import org.joml.Vector2ic;
import rutils.glfw.second.Window;

public class GLFWEventWindowFramebufferResized extends GLFWEventWindow
{
    private final Vector2i fbSize;
    
    public GLFWEventWindowFramebufferResized(Window window, Vector2i fbSize)
    {
        super(window);
        
        this.fbSize = fbSize;
    }
    
    @Property
    public Vector2ic framebufferSize()
    {
        return this.fbSize;
    }
    
    public int framebufferWidth()
    {
        return this.fbSize.x;
    }
    
    public int framebufferHeight()
    {
        return this.fbSize.y;
    }
}
