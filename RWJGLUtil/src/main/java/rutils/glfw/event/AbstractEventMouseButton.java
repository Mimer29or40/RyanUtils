package rutils.glfw.event;

import org.joml.Vector2d;
import org.joml.Vector2dc;
import rutils.glfw.Mouse;
import rutils.glfw.Window;

abstract class AbstractEventMouseButton extends AbstractEventInput implements EventMouseButton
{
    private final Mouse.Button button;
    private final Vector2d     pos;
    
    AbstractEventMouseButton(Window window, Mouse.Button button, Vector2dc pos)
    {
        super(window);
        
        this.button = button;
        this.pos    = new Vector2d(pos);
    }
    
    @Override
    public Mouse.Button button()
    {
        return this.button;
    }
    
    @Override
    public Vector2dc pos()
    {
        return this.pos;
    }
}
