package rutils.glfw.event;

import org.joml.Vector2d;
import rutils.glfw.Mouse;
import rutils.glfw.Window;

@SuppressWarnings("unused")
public class GLFWEventMouseButtonPressed extends GLFWEventMouseButton
{
    private final boolean doublePressed;
    
    public GLFWEventMouseButtonPressed(Window window, Mouse.Button button, int mods, Vector2d pos, boolean doublePressed)
    {
        super(window, button, mods, pos);
    
        this.doublePressed = doublePressed;
    }
    
    @Property
    public boolean doublePressed()
    {
        return this.doublePressed;
    }
}
