package rutils.glfw.event.events;

import org.joml.Vector2d;
import org.joml.Vector2dc;
import rutils.glfw.Window;

public class EventMouseScrolled extends EventMouse
{
    private final Vector2d scroll;
    
    public EventMouseScrolled(Window window, Vector2d scroll)
    {
        super(window);
        
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
