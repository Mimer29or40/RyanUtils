package rutils.glfw.event;

import org.joml.Vector2d;
import rutils.glfw.Mouse;
import rutils.glfw.Window;

public class GLFWEventMouseButtonPressed extends GLFWEventMouseButton
{
    private final boolean doublePressed;
    
    public GLFWEventMouseButtonPressed(Window window, Mouse.Input button, Vector2d pos, boolean doublePressed)
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
