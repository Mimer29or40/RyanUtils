package rutils.glfw.event;

import org.joml.Vector2d;
import org.joml.Vector2dc;
import rutils.glfw.Mouse;

@SuppressWarnings("unused")
public class GLFWEventMouseButton extends GLFWEventMouse
{
    private final Mouse.Button button;
    private final int          mods;
    private final Vector2d     pos;
    
    public GLFWEventMouseButton(Mouse mouse, Mouse.Button button, int mods, Vector2d pos)
    {
        super(mouse);
    
        this.button = button;
        this.mods   = mods;
        this.pos    = pos;
    }
    
    @Property
    public Mouse.Button button()
    {
        return this.button;
    }
    
    @Property
    public int mods()
    {
        return this.mods;
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
}
