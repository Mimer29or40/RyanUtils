package rutils.glfw.event.events;

import org.joml.Vector2d;
import rutils.glfw.Mouse;
import rutils.glfw.Window;

public class EventMouseButtonPressed extends EventMouseButton
{
    private final boolean doublePressed;
    
    public EventMouseButtonPressed(Window window, Mouse.Button button, Vector2d pos, boolean doublePressed)
    {
        super(window, button, pos);
    
        this.doublePressed = doublePressed;
    }
    
    @Property
    public boolean doublePressed()
    {
        return this.doublePressed;
    }
}
